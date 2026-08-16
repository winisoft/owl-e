"""
The three claims, as assertions.

Brings the sealed network up, runs the workload to completion, then reads what it wrote
and what the gateway audited. Everything asserted here is observed output, not a mock.
"""

import json
import shutil
import subprocess
from pathlib import Path

import pytest

HERE = Path(__file__).parent
OUT = HERE / "out"
CA = HERE / "ca"

FIXTURE_MARKER = "SPIKE-FIXTURE-OK"


def _compose(*args, timeout=900):
    # encoding is pinned because Docker emits UTF-8 and the default Windows codepage
    # (cp1252) throws mid-stream, which would swallow the diagnostics on a failure.
    return subprocess.run(
        ["docker", "compose", *args],
        cwd=HERE, capture_output=True, text=True, timeout=timeout,
        encoding="utf-8", errors="replace",
    )


@pytest.fixture(scope="session")
def run_spike():
    """Bring the whole thing up once, tear it down, hand the artifacts to the tests."""
    for d in (OUT, CA):
        if d.exists():
            shutil.rmtree(d, ignore_errors=True)
        d.mkdir(parents=True, exist_ok=True)

    _compose("down", "-v", "--remove-orphans", timeout=180)

    result = _compose(
        "up", "--build", "--abort-on-container-exit", "--exit-code-from", "workload"
    )
    logs = _compose("logs", "--no-color", timeout=120).stdout

    probe_path = OUT / "probe.json"
    flows_path = OUT / "flows.jsonl"
    tap_path = OUT / "tap.txt"

    artifacts = {
        "returncode": result.returncode,
        "stdout": result.stdout,
        "stderr": result.stderr,
        "logs": logs,
        "probe": json.loads(probe_path.read_text()) if probe_path.exists() else None,
        "flows": [
            json.loads(line)
            for line in flows_path.read_text().splitlines()
            if line.strip()
        ] if flows_path.exists() else [],
        "tap": tap_path.read_text() if tap_path.exists() else "",
    }

    yield artifacts

    _compose("down", "-v", "--remove-orphans", timeout=180)


@pytest.fixture(scope="session")
def probe(run_spike):
    if run_spike["probe"] is None:
        pytest.fail(
            "workload produced no probe.json.\n"
            f"--- compose stdout ---\n{run_spike['stdout'][-4000:]}\n"
            f"--- compose stderr ---\n{run_spike['stderr'][-4000:]}\n"
            f"--- logs ---\n{run_spike['logs'][-4000:]}"
        )
    return run_spike["probe"]


@pytest.fixture(scope="session")
def flows(run_spike):
    """HTTP-level audit records only."""
    return [f for f in run_spike["flows"] if f.get("event", "http_request") == "http_request"]


@pytest.fixture(scope="session")
def all_events(run_spike):
    """Every audit record, including connection- and TLS-layer observations."""
    return run_spike["flows"]


@pytest.fixture(scope="session")
def tap(run_spike):
    """Packet capture taken inside the workload's network namespace."""
    if not run_spike["tap"].strip():
        pytest.fail(
            "capture sidecar produced nothing.\n"
            f"--- logs ---\n{run_spike['logs'][-4000:]}"
        )
    return run_spike["tap"]


# ---------------------------------------------------------------------------
# Claim 1 — a declared host is served from fixtures over TLS the client trusts
# ---------------------------------------------------------------------------

def test_declared_host_is_served_from_fixtures(probe):
    rec = probe["declared"]
    assert rec["error"] is None, f"request failed: {rec['error']}"
    assert rec["status"] == 200
    assert FIXTURE_MARKER in rec["body"], f"did not get the fixture body: {rec['body']!r}"


def test_declared_host_tls_verified_without_relaxation(probe):
    """requests ran with verify=True. A trusted chain is the only way this passes."""
    rec = probe["declared"]
    assert rec["tls_ok"] is True, f"TLS did not verify cleanly: {rec['error']}"


def test_suite_is_falsifiable(probe):
    """
    Negative control. The same request against the stock trust bundle — before the gateway
    CA was installed — must fail certificate verification.

    If this ever passes, TLS verification is not actually happening and every other TLS
    assertion here is worthless.
    """
    rec = probe["negative_control_pristine_ca"]
    assert rec["tls_ok"] is False, (
        "the untrusted-CA control SUCCEEDED — TLS verification is not being enforced, "
        f"so the suite proves nothing. record={rec}"
    )
    assert "CERTIFICATE_VERIFY_FAILED" in (rec["error"] or "").upper().replace(" ", "_") \
        or "SSLError" in (rec["error"] or ""), \
        f"expected a certificate verification failure, got: {rec['error']}"


def test_path_template_captures_and_templates_the_body(probe):
    rec = probe["declared_with_param"]
    assert rec["error"] is None, f"request failed: {rec['error']}"
    assert rec["status"] == 200
    body = json.loads(rec["body"])
    assert body["id"] == "42", f"{{id}} capture did not reach the body: {rec['body']!r}"


# ---------------------------------------------------------------------------
# Claim 2 — an undeclared host is intercepted and logged, not silently dropped
# ---------------------------------------------------------------------------

