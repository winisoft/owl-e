# Containment Spike

*Started 2026-08-15. A standalone experiment that answers one question and then gets out of the way.*

## The question

> Can a workflow container be sealed so that **every** outbound network attempt is intercepted,
> answered from fixtures, and audited — while the code inside sees an entirely ordinary
> environment and has no tell that it is being watched?

If yes, the containment and simulation ambition in
[EXECUTION-ENVIRONMENT.md](../../docs/EXECUTION-ENVIRONMENT.md) is real and everything after it is
elaboration. If no, we found out in a day for the price of ~200 lines.

**This spike deliberately touches nothing else in Owl-E.** No schema, no Kotlin, no Gradle, no DTOs.
The server is mid-refactor with a large downstream error count; tying a viability question to that
build state would tell us nothing about viability. It is plain Docker and plain Python.

## What "no tell" means, concretely

These are the three claims under test. Each is an assertion in `test_containment.py`:

| # | Claim | How it's proven |
|---|---|---|
| 1 | A **declared** host is served from fixtures over TLS the client trusts | `requests.get(...)` with default `verify=True` returns our fixture body. No `verify=False`, no warnings suppressed. |
| 2 | An **undeclared** host is intercepted and logged, not silently dropped | The attempt appears in `flows.jsonl` with `disposition: unmatched`. |
| 3 | The workload cannot tell it is sandboxed | No proxy variables in `os.environ`, and a request to a **real, live** external hostname is answered by our gateway rather than the internet. |

Claim 3 is the one that separates this from an ordinary mock server. A mock server you *point at*
is trivial. The requirement here is that pointing is not necessary and evasion is not possible.

## How it works, piece by piece

```
docker network "sealed"  (internal: true — genuinely no route off the host)
┌────────────────────────────┐          ┌──────────────────────────────────┐
│ workload                   │   DNS    │ gateway            172.28.0.10   │
│  python:3.12-slim          │─────────▶│  dnsmasq: address=/#/172.28.0.10 │
│  requests, default verify  │          │   (every name → this box)        │
│  CA installed into system  │   TLS    │  iptables REDIRECT :443 → :8080  │
│   store + certifi bundle   │─────────▶│  mitmdump --mode transparent     │
│  NO proxy env vars         │          │   --set upstream_cert=false      │
│                            │          │   -s addon.py                    │
└────────────┬───────────────┘          └──────────────┬───────────────────┘
             │                                         │
             └──────────── ./out ──────────────────────┘
                    probe.json, flows.jsonl
```

Each piece earns its place:

- **`internal: true`** — the network has no route to anywhere. This is what makes containment a
  property of the topology rather than of the gateway behaving well. Even a total failure of the
  addon cannot leak traffic.
- **dnsmasq wildcard** — `address=/#/172.28.0.10` answers *every* DNS query with the gateway's
  address. This is why the workload needs no configuration: `api.stripe.com` simply resolves to the
  gateway, as does everything else.
- **iptables REDIRECT** — traffic arriving at the gateway on 80/443 is redirected into mitmproxy's
  transparent listener. Doing the capture at the packet layer rather than the DNS layer is what
  will later let us observe raw-IP attempts too, not just resolved hostnames.
- **`upstream_cert=false`** — normally mitmproxy contacts the real server to copy its certificate
  details before minting one. On a sealed network there *is* no real server to contact, so we tell
  it to mint purely from the TLS SNI. Without this flag nothing works.
- **CA into the system store *and* certifi** — Python's `requests` ignores the system store and
  uses `certifi`'s bundle, so both must be updated. The alternative — setting `REQUESTS_CA_BUNDLE`
  — would be a visible tell in `os.environ`, which is exactly what claim 3 forbids.
- **the addon** — attributes host → access point, matches the route, serves the variant, and writes
  an audit line. The unmatched branch is not an error path; it is the product.

## Deviations from the real design (deliberate, for the spike only)

- The CA is generated at first gateway start into a shared volume and installed by the workload at
  container start. The real pipeline bakes it in at **image build** time (EXECUTION-ENVIRONMENT §8),
  which is stricter. The property under test — no env vars, clean `verify=True` — is identical.
- One access point, one route, one variant. No `SimulationProfile`, no situations, no state
  transitions. Those are elaboration on a mechanism that has to work first.
- Fixtures are a hand-written `fixtures.json` rather than compiled from the database.

## Running it

```bash
cd spike/containment && python -m pytest test_containment.py -v
```

## Log

Written as it happened, failures included.

<!-- LOG:START -->

### 2026-08-15 — built and green

