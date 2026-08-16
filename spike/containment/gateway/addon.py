"""
The fixture-serving, audit-writing mitmproxy addon.

This is the spike-sized ancestor of the "governor" described in the design discussion:
attribute host -> access point, match a route, serve a variant, record what happened.
Situations and state transitions are deliberately absent — this file exists to prove the
interception mechanism, not the scenario model.

The single most important property: *the addon never forwards*. Every request either gets a
fixture or gets a refusal, and both are logged. It cannot leak even if the matching is wrong.
"""

import json
import threading
import time
from datetime import datetime, timezone

from mitmproxy import http

FIXTURES_PATH = "/fixtures.json"
FLOWS_PATH = "/out/flows.jsonl"

with open(FIXTURES_PATH) as fh:
    FIXTURES = json.load(fh)

_lock = threading.Lock()
_seq = 0


def _log(record: dict) -> None:
    """Append one audit line. Monotonic seq gives a total order independent of clock resolution."""
    global _seq
    with _lock:
        _seq += 1
        record["seq"] = _seq
        record["ts"] = datetime.now(timezone.utc).isoformat()
        record.setdefault("event", "http_request")
        with open(FLOWS_PATH, "a") as fh:
            fh.write(json.dumps(record) + "\n")
            fh.flush()


def _intended_destination(context):
    """
    Best-effort recovery of where the client actually meant to go.

    For hostname traffic this is redundant with the Host header, but for a raw-IP
    connection there is no hostname anywhere in the request — the only witness is the
    original destination the kernel recorded before iptables rewrote it, which mitmproxy
    surfaces on the server connection's address in transparent mode.
    """
    try:
        addr = getattr(getattr(context, "server", None), "address", None)
        if addr:
            return {"intended_host": addr[0], "intended_port": addr[1]}
    except Exception:  # pragma: no cover - diagnostic path only
        pass
    return {"intended_host": None, "intended_port": None}


# --- connection-layer observation ----------------------------------------------------
# The HTTP `request` hook only fires for traffic that parses as HTTP. A raw-IP TLS attempt
# usually presents no SNI, so certificate minting fails and no request ever materialises.
# Without these hooks that attempt would be contained but unobserved — which is the exact
# blind spot this whole experiment exists to close.

def tls_clienthello(data) -> None:
    dest = _intended_destination(data.context)
    _log({
        "event": "tls_clienthello",
        "hostname": data.client_hello.sni,
        "sni_present": data.client_hello.sni is not None,
        "disposition": "observed",
        **dest,
    })


def tls_failed_client(data) -> None:
    dest = _intended_destination(data.context)
    _log({
        "event": "tls_failed",
        "hostname": None,
        "disposition": "tls_rejected",
        "detail": str(getattr(data, "conn", None) and data.conn.error or "")[:200],
        **dest,
    })


def _match_path(pattern: str, path: str):
    """
    Segment-wise match with {param} capture.

    Every pattern segment must consume exactly one path segment, and the path must be
    exhausted. (The air prototype's version skipped non-matching literals, which let
    pattern /a/b match path /b — see README. This one fails closed.)
    """
    pat = [s for s in pattern.strip("/").split("/") if s]
    act = [s for s in path.split("?")[0].strip("/").split("/") if s]
    if len(pat) != len(act):
        return None
    captures = {}
    for p, a in zip(pat, act):
        if p.startswith("{") and p.endswith("}"):
            captures[p[1:-1]] = a
        elif p != a:
            return None
    return captures


def _find_access_point(host: str):
    """Attribute a hostname to a declared access point: exact match, then *.wildcard."""
    for ap in FIXTURES["access_points"]:
        for pattern in ap["hosts"]:
            if pattern == host:
                return ap
            if pattern.startswith("*.") and host.endswith(pattern[1:]):
                return ap
    return None


def request(flow: http.HTTPFlow) -> None:
    host = flow.request.pretty_host
    base = {
        "hostname": host,
        "port": flow.request.port,
        "method": flow.request.method,
        "path": flow.request.path,
        "scheme": flow.request.scheme,
    }

    access_point = _find_access_point(host)

    # --- nothing declared this host -------------------------------------------------
    # Not an error path. This is the containment signal, and in the real product this
    # record is what the deviation report is built from.
    if access_point is None:
        _log({**base, "access_point": None, "variant": None,
              "disposition": "unmatched", "response_status": 403})
        flow.response = http.Response.make(
            403,
            json.dumps({"error": "undeclared access point", "host": host}).encode(),
            {"Content-Type": "application/json"},
        )
        return

    # --- host declared: find a route ------------------------------------------------
    for route in access_point.get("routes", []):
        if route.get("method", "ANY") not in ("ANY", flow.request.method):
            continue
        captures = _match_path(route["path_pattern"], flow.request.path)
        if captures is None:
            continue

        variant = route["variants"][0]
        body = variant.get("body", "")
        for key, value in captures.items():
            body = body.replace("{" + key + "}", value)

        latency_ms = variant.get("latency_ms", 0)
        if latency_ms:
            time.sleep(latency_ms / 1000.0)

        _log({**base,
              "access_point": access_point["name"],
              "route": route["path_pattern"],
              "variant": variant.get("name"),
              "disposition": "mocked",
              "response_status": variant.get("status", 200)})

        flow.response = http.Response.make(
            variant.get("status", 200),
            body.encode(),
            variant.get("headers", {}),
        )
        return

    # --- host declared, route not ----------------------------------------------------
    _log({**base, "access_point": access_point["name"], "variant": None,
          "disposition": "unmatched_route", "response_status": 404})
    flow.response = http.Response.make(
        404,
        json.dumps({"error": "no route declared", "host": host,
                    "path": flow.request.path}).encode(),
        {"Content-Type": "application/json"},
    )
