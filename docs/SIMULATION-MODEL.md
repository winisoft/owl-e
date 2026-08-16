# Owl-E Air — the Simulation Model

*Design doc, 2026-08-10. The clean-room data model for "the reality a contained workflow sees." This is the forward authority for the access-point/fixture layer of [EXECUTION-ENVIRONMENT.md](EXECUTION-ENVIRONMENT.md) (§5/§9a); where they differ, this wins. Harvested and re-derived from an earlier mock-server prototype of Wini's — see the clean-room note in §8.*

---

## 1. Purpose

Inside the sandbox, a workflow (or an AI acting as one) consults external things: HTTP services, and ambient facts about the host it believes it's running on — files an `ls`/`cat` would reveal, environment variables, config values. **The simulation model is Owl-E's declaration of those things and the responses they give, per named reality.** The gateway (EXECUTION-ENVIRONMENT §4) serves from this model and audits every consultation against it.

Two products, one model: mocked realities are *test fixtures* for the outside world, and simultaneously the *expectation* a containment report is scored against.

## 2. Entity model

```
SimulationProfile        a named reality ("Happy Path", "All 501", "Slow Network")
   │  (selects/forces which response is active, per run/test)
   ▼
AccessPoint              an external dependency of a workflow  (kind: HTTP | AMBIENT)
   ├─ AccessPointHost    hostname(s) it fronts (exact or *.wildcard)  [HTTP]
   ├─ Route              a request template: method + /path/{param}   [HTTP]
   │     └─ ResponseVariant   a possible response ("Success","Timeout","501")
   │                          match: query/body/header/sequence/state
   │                          serve: status, headers, body, latency, fault
   └─ AmbientResource    a host fact (kind: FILE | ENV | KEYVALUE)     [AMBIENT]
         └─ AmbientValue       its content, per profile
```

**AccessPoint** — a named external dependency, a **library object** (optionally `user_id`-owned; `NULL` = system-provided), not owned by any one workflow — "Stripe" is a fact about the world, authored once, referenced by every workflow that uses it. `kind` = HTTP (hosts + routes) or AMBIENT (resources). Optional `openapi_spec` records import provenance so a whole surface can be scaffolded from a spec.

**WorkflowAccessPoint** — a workflow's *declaration* that it expects to use a given AccessPoint. This is the join the ownership move above needs: it's what the containment report scores observed egress against ("did this run only touch what the workflow declared?"), separate from the AP's own authoring. (Decided 2026-08-16 — the original per-workflow-owned sketch conflated "this exists" with "this workflow uses it.")

**Route** — the stable request surface under an HTTP access point: `method` (incl. `ANY`) + `path_pattern` with `{param}` segments (`/users/{id}`, `/v1/messages`). Defined once; scaffoldable from OpenAPI.

**ResponseVariant** — one possible response for a route (the unit an operator names and flips). **Match** fields are all optional and ANDed; an absent field is a wildcard:
- `query_pattern`, `header_pattern` — *subset* match (every specified key/value must be present in the request; the request may carry more).
- `body_pattern` — substring/regex.
- `sequence_position` — match only the Nth call to this route (call-count scenarios).
- `state_key` — match only while that named scenario state is set (§9a of EXECUTION-ENVIRONMENT; step-progress scenarios).
- `workflow_id`, `step_id` (both optional) — narrows a variant to firing only within a specific workflow, or a specific step of one, rather than everywhere the route is hit. (Added 2026-08-16.) Most stateful sequences are genuinely route-global — "the 2nd call to this endpoint, from anyone" — so these stay optional rather than becoming required scoping; the orchestrator already knows the current step from the state-channel events (§9a), so evaluating them costs nothing new.

**Serve** fields: `status`, `headers` (JSON), `body` (may interpolate captured `{param}` values and match captures), `latency_ms`, `fault` (`none|timeout|connection_reset`).