Two self-inflicted errors while scaffolding, both mine, both trivial: the compose file opened
with a markdown blockquote instead of a YAML comment, and I briefly thought I'd written the
dnsmasq config to a filename with a trailing newline (I hadn't — the path got trimmed).

Then the suite passed 9/9 on the first run, which is precisely when a result deserves the least
trust. So before believing it, I read the raw artifacts rather than the assertions:

```
declared            → 200  {"widgets":[{"id":1,"name":"SPIKE-FIXTURE-OK"}], …}   tls_ok: true
declared_with_param → 200  {"id":"42", …}          ← {id} captured and templated
undeclared          → 403  {"error":"undeclared access point","host":"evil.undeclared.test"}
real_external_host  → 403  {"error":"undeclared access point","host":"pypi.org"}
proxy_env           → {}
```

All four resolved to `172.28.0.10`. **`pypi.org` — a real host that genuinely exists — was
answered by our gateway.** Nothing left the sandbox.

And the audit accounted for all four, in order, each attributed:

```json
{"seq":1,"hostname":"api.declared.test","path":"/v1/widgets","access_point":"Declared Widget API","variant":"Success","disposition":"mocked","response_status":200}
{"seq":3,"hostname":"evil.undeclared.test","path":"/exfil","access_point":null,"disposition":"unmatched","response_status":403}
{"seq":4,"hostname":"pypi.org","path":"/simple/","access_point":null,"disposition":"unmatched","response_status":403}
```

### Making it falsifiable

A green suite is worthless if it would stay green when the mechanism breaks. The claim most
worth doubting was `tls_ok: true` — that could just mean verification wasn't happening.

So: the workload entrypoint now snapshots the stock `certifi` bundle *before* adding the gateway
CA, and the probe repeats the declared request against that pristine bundle. It fails, exactly
as it must:

```
SSLCertVerificationError: [SSL: CERTIFICATE_VERIFY_FAILED]
    certificate verify failed: unable to get local issuer certificate
```

That is now a permanent test (`test_suite_is_falsifiable`). If interception ever stops being
real, the suite goes red instead of quietly lying. Note that this attempt produces **no** flow
record — it dies at the TLS handshake, before any request exists to audit. Correct behaviour.

Also fixed: the test harness decoded Docker's UTF-8 output using the Windows cp1252 default and
threw mid-stream, which would have swallowed diagnostics on a real failure.

**Final: 10 passed. ~11s warm, ~70s cold.**

### Verdict

The mechanism works, on this machine, today.

What is now demonstrated rather than asserted:

- A sealed container reaches nothing. Containment is a property of the network topology, not of
  the gateway behaving correctly.
- Ordinary Python — plain `requests`, default `verify=True`, zero configuration — transparently
  receives fixtures over TLS it genuinely trusts.
- There is no tell. No proxy variables, no CA variables, no client cooperation of any kind.
- Every attempt is attributed and logged, and undeclared hosts produce exactly the deviation
  record a containment report would be built from.

What this does **not** yet prove, stated plainly so it isn't quietly assumed later:

1. **Raw-IP egress is untested.** *(Closed the same day — see below.)*
2. Fixtures are hand-written here, not compiled from a `SimulationProfile`.
3. No situations, no state transitions, no scenario progression.
4. The CA is installed at container start rather than baked at image build.

None of those are viability questions. They are the build.

### 2026-08-16 — closing the raw-IP blind spot

The open question: wildcard DNS can only steer traffic that asks for a name. What happens to a
connection opened straight to a literal IP — is it merely contained, or actually *observed*?

**Measurement 1 — the blind spot is real.** From the workload, `1.1.1.1:443`:

```
ENETUNREACH in 0.00s          flows.jsonl: 4 before, 4 after
```

Instant local failure. The container has **no default route at all** — Docker installs none on an
internal network — so the kernel rejects the connection before emitting a single packet.
Containment is airtight at a lower level than expected. Observation is zero.

**Measurement 2 — routing to the sentinel doesn't fix it.** Gave the workload a default route via
the gateway container. Behaviour changed (instant `ENETUNREACH` became an 8s timeout, so packets
were now being emitted) but the gateway's `nat PREROUTING` counters stayed at **0 packets**. The
packets were leaving the workload and dying before reaching the sentinel.

**Measurement 3 — isolating the cause.** Flipped the network to `internal: false` and repeated:

```
internal: true   →  REDIRECT counter 0 packets   raw-IP invisible
internal: false  →  REDIRECT counter 2 packets   raw-IP intercepted
```

So `internal: true` is precisely the cause. The host discards packets with a non-local destination
leaving that bridge, before they can be switched to the gateway container.

That is a genuine structural tradeoff, and it is not one worth taking:

| | `internal: true` | `internal: false` + gateway as route |
|---|---|---|
| hostname traffic | intercepted + observed | intercepted + observed |
| **raw-IP traffic** | **blocked, unobserved** | intercepted + observed |
| containment rests on | **topology** — no route exists | configuration continuing to hold |

Trading a structural guarantee for an observational one is a bad trade. So: keep both, by
observing somewhere the gateway can't reach.

**The fix — a capture sidecar in the workload's network namespace.** A separate container,
`network_mode: service:workload`, running `tcpdump`. It watches at the workload's own interface,
which is *upstream* of the bridge that drops the packets. The workload cannot disable it: separate
PID and mount namespaces, sharing only the network namespace.

With `internal: true` fully intact, the sentinel still at 0 packets:

```
08:05:42  eth0 Out IP 172.28.0.2.55736 > 1.1.1.1.443:  Flags [S]   (×4 retries)
08:05:57  eth0 Out IP 172.28.0.2.40294 > 1.1.1.1.80:   Flags [S]   (×4 retries)
08:06:12  eth0 Out IP 172.28.0.2.51214 > 1.1.1.1.9999: Flags [S]   (×4 retries)
```

85 packets captured. Direction, source, destination, port, timestamp. Contained *and* observed.

Two of my own tooling bugs surfaced on the way and are worth recording because both would have
produced a confident wrong answer:

- A `cd` in an earlier shell call left the working directory wrong, so two `gradlew` invocations
  produced no output at all — which I nearly read as "zero errors."
- Git Bash rewrote `/out/tap.pcap` into `C:/Program Files/Git/out/tap.pcap`, so the first capture
  attempt silently wrote nothing and reported `0 packets captured`. That looked exactly like a
  real finding. It was `MSYS_NO_PATHCONV=1`.

**Final: 14 passed.** The architecture that survives:

```
internal: true            → structural containment; nothing can leave, whatever the software does
wildcard DNS + mitmproxy  → hostname traffic intercepted, fixtures served, HTTP-level audit
capture sidecar in netns  → packet-level observation, including evasion the gateway cannot see
```

Three tests now pin the limitation itself rather than hiding it:
`test_raw_ip_egress_does_not_succeed`, `test_raw_ip_attempts_never_reach_the_gateway` (asserts the
sentinel's blindness, so a topology change that breaks this assumption is loud), and
`test_raw_ip_attempts_are_observed_by_the_capture_sidecar`.

### 2026-08-16 — the observer was detectable

Claimed in the previous entry: "the workload cannot stop it — separate PID and mount namespaces."
True, and beside the point. I never checked whether the workload could *detect* it.

It could. `/proc/net/packet` is keyed by **network** namespace, not process namespace, so sharing
only the network namespace is enough to leave a trace:

```
tap stopped:   sk  RefCnt Type Proto Iface R Rmem User Inode        (empty)
tap running:   00000000acf3b915  3  2  0003  0  1  0  0  26915      ← a raw socket
ss -0:         p_dgr  0  0  *:*  *                                  ← visible here too
```

Process isolation did hold — the workload's `/proc` shows only its own PIDs (1, 9, 54, 60, 61);
tcpdump's is genuinely absent — and `-i any` correctly left `promiscuity` at 0. But a process that
knows to read `/proc/net/packet` sees a raw socket it did not open. For a design whose whole
premise is that the workload has no tell, that is a real crack.

**Fix: capture from the host side of the veth pair.** Every container interface is half of a veth
pair; the other half lives in the host's namespace. Capturing there shares *nothing* with the
workload. Locating it means reading `eth0`'s peer ifindex from inside the container and matching it
against the host's interfaces:

```
docker exec <workload> cat /sys/class/net/eth0/iflink   →  7
host: /sys/class/net/veth*/ifindex == 7                 →  veth673315f
```

Then capturing on `veth673315f` with `--net host`:

```
BEFORE:  /proc/net/packet empty · ss -0 empty · promiscuity 0
AFTER:   /proc/net/packet empty · ss -0 empty · promiscuity 0     ← unchanged
```

and it still sees everything:

```
10:00:54  IP 172.28.0.2.43270 > 1.1.1.1.443: Flags [S]
10:00:55  IP 172.28.0.2.43270 > 1.1.1.1.443: Flags [S]
```

**Undetectable and complete.** Three observation points, ranked:

| vantage | sees raw-IP | detectable by workload |
|---|---|---|
| gateway container | no — bridge drops first | no |
| sidecar in workload netns | yes | **yes** — `/proc/net/packet` |
| host-side veth | yes | no |

**Not yet automated.** Finding the veth requires reading `/sys/class/net/eth0/iflink` from outside
the container — an orchestrator operation. Nothing *inside* the sealed network can do it without
mounting the Docker socket, which the hardening floor forbids. That is the correct boundary rather
than a shortcut: the orchestrator already holds Docker access; the sealed network must never.

The committed spike still uses the netns sidecar, because it works under plain `docker compose`
and the detectability gap is now documented rather than hidden. Production should use the veth
vantage, driven by the orchestrator.

### Remaining, honestly

- UDP is not captured (filter is `tcp and not port 53`). QUIC/HTTP3 egress would be contained but
  unobserved. Widening the filter is a one-line change; deciding what to *do* with the volume is not.
- `tap.txt` is tcpdump text. Production wants these parsed into `network_events` alongside the
  gateway's own records, so the containment report has one timeline.
- Still no `SimulationProfile`, no situations, no state channel.

<!-- LOG:END -->
