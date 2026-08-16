"""
The code under observation.

This is deliberately the most ordinary Python imaginable: plain `requests`, default TLS
verification, no configuration, no awareness of a gateway. If containment requires this file
to cooperate in any way, the experiment has failed.
"""

import json
import os
import socket
import time

import requests

OUT = "/out/probe.json"

results = {}

# Claim 3, part one: is there anything in the environment that betrays the sandbox?
results["proxy_env"] = {
    k: v for k, v in os.environ.items()
    if any(tok in k.lower() for tok in ("proxy", "ca_bundle", "ssl_cert", "curl_ca"))
}


def probe(name: str, url: str, note: str, verify=True) -> None:
    record = {"url": url, "note": note}
    try:
        host = url.split("://", 1)[1].split("/", 1)[0]
        record["resolved_to"] = socket.gethostbyname(host)
    except Exception as exc:
        record["resolved_to"] = f"{type(exc).__name__}: {exc}"

    try:
        # verify defaults to True. Nothing is suppressed. If the certificate does not
        # chain to a CA the client already trusts, this raises.
        response = requests.get(url, timeout=15, verify=verify)
        record.update(
            status=response.status_code,
            body=response.text[:400],
            tls_ok=True,
            error=None,
        )
    except requests.exceptions.SSLError as exc:
        record.update(status=None, body=None, tls_ok=False,
                      error=f"SSLError: {str(exc)[:300]}")
    except Exception as exc:
        record.update(status=None, body=None, tls_ok=None,
                      error=f"{type(exc).__name__}: {str(exc)[:300]}")

    results[name] = record
    print(f"[probe] {name}: {json.dumps(record)[:300]}", flush=True)


probe(
    "declared",
    "https://api.declared.test/v1/widgets",
    "Declared in fixtures.json — must return the fixture over trusted TLS.",
)

probe(
    "declared_with_param",
    "https://api.declared.test/v1/widgets/42",
    "Exercises {id} path-template capture and body templating.",
)

probe(
    "undeclared",
    "https://evil.undeclared.test/exfil",
    "Declared by nobody — must be intercepted and logged, not silently dropped.",
)

probe(
    "real_external_host",
    "https://pypi.org/simple/",
    "A real, live host. Must be answered by our gateway, proving no traffic leaves.",
)

# The evasion vectors. These skip DNS entirely — no hostname is ever looked up, so wildcard
# resolution cannot steer them. They are the honest test of whether the sentinel sees
# everything or merely everything that asked it for directions.
probe(
    "raw_ip_https",
    "https://1.1.1.1/exfil",
    "Raw IP over TLS, no DNS, no SNI. Must be refused AND observed.",
)

probe(
    "raw_ip_http",
    "http://1.1.1.1/exfil",
    "Raw IP over plaintext, no DNS. Must be refused AND observed.",
)


def probe_raw_socket(name: str, host: str, port: int, note: str) -> None:
    """A bare TCP connect on an odd port — no HTTP, no TLS, nothing to parse."""
    import socket as _socket
    record = {"target": f"{host}:{port}", "note": note}
    t0 = time.time()
    sock = _socket.socket()
    sock.settimeout(8)
    try:
        sock.connect((host, port))
        record.update(connected=True, error=None)
    except Exception as exc:
        record.update(connected=False, error=f"{type(exc).__name__}: {str(exc)[:200]}")
    finally:
        sock.close()
    record["elapsed_s"] = round(time.time() - t0, 3)
    results[name] = record
    print(f"[probe] {name}: {json.dumps(record)[:300]}", flush=True)


probe_raw_socket(
    "raw_ip_odd_port", "1.1.1.1", 9999,
    "Bare TCP on a non-web port. Tests whether capture is port-specific.",
)


# Negative control. Same request, but validated against the stock trust bundle as it
# shipped — before the gateway CA was added. This MUST fail. If it ever passes, then
# "TLS verified cleanly" above has stopped meaning anything and the suite is vacuous.
probe(
    "negative_control_pristine_ca",
    "https://api.declared.test/v1/widgets",
    "Against the untouched CA bundle. Expected to fail; proves verification is real.",
    verify="/tmp/pristine-ca.pem",
)

with open(OUT, "w") as fh:
    json.dump(results, fh, indent=2)

print(f"[probe] wrote {OUT}", flush=True)