**Payload separation (decided 2026-08-16).** A single `response_variants` row was doing four jobs with four different lifecycles — authored config (changes at design time), a hot match predicate (evaluated every request), an immutable payload (should never change once a run has referenced it), and the thing a historical `network_events` row points back to. Collapsing them meant editing a fixture body could silently rewrite what a past containment report appears to have served. Split:

- `response_variants` keeps match fields + a payload reference. Small, hot, freely editable.
- `response_payloads` — bodies, content-addressed by SHA-256, immutable, deduplicated.
- the **compiled stub-set** the gateway loads (§6) gets its own hash.
- `network_events` (EXECUTION-ENVIRONMENT §6) references the compiled snapshot's hash and the variant id — never the live, editable config row.

Same principle `environment_images` already follows (a run names a lockfile + digest, not "whatever `workflow_requirements` currently says"): **the audit references immutable artifacts, never mutable configuration**, so a run stays reproducible and its receipts stay honest regardless of what gets edited afterward.

**SimulationProfile** — a named reality; the selector that decides *which* responses are live. One `is_default` per workflow. Carries an optional sweeping default (`default_disposition` + `default_variant`) so "every route in this workflow answers 501" is one row, not hundreds.

**ProfileRouteOverride** — per-profile exception to a route's behavior: `disposition` = `MATCH` (default — variants compete by their match fields), `FORCE` (always serve `forced_variant_id`, ignoring input), or `DISABLE` (route is unconfigured → feeds the containment "unexpected" path). Absence of a row = `MATCH`.

**AmbientResource / AmbientValue** — the generalization of "what does the host look like": a `FILE` (path → contents), an `ENV` var (name → value), or a `KEYVALUE` config entry, each resolving to a per-profile value (null profile = default). This is the "whatever computer Owl-E runs on" surface, decoupled from any specific OS's property system.

## 3. Matching algorithm (gateway, language-neutral)

For each intercepted request, under the run's active SimulationProfile:

1. **Attribute to an AccessPoint** by SNI/Host against `AccessPointHost` (exact, then wildcard). No match → **unmatched** → disposition per run policy (`deny|fault|forward`), always logged. *The unmatched case is the containment signal, not a gap.*
2. **Select the Route**: first route (by `id`) whose method matches (or `ANY`) and whose `path_pattern` matches segment-wise — a `{param}` segment consumes exactly one path segment and captures it; all pattern segments must be consumed and the path exhausted. No route → unmatched.
3. **Apply the profile's disposition** for that route (override row, else profile default, else `MATCH`):
   - `DISABLE` → denied (+log).
   - `FORCE` → serve the forced variant.
   - `MATCH` → among the route's variants: keep those whose `state_key` is currently set (or null), then those whose `sequence_position` equals this route's running call count (or null), then those whose query/header/body patterns hold; **first by `match_order` wins.**
4. **Serve**: apply `latency_ms`, `fault`; template the body with captured params; return `status`/`headers`/`body`. Record the flow → `network_events` (disposition, access_point, variant, timing, fingerprint).

Ambient reads resolve analogously: resource keyed by kind+key → value for the active profile (or default) → returned to the runner's datastore/host shims.

## 4. Scenario state (step-keyed)

Variants carry `state_key`; the gateway holds a small named-state set and a **loopback-only control endpoint**; the orchestrator sets/clears state on runner step events ("`step_completed 1234` ⇒ set `post_1234`"). "First S3 hit → A; after step 1234 → B" = variant A (`state_key` null), variant B (`state_key='post_1234'`), orchestrator sets it on that step. State transitions are logged alongside network events so the audit shows *which reality* served each request. Call-count cases use `sequence_position`; the two compose. (Full rationale: EXECUTION-ENVIRONMENT §9a.)

## 5. OpenAPI import

An HTTP AccessPoint may be scaffolded from an OpenAPI document: `servers` → hosts, `paths`×methods → Routes, response examples/schemas → starter ResponseVariants. Reduces "model this external surface" to an import. (Capability retained from the source prototype; no specific specifications carried over — §8.)