def test_undeclared_host_is_refused(probe):
    rec = probe["undeclared"]
    assert rec["error"] is None, f"expected an HTTP refusal, got transport error: {rec['error']}"
    assert rec["status"] == 403


def test_undeclared_attempt_appears_in_the_audit(flows):
    hits = [f for f in flows if f["hostname"] == "evil.undeclared.test"]
    assert hits, f"nothing audited for the undeclared host. flows={flows}"
    assert hits[0]["disposition"] == "unmatched"
    assert hits[0]["access_point"] is None
    assert hits[0]["path"] == "/exfil", "the attempted path must survive into the audit"


def test_every_attempt_is_audited(flows):
    """Four probes were made; the audit must account for all of them."""
    assert len(flows) >= 4, f"expected >=4 audited flows, got {len(flows)}: {flows}"
    assert all(f.get("disposition") for f in flows), "every flow needs a disposition"
    assert [f["seq"] for f in flows] == sorted(f["seq"] for f in flows), \
        "audit sequence must be monotonic"


# ---------------------------------------------------------------------------
# Claim 3 — the workload cannot tell it is sandboxed
# ---------------------------------------------------------------------------

def test_no_proxy_or_ca_environment_variables(probe):
    """The usual tells: HTTP(S)_PROXY, REQUESTS_CA_BUNDLE, SSL_CERT_FILE."""
    assert probe["proxy_env"] == {}, f"environment betrays the sandbox: {probe['proxy_env']}"


def test_real_external_host_never_leaves_the_sandbox(probe, flows):
    """
    pypi.org genuinely exists. If the sandbox leaked, this would return pypi's own page.
    It must instead be answered by our gateway.
    """
    rec = probe["real_external_host"]
    assert rec["resolved_to"] == "172.28.0.10", \
        f"DNS did not point at the gateway: {rec['resolved_to']}"
    assert rec["status"] == 403, f"expected our gateway's refusal, got {rec['status']}"

    hits = [f for f in flows if f["hostname"] == "pypi.org"]
    assert hits, "the attempt on a real external host was not audited"
    assert hits[0]["disposition"] == "unmatched"


def test_declared_flow_is_attributed_to_its_access_point(flows):
    hits = [f for f in flows if f["hostname"] == "api.declared.test"
            and f["path"] == "/v1/widgets"]
    assert hits, "declared call missing from the audit"
    assert hits[0]["disposition"] == "mocked"
    assert hits[0]["access_point"] == "Declared Widget API"
    assert hits[0]["variant"] == "Success"


# ---------------------------------------------------------------------------
# Claim 4 — raw-IP egress, which skips DNS entirely, is contained AND observed
#
# This is the evasion path. Wildcard DNS cannot steer a connection that never asks
# for a name, and the sealed bridge discards such packets before the gateway sees
# them. Containment therefore comes from the topology; observation comes from a
# capture sidecar sharing the workload's network namespace.
# ---------------------------------------------------------------------------

RAW_IP_TARGETS = [("1.1.1.1", "443"), ("1.1.1.1", "80"), ("1.1.1.1", "9999")]


def test_raw_ip_egress_does_not_succeed(probe):
    """Every raw-IP vector must fail. None may complete a connection."""
    for name in ("raw_ip_https", "raw_ip_http"):
        rec = probe[name]
        assert rec["status"] is None, f"{name} unexpectedly got a response: {rec}"
        assert rec["error"], f"{name} should have failed outright: {rec}"

    sock = probe["raw_ip_odd_port"]
    assert sock["connected"] is False, f"bare TCP on an odd port connected: {sock}"


def test_raw_ip_attempts_never_reach_the_gateway(all_events):
    """
    Documents the real limitation rather than papering over it: the sentinel genuinely
    cannot see these. If this ever starts failing, the topology changed and the sidecar
    may no longer be load-bearing — which is worth knowing.
    """
    for host, _ in RAW_IP_TARGETS:
        hits = [e for e in all_events if e.get("hostname") == host
                or e.get("intended_host") == host]
        assert not hits, f"unexpectedly saw {host} at the gateway: {hits}"


def test_raw_ip_attempts_are_observed_by_the_capture_sidecar(tap):
    """The point of the whole exercise: invisible to the sentinel, still not unobserved."""
    for host, port in RAW_IP_TARGETS:
        needle = f"> {host}.{port}:"
        assert needle in tap, (
            f"raw-IP attempt to {host}:{port} was NOT captured — it would have been "
            f"contained but invisible.\n--- capture ---\n{tap[:2000]}"
        )


def test_capture_records_outbound_syn_with_full_destination(tap):
    """A finding needs receipts: direction, destination, and port, not just a count."""
    syns = [ln for ln in tap.splitlines() if "Flags [S]" in ln and "1.1.1.1.443" in ln]
    assert syns, f"no SYN captured toward 1.1.1.1:443\n{tap[:2000]}"
    assert "Out" in syns[0], f"capture did not record direction: {syns[0]}"
    assert "172.28.0.2" in syns[0], f"capture did not record the source: {syns[0]}"
