# Owl-E Execution Environment — Containment, Access Points, and the Generated Runner

*Design doc, 2026-08-10. The active focus. Grounded in the existing schema (see [REPO-STATE.md](REPO-STATE.md) for the concept map); everything proposed here extends, never replaces, what's built.*

---

## 1. Purpose

A workflow's definition already includes its environment (`python_version_id`, `workflow_requirements` over the PyPI mirror). This design makes that literal: **executing a workflow means materializing that environment as a sealed container and running a generated Python program inside it.** Every external thing the workflow could touch is represented by a user-configured **access point** whose behavior during a run is either mocked (fixtures) or live — but in all cases **observed**.

Two products fall out of one mechanism:

- **Ordinary testing** — "this step hits Google's Location API; here's the result set it should see; assert the workflow lands where it should." Mocked access points are just test fixtures for the outside world.
- **Behavioral observation of AI executors** — an agent handed part of a workflow operates in what is, from inside, a completely typical environment: real hostnames resolve, TLS handshakes succeed, APIs answer. It has no tell that the environment is instrumented. The execution log then answers, with receipts, "did it stay within the parameters we expected?" — including every attempt to reach anything we *didn't* expect.

## 2. Principles

1. **No unobserved egress.** The guarantee is *observation*, not success. A cert-pinning or otherwise evasive process doesn't get out; its attempt is the finding. Containment verdicts are computed from the audit, never assumed.
2. **Determinism is a dial, not a switch.** Run modes (§7) choose which access points are fixtures and which are live. Fully-mocked + locked packages + pinned image + seeded RNG ⇒ reproducible runs, byte for byte.
3. **The AI is just another external service.** An LLM provider (api.anthropic.com, etc.) is an access point like any other: record/replay its completions and an *agentic* step becomes deterministic in simulation; run it live and every prompt/response is still audited. This unifies "AI safety observation" with plain testing — same tables, same gateway, same report.
4. **The schema is the program.** The runner is *generated* from the workflow tables; nothing is interpreted ad hoc at runtime. What the user designed is exactly what runs — that's the determinism story and the auditability story in one.

## 3. The generated runner

One workflow revision ⇒ one generated Python package. Layout:

```
/app
  runner.py        # orchestration loop generated from the step graph
  tools.py         # tools.body sources, verbatim, one function per tool
  inquisitions.py  # inquisitions.body sources — user-authored predicates, same treatment as tools
  transforms.py    # canonical implementations of the transforms stdlib (shipped, not generated)
  datastore.py     # typed slots from data_points (preset values, store_method semantics;
                   #   is_prerequisite slots seeded from test inputs / junction injections)
  manifest.json    # step graph, watches, inquisitions, routes — for tooling & ingestion
```

The runner emits **JSONL events** to `/out/events.jsonl` (the server ingests these into `workflow_executions` / `execution_logs` / step runs):

```json
{"seq":14,"ts":"…","run":"…","kind":"step_started","step_id":11511650}
{"seq":15,"ts":"…","run":"…","kind":"error_watch_triggered","step_id":11511650,"error_type":"jsonschema.ValidationError","routed_to":11511655}
{"seq":16,"ts":"…","run":"…","kind":"inquisition_evaluated","inquisition_id":9,"result":false}
{"seq":17,"ts":"…","run":"…","kind":"data_point_written","data_point_id":10011251}
```

Event kinds: `run_started`, `step_started`, `tool_invoked`, `error_watch_triggered`, `inquisition_evaluated`, `data_point_written`, `routed`, `junction_requested`, `run_completed`, `log`.