## 6. Binding to execution

- `SimulationProfile.id` is recorded on `workflow_executions` (which reality a run used); `test_cases` reference a `simulation_profile_id` (which reality a test asserts against). A run/test that names none uses the workflow's `is_default` profile. *(This replaces the earlier `access_point_stubs.test_case_id` sketch in EXECUTION-ENVIRONMENT §5 — profiles are the binding point, so variants stay defined once and are selected, not duplicated per test.)*
- The gateway consumes a **compiled stub-set** (profile resolved to concrete per-route dispositions + variants) as mounted JSON; it does not talk to the database at request time.
- Audit → `network_events`; the containment report (EXECUTION-ENVIRONMENT §6) is computed from it.

## 7. DDL (house style)

```sql
CREATE TABLE `access_points` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `workflow_id` bigint unsigned NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(8192) DEFAULT NULL,
  `kind` enum('HTTP','AMBIENT') NOT NULL DEFAULT 'HTTP',
  `scheme` enum('https','http') NOT NULL DEFAULT 'https',
  `openapi_spec` longtext DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `workflowId` (`workflow_id`),
  CONSTRAINT `access_points_workflow_fk` FOREIGN KEY (`workflow_id`) REFERENCES `workflows` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `access_point_hosts` (
  `access_point_id` bigint unsigned NOT NULL,
  `hostname` varchar(255) NOT NULL,
  PRIMARY KEY (`access_point_id`,`hostname`),
  CONSTRAINT `aph_ap_fk` FOREIGN KEY (`access_point_id`) REFERENCES `access_points` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `access_point_routes` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `access_point_id` bigint unsigned NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `method` enum('GET','POST','PUT','PATCH','DELETE','ANY') NOT NULL DEFAULT 'ANY',
  `path_pattern` varchar(1024) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `accessPointId` (`access_point_id`),
  CONSTRAINT `apr_ap_fk` FOREIGN KEY (`access_point_id`) REFERENCES `access_points` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `response_variants` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `route_id` bigint unsigned NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `match_order` int unsigned NOT NULL DEFAULT 0,
  `query_pattern` varchar(1024) DEFAULT NULL,
  `header_pattern` varchar(1024) DEFAULT NULL,
  `body_pattern` text DEFAULT NULL,
  `sequence_position` int unsigned DEFAULT NULL,
  `state_key` varchar(255) DEFAULT NULL,
  `response_status` smallint unsigned NOT NULL DEFAULT 200,
  `response_headers` text DEFAULT NULL,
  `response_body` longtext DEFAULT NULL,
  `latency_ms` int unsigned NOT NULL DEFAULT 0,
  `fault` enum('none','timeout','connection_reset') NOT NULL DEFAULT 'none',
  PRIMARY KEY (`id`),
  KEY `routeId` (`route_id`),
  CONSTRAINT `rv_route_fk` FOREIGN KEY (`route_id`) REFERENCES `access_point_routes` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `simulation_profiles` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `workflow_id` bigint unsigned NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(8192) DEFAULT NULL,
  `is_default` tinyint(1) NOT NULL DEFAULT 0,
  `default_disposition` enum('MATCH','FORCE','DISABLE') NOT NULL DEFAULT 'MATCH',
  `default_variant_id` bigint unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `workflowId` (`workflow_id`),
  CONSTRAINT `sp_workflow_fk` FOREIGN KEY (`workflow_id`) REFERENCES `workflows` (`id`) ON DELETE CASCADE,
  CONSTRAINT `sp_default_variant_fk` FOREIGN KEY (`default_variant_id`) REFERENCES `response_variants` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `profile_route_overrides` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `simulation_profile_id` bigint unsigned NOT NULL,
  `route_id` bigint unsigned NOT NULL,
  `disposition` enum('MATCH','FORCE','DISABLE') NOT NULL DEFAULT 'MATCH',
  `forced_variant_id` bigint unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_profile_route` (`simulation_profile_id`,`route_id`),
  KEY `routeId` (`route_id`),
  CONSTRAINT `pro_profile_fk` FOREIGN KEY (`simulation_profile_id`) REFERENCES `simulation_profiles` (`id`) ON DELETE CASCADE,
  CONSTRAINT `pro_route_fk` FOREIGN KEY (`route_id`) REFERENCES `access_point_routes` (`id`) ON DELETE CASCADE,
  CONSTRAINT `pro_forced_variant_fk` FOREIGN KEY (`forced_variant_id`) REFERENCES `response_variants` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `ambient_resources` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `access_point_id` bigint unsigned NOT NULL,
  `kind` enum('FILE','ENV','KEYVALUE') NOT NULL,
  `key_path` varchar(1024) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `accessPointId` (`access_point_id`),
  CONSTRAINT `ar_ap_fk` FOREIGN KEY (`access_point_id`) REFERENCES `access_points` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `ambient_values` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `ambient_resource_id` bigint unsigned NOT NULL,
  `simulation_profile_id` bigint unsigned DEFAULT NULL,   -- NULL ⇒ default value
  `present` tinyint(1) NOT NULL DEFAULT 1,
  `content` longtext DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_resource_profile` (`ambient_resource_id`,`simulation_profile_id`),
  KEY `simProfileId` (`simulation_profile_id`),
  CONSTRAINT `av_resource_fk` FOREIGN KEY (`ambient_resource_id`) REFERENCES `ambient_resources` (`id`) ON DELETE CASCADE,
  CONSTRAINT `av_profile_fk` FOREIGN KEY (`simulation_profile_id`) REFERENCES `simulation_profiles` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- execution binding
ALTER TABLE `workflow_executions` ADD `simulation_profile_id` bigint unsigned DEFAULT NULL;
ALTER TABLE `test_cases`          ADD `simulation_profile_id` bigint unsigned DEFAULT NULL;
```