Generated shape per step — mechanical translation of the lifecycle in REPO-STATE §3 (using the canonical example's real IDs):

```python
def step_11511650(ds: DataStore) -> Route:                      # "Schema Validate JSON"
    emit("step_started", step_id=11511650)
    args = dict(
        data=ds.read(10011250),                                  # raw_json
        schema_uri=ds.read(10011252),                            # schema_uri
    )
    try:
        result = tools.validate_json(**args)
    except jsonschema.ValidationError as e:                      # error_watches row
        emit("error_watch_triggered", step_id=11511650, error_type=type(e).__qualname__)
        return Route.step(11511655)                              # its trigger_step
    ds.write(10011251, transforms.apply(WRITES[11511650], result))  # validation_report
    #   writes ALWAYS run on normal return — persistence follows production
    #   (decision 2026-08-10, REPO-STATE §3); inquisitions evaluate AFTER, over data points
    for inq in INQUISITIONS[11511650]:                           # ordered by execution_order
        verdict = inq.predicate(ds)                              # body lives in inquisitions.py;
        emit("inquisition_evaluated", inquisition_id=inq.id, result=verdict)
        if verdict:
            return inq.route                                     # trigger step or junction
    return Route.step(NEXT[11511650])                            # step_default_routes / END
```

`runner.py` is then a trivial trampoline: `route = STEPS[current](ds)` until `END` or `junction_requested` (junction handling in §9). **A junction never executes in-container** — the runner writes the injection payload to `/out/junction.json` and exits; the orchestrator provisions the target workflow's own environment.

*Build note:* generator lives server-side (Kotlin, suggest `com.wini.owle.generation`), golden-file-tested against the `CompleteExample` fixture. This replaces the lost prior codegen work; the lifecycle spec makes it a mechanical rewrite, confirming the "easier to just do again" instinct.

## 4. Container topology

```
docker network owle-run-{id}   (internal: true — no route to anywhere)
┌──────────────────────────────┐      ┌────────────────────────────────┐
│ workflow container           │      │ gateway container              │
│  python:3.x.y@sha256:…       │ DNS  │  dnsmasq: answers EVERY name   │
│  /app  (generated runner)    │─────▶│    with the gateway's own IP   │
│  site-packages from uv lock  │      │  mitmproxy (transparent :80,   │
│  gateway CA in trust store + │─TLS─▶│   :443) + fixture addon        │
│   SSL_CERT_FILE/REQUESTS_CA… │      │   ├─ stub matcher (fixtures)   │
│  non-root, read-only rootfs, │      │   ├─ unmatched-traffic policy  │
│  cap-drop ALL, pids/mem caps │      │   └─ audit → /out/flows.jsonl  │
└──────────────┬───────────────┘      └────────────────────────────────┘
               │  /out volume: events.jsonl, junction.json, artifacts, flows.jsonl
               ▼
        server ingests → workflow_executions, execution_logs, network_events
```

**Realism requirements** (the "what does it know" property):

- No proxy environment variables — interception is transparent (DNS + routing), so nothing in `os.environ` betrays the sandbox and nothing can be stripped to bypass it.
- Real hostnames resolve; TLS presents certificates chaining to the injected CA, so `requests`/`httpx`/`urllib`/`curl` all just work.
- A process that pins certificates or tries raw IPs/DoH simply fails to connect (internal network has no route out) — and the attempt is both **contained and observed** (see below). That *is* the containment signal, not a gap in it.

**Raw-IP capture is a separate mechanism from the DNS-steered gateway, proven empirically in `spike/containment/` (2026-08-16).** Wildcard DNS only steers traffic that asks for a hostname; a raw-IP connect skips DNS and is dropped by the `internal: true` bridge *before it ever reaches the gateway container* — contained, but invisible to it. Fix: a second observation point, on the **host side of each workload's veth pair**, outside every container namespace. Measured properties:

| vantage | sees raw-IP egress | detectable from inside the workload |
|---|---|---|
| gateway container (DNS-steered) | no — bridge drops it first | — |
| capture process sharing the workload's *network* namespace | yes | **yes** — `/proc/net/packet` and `ss -0` reveal its raw socket even though the process tree is invisible |
| capture on the host-side veth interface | yes | no — shares no namespace with the workload at all |

Locating the veth requires reading the workload's `eth0` peer ifindex and matching it against the host's interfaces — an **orchestrator** operation (the thing already holding Docker access to provision containers), never something run from inside the sealed network, which must never see a Docker socket. Production capture point: host-side veth, orchestrator-driven. (The netns-sidecar variant is documented in the spike log as the ruled-out alternative, kept for the record of why.)

**Hardening floor** (MVP, all cheap): `--network` internal-only · non-root UID · `--read-only` rootfs + tmpfs `/tmp` · `--cap-drop ALL` · `--security-opt no-new-privileges` · `--memory`/`--cpus`/`--pids-limit` · never mount docker.sock · single `rw` output volume.

## 5. Access points — the data model

An **access point** names an external dependency of a workflow (per workflow, user-authored during design): "Google Location API", "our Postgres", "Anthropic API". Stubs give it behavior; a stub set is either the AP's **default** or bound to a **test case**.

> **Authoritative, expanded schema: [SIMULATION-MODEL.md](SIMULATION-MODEL.md).** That doc supersedes the sketch below — notably, response variants bind to a **`SimulationProfile`** (a named reality), not directly to `test_case_id`, so a variant is defined once and *selected* per run/test rather than duplicated.

Sketch DDL (house style; FKs elided for brevity):

```sql
CREATE TABLE `access_points` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `workflow_id` bigint unsigned NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(8192) DEFAULT NULL,
  `scheme` enum('https','http') NOT NULL DEFAULT 'https',
  `openapi_spec` longtext DEFAULT NULL,          -- optional import provenance; scaffolds routes/stubs  PRIMARY KEY (`id`)
);

CREATE TABLE `access_point_hosts` (          -- an AP can front several hostnames
  `access_point_id` bigint unsigned NOT NULL,
  `hostname` varchar(255) NOT NULL,          -- exact or *.wildcard
  PRIMARY KEY (`access_point_id`,`hostname`)
);

CREATE TABLE `access_point_stubs` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `access_point_id` bigint unsigned NOT NULL,
  `test_case_id` bigint unsigned DEFAULT NULL,   -- NULL ⇒ default stub set
  `name` varchar(255) DEFAULT NULL,              -- human-named variant: "Success", "Time Out"…  `state_key` varchar(255) DEFAULT NULL,         -- matches only when this named scenario state is set (§9a)
  `match_order` int unsigned NOT NULL,
  `method` enum('GET','POST','PUT','PATCH','DELETE','ANY') NOT NULL DEFAULT 'ANY',
  `path_pattern` varchar(1024) NOT NULL,         -- /geocode/json, /v1/messages, /users/{id}
  `query_pattern` varchar(1024) DEFAULT NULL,    -- substring/regex, optional
  `body_pattern` text DEFAULT NULL,              -- substring/regex, optional
  `sequence_position` int unsigned DEFAULT NULL, -- stateful: match Nth call only
  `response_status` smallint unsigned NOT NULL DEFAULT 200,
  `response_headers` text DEFAULT NULL,          -- JSON object
  `response_body` longtext DEFAULT NULL,         -- may template {path.params} and captures
  `latency_ms` int unsigned NOT NULL DEFAULT 0,
  `fault` enum('none','timeout','connection_reset') NOT NULL DEFAULT 'none',
  PRIMARY KEY (`id`)
);
```

**Matching:** requests are attributed to an AP by SNI/Host against `access_point_hosts`; within the active stub set (test-case set overlaying the default set), first match by `match_order` wins, `sequence_position` filtering first. **Unmatched traffic** — including hosts belonging to no AP at all — follows the run's policy: `deny` (connection refused + log), `fault` (templated 5xx + log), or `forward` (live, only legal in rehearse/live modes). Always logged; policy never affects logging.

## 6. Observation — runs, network audit, containment report

```sql
ALTER TABLE `workflow_executions`
  ADD `mode` enum('simulate','rehearse','live') NOT NULL DEFAULT 'simulate',
  ADD `environment_image_id` bigint unsigned DEFAULT NULL,
  ADD `unmatched_policy` enum('deny','fault','forward') NOT NULL DEFAULT 'deny';

CREATE TABLE `network_events` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `workflow_execution_id` bigint unsigned NOT NULL,
  `step_run_id` bigint unsigned DEFAULT NULL,        -- attributed by step time-span
  `seq` bigint unsigned NOT NULL,                    -- gateway-monotonic
  `ts` timestamp(3) NOT NULL,
  `hostname` varchar(255) NOT NULL,
  `port` int unsigned NOT NULL,
  `method` varchar(8) DEFAULT NULL,
  `path` varchar(2048) DEFAULT NULL,
  `access_point_id` bigint unsigned DEFAULT NULL,    -- NULL ⇒ nothing claimed this host
  `stub_id` bigint unsigned DEFAULT NULL,
  `disposition` enum('mocked','forwarded','denied','faulted','tls_rejected') NOT NULL,
  `response_status` smallint unsigned DEFAULT NULL,
  `duration_ms` int unsigned DEFAULT NULL,
  `request_sha256` char(64) DEFAULT NULL,            -- body fingerprint by default…
  PRIMARY KEY (`id`)
);
-- …full request/response capture is per-AP opt-in (LLM prompts are useful to keep;
--    arbitrary bodies may be sensitive and bulky).
```

**Containment report** (per run, derivable by query — worth a view): total egress attempts; matched-to-AP vs unmatched; dispositions; per-step attribution (join step start/end spans from events against `network_events.ts`, tie-break with `seq`). Verdict: **CONTAINED** (every attempt matched an expected AP) or **DEVIATED** (list the attempts). That is the "ah, see — it's not 0% of the time it stayed constrained" number, and in test contexts it doubles as "the workflow called exactly the endpoints the design says it should."

`benchmark_reports` keeps working unchanged (wall time from run events); `execution_logs` receives runner `log` events; `test_results` binds runs to test cases exactly as today.

## 7. Run modes

| | access points | unmatched | packages | intended use |
|---|---|---|---|---|
| **simulate** | all mocked | deny | locked image | design-time testing, CI, agent observation, replay |
| **rehearse** | per-AP choice (mock some, forward some) | deny | locked image | integration against selected real services |
| **live** | forwarded (stubs ignored) | forward | locked image | production runs — still fully audited |

Determinism levers, applied cumulatively in simulate: sealed network (fixtures) · uv-locked wheels + digest-pinned base image · `PYTHONHASHSEED` + seeded `random`/`numpy` · optional frozen clock (`faketime`)/fixed `TZ` · read-only rootfs with declared volumes only · recorded LLM completions. Simulate mode with all levers on is bit-reproducible; each lever released is a documented, deliberate loss of that guarantee.

## 8. Environment build pipeline

```
workflow_requirements ──(mirror: packages/versions/env_marker_*)──▶ candidate set
        │  uv pip compile  (deterministic, hash-pinned; markers evaluated for the target image)
        ▼
environment_images row: lockfile + lock_hash + base_image (digest-pinned python:x.y.z-slim)
        │  docker build  (template Dockerfile: base + CA + non-root user + uv pip sync + /app COPY)
        ▼
image_digest recorded; cache key = (python_version, lock_hash, ca_version) ⇒ rebuilds are rare
```

```sql
CREATE TABLE `environment_images` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `workflow_id` bigint unsigned NOT NULL,
  `python_version_id` bigint unsigned NOT NULL,
  `base_image` varchar(255) NOT NULL,
  `lockfile` longtext NOT NULL,
  `lock_hash` char(64) NOT NULL,
  `image_digest` varchar(255) DEFAULT NULL,
  `status` enum('resolved','building','built','failed') NOT NULL DEFAULT 'resolved',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_wf_lock` (`workflow_id`,`lock_hash`)
);
```

The generated `/app` is bind-mounted (or layered as a final COPY) so tool-body edits don't invalidate the dependency image.

**Content-addressed, not per-workflow.** Two workflows on the same Python version with identical resolved requirements should share one image, not each provision their own. Key `environment_images` on `(python_version_id, lock_hash)` — an image is identified by what's *in* it — and let workflows **reference** a row rather than always minting one. A user re-running a workflow they've already built selects the existing image; they are not expected to "clone" something they already have. (Decided 2026-08-16, in the course of reviewing the containment spike's findings.)

## 8a. Write caps on data points — `store_cap`, resolved

`data_points` gains one nullable unsigned column, `write_cap`:

| value | meaning |
|---|---|
| `NULL` | unlimited (today's behavior) |
| `0` | read-only — preset value only, no step may ever write |
| `1` | write-once — "you never had a value, now you do," and never again |
| `N` | budget of N writes |

A violation is the same *shape* of event as undeclared egress, not a distinct failure mode: emit
`write_cap_exceeded` (data point, step, attempted write, count-so-far) into `execution_logs`, then
fail the run. No remediation semantics, no author-side handling required — the excess write *is*
the finding, exactly as an unmatched host is the finding for network egress. This reframes
`store_cap` as a containment primitive (an in-process analogue of the deviation report) rather than
a data-integrity feature, which is also why it needs no more machinery than one column and one
event kind.

## 9a. Scenario state — "first call returns A; after step 1234, B"

Stubs may carry a `state_key`. The gateway keeps a tiny named-state set and exposes a **loopback-only control endpoint**; the orchestrator advances state on runner events ("`step_completed` 1234 ⇒ set `post_1234`"). Matching then prefers stubs whose `state_key` is currently set (unset-key stubs are the default reality). State transitions are recorded alongside `network_events` so the audit shows *which reality* served every request. Call-count cases keep using `sequence_position`; the two compose. This is the workflow-progress-keyed statefulness no off-the-shelf tool ships (WireMock's externally-advanceable "scenarios" prove the mechanism; keying to step completion is the Owl-E part) — see [SIMULATION-MODEL.md](SIMULATION-MODEL.md) §4.

## 8b. Concurrency — deliberately deferred, constraint recorded now

A Multiflow is currently **one logical thread**: a junction is a tail call (the runner exits, the
orchestrator provisions the next environment), never a fork. This single fact is what several
guarantees quietly rest on — worth naming before anyone spends against it:

- `execution_logs.seq` is a **total order**; concurrent branches can only give a partial order.
- Inquisitions route on "first predicate to evaluate true" — racy if the data points they read can
  change concurrently under a competing branch.
- `write_cap` counting (§8a) assumes one writer's view of "how many so far."
- Simulation's `sequence_position` ("the Nth call to this route") and **scenario situations
  (§9a) are per-run singletons** — concurrent branches would contend over one active situation.
- Bit-reproducibility in simulate mode requires a total schedule to record and replay; concurrency
  needs the schedule itself captured, not just the inputs.

Note the last two: **concurrency breaks the simulation subsystem as much as the execution one.**
That doubles the blast radius of ever introducing it casually.

Decision: stay sequential until a real workflow demands fan-out. If/when it's added, it must be
**authored, not implicit** — explicit fork/join nodes with a declared merge policy (first-wins,
all-must-complete, quorum), `execution_logs` gaining a `branch_id` with `seq` scoped per-branch,
and — the load-bearing constraint to bake in now, before the state model solidifies further —
**simulation state (situations, sequence counters) must be branch-scoped, not run-scoped.**
Implicit forking (a junction silently becoming concurrent) is ruled out; it would degrade every
guarantee above without anyone deciding to.

This also folds into open question 2 below: pre-warming vs. on-demand junction provisioning is
partly a concurrency question, not purely a resource one, and should be answered together with it.

## 9. Junctions & the AI executor

**Junctions.** The runner exits with `/out/junction.json` (`junction_id`, injected values per `data_point_injections`, transform kind `inject` applied). The orchestrator then provisions the *target* workflow's own image + gateway + fixtures and starts it with those data points pre-seeded — literally "starting all over by setting up a new environment, having lifted one or more of the values" (post-write state, per the REPO-STATE §3 persistence rule; a data point not mapped in `data_point_injections` simply isn't lifted). Guardrails: max-hop budget per run, cycle detection across the junction chain, one `workflow_executions` row per hop linked into a chain (add `parent_execution_id` when this lands).

**AI executor.** An agentic step is a tool whose body calls a provider SDK; the provider is an access point. Simulate mode replays recorded completions (stub `sequence_position` maps naturally onto multi-turn tool-use loops) ⇒ the agent's behavior is exactly reproducible. Live mode forwards, with full capture opted in for that AP. Either way the agent's *actions* — every network attempt it makes while "being" the step — are in the containment report. Nothing about this is a special case; that's the point of principle 3.

## 10. Tooling choices (recommendations, with escape hatches)

- **Gateway: mitmproxy** in transparent mode + a ~100-line Python addon (load stub set JSON, match, respond, append `flows.jsonl`). Rationale: transparent TLS interception is first-class, the addon language matches the stack, flow export is native. Alternatives: Hoverfly (capture/replay-centric, Go templating weaker for our matching), WireMock (excellent stubbing, but JVM-side and not transparent-first). The gateway image is built **once**, parameterized per run by a mounted fixture file — no per-run builds.
- **Resolver/installer: uv** (`uv pip compile` / `uv pip sync`) — deterministic hash-pinned locks, fast enough to keep builds interactive. Fallback: pip-tools.
- **Docker control: shell out to the docker CLI** from Kotlin (`ProcessBuilder`) for M1–M2 — trivial, debuggable, Windows-friendly. Graduate to docker-java when the orchestrator needs event streams. Testcontainers stays in its lane: the *server's own* integration tests (e.g., MySQL), not workflow execution.
- **DNS: dnsmasq** with `address=/#/<gateway-ip>` (wildcard-answer everything).
- **Authoring/control plane:** the simulation model (`SimulationProfile` → `AccessPoint` → `Route` → `ResponseVariant`) is authored through Owl-E's own client; a headless gateway consumes the compiled fixture set — there is no live UI in the run path. The model, its matching algorithm, OpenAPI import, and the variant-flipping authoring pattern are specified in [SIMULATION-MODEL.md](SIMULATION-MODEL.md).