## 8. Clean-room note

Service virtualization — named response variants, request matching, environment/profile switching, latency/fault injection, spec-driven scaffolding — is generic, long-established prior art (WireMock, Hoverfly, MockServer, Mountebank, and the enterprise SV tools all predate and share these ideas). This model **re-derives** those generic mechanics in Owl-E's own domain-neutral vocabulary (`AccessPoint` / `Route` / `ResponseVariant` / `SimulationProfile` / `AmbientResource`). No domain-specific structure, naming, schema, seed data, or API specifications from the source prototype are carried into Owl-E; the source retains only its generic mock-server shape, which is not anyone's proprietary property. The source tree itself lives solely in a local, unpushed git branch and is not part of Owl-E.

### Concept map (source prototype → Owl-E), for our record only
| source concept | Owl-E |
|---|---|
| Environment (named sim reality) | `SimulationProfile` |
| Group (mid-tier grouping) | folded into profile scoping |
| API / API-override | `AccessPoint` (+ `ProfileRouteOverride` for per-profile enablement) |
| Endpoint / endpoint-override | `Route` |
| Call (named response) | `ResponseVariant` |
| device/OS ambient surfaces | `AmbientResource` {FILE, ENV, KEYVALUE} |
| delay / status / body | `latency_ms` / `response_status` / `response_body` |
| client `?code=N` selection hint | `sequence_position` / `state_key` / attribute matching (no hint needed) |

## 9. Status & next

- **Harvest captured (2026-08-10).** This doc is the durable result; the source tree can be deleted from the working copy at any time (recoverable from `backup/air-integration-20260810`).
- **Matcher reference implementation:** deferred to the gateway build (EXECUTION-ENVIRONMENT M2–M3). The algorithm in §3 is language-neutral on purpose — the runtime home (mitmproxy Python addon vs. a server-side authoring preview) is settled there, not here.
- **Authoring UX:** deferred with the Owl-E client-framework decision (EXECUTION-ENVIRONMENT §12). The source prototype's interaction patterns (hierarchy panel, variant flipping, spec import) are the reference; its implementation is not carried forward.