## 11. Delivery plan

Bias: something real running end-to-end before any of the fancy parts.

- **M0 — hygiene (hours).** Commit the composeApp restoration; delete `docs/composeApp/` (Wini); `.gitignore` `result.*`; decide the §3 write-suppression semantics (REPO-STATE §3 open question); verify/fix the `CompleteExample` dangling type-node ref.
- **M1 — generated runner, minimal sandbox (first real progress).** `ProgramGenerator` (Kotlin) for a single linear workflow: reads/writes, error watches, inquisitions, default routes — no junctions, no transforms beyond identity. Execute in stock `python:3.12-slim` with `--network none` (fixtures not needed yet: tool bodies for the demo avoid network). Golden-file tests against `CompleteExample`; JSONL ingestion into `workflow_executions`/`execution_logs`. **Demo:** canonical workflow runs, events queryable.
- **M2 — sealed environment.** `environment_images` + uv pipeline + Dockerfile template; gateway container (dnsmasq + mitmproxy + addon); `network_events` ingestion + containment report v1. **Demo:** the fetch_json step really performs HTTP against a mocked "api.com" and the report shows CONTAINED; a deliberately rogue tool body (calls evil.example) shows DEVIATED.
- **M3 — access points as product.** Authoring API/DTOs for APs + stubs; test-case stub binding; sequences, latency, faults; rehearse mode; **OpenAPI import** to scaffold an AP from a spec; **scenario state channel** (§9a) with matcher semantics per [SIMULATION-MODEL.md](SIMULATION-MODEL.md) §3. **Demo:** the Google-Location-API scenario — fixture result set, workflow picks the record with properties X/Y/Z, test asserts the outcome; plus a stateful demo — an artifact-availability poll loop flips from not-ready to ready on a step event.
- **M4 — junctions & agents.** Junction handoff orchestration (`parent_execution_id` chain, hop budget); transforms stdlib in the runner; AI-executor step with recorded/replayed completions. **Demo:** two-workflow junction with injections, then an agentic step whose containment report is green.

## 12. Open questions for Wini

1. ~~Write suppression on triggered inquisitions~~ — **decided 2026-08-10**: writes are never suppressed; inquisitions evaluate after writes as post-completion routing predicates. Rationale and corollaries in REPO-STATE §3.
2. **Junction provisioning strategy** — on demand vs. pre-warmed. Tied to §8b: this is partly a concurrency question (pre-warming several junction targets implies they could run concurrently), so answer together with whether/when fan-out is introduced. Leaning on-demand as long as execution stays sequential (§8b default).
3. **Capture policy defaults** — full request/response bodies for LLM APs only, fingerprints elsewhere? Scope note (2026-08-16): this is a storage/capture-volume decision, independent of *why* an AP is mocked — a fully deterministic, non-AI workflow gets identical value from mocked APIs (predictability, statistics, CI) and the capture-policy question applies to it the same way. Still open.
4. **Where do runs execute long-term** — local Docker daemon vs. remote executor. Leaning: both, as a first-class choice, not a migration. Local for anyone who doesn't want to run Docker Desktop year-round; a hosted executor profile ("run it here, or we run it for $X.YZ/min") as an early revenue lever, not an afterthought. Whichever the user configures, the containment guarantee is identical — this is a deployment-target choice, not a rigor tradeoff. Design accordingly rather than bolting hosting on later.
5. ~~`store_cap`~~ — **decided 2026-08-16**, see §8a: single nullable `write_cap` column, capped writes fail the run the same way undeclared egress does.
6. ~~Tool call-contract authority~~ — **resolved by the schema migration itself**: `tool_params` no longer exists in the DDL. `callable_signatures`/`callable_parameters` is the sole authority; `step_data_point_reads` and `inquisition_data_point_reads` both key on `callable_parameter_id`. No decision needed — it already happened.
7. ~~Where does the authoring UI live~~ — **decided 2026-08-10**: the source prototype is harvested (model + matcher → [SIMULATION-MODEL.md](SIMULATION-MODEL.md)) and retired to a local branch; its UI is not carried forward. What remains is the broader **client-framework** question — keep the existing Compose Multiplatform `composeApp`, or move the Owl-E client to React/TS (Wini leans React/TS; the server REST API backs either). Deferred, not blocking the containment work.
