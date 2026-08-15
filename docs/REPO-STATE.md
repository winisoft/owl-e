# Owl-E — Repo State & Concept Map

*Written 2026-08-10 by Claude (session with Wini) to be the single orientation point after a period of fragmentation. When this disagrees with `Owl-E.md`, `Architecture.md`, or `create.sql`, this file wins — they have drifted (specifics below).*

Companion doc: [EXECUTION-ENVIRONMENT.md](EXECUTION-ENVIRONMENT.md) — the active design focus (containerized execution + mock access points).

---

## 1. Working-tree incident (resolved 2026-08-10)

The entire `composeApp/` module (438 tracked files) had been moved, uncommitted, into `docs/composeApp/` — almost certainly an accidental drag in an editor. Every file was verified **byte-identical** to `HEAD` (full recursive diff), after which the tracked `composeApp/` tree was restored via `git restore composeApp`. The build was broken during that window because `settings.gradle.kts` still includes `:composeApp`.

**Remaining cleanup (Wini's call, not done automatically):**

- `docs/composeApp/` is now a redundant, untracked, byte-identical copy. Delete with `rm -rf docs/composeApp` once satisfied.
- `result.txt` / `result.html` at repo root are `tree`-command dumps (UTF-16). Delete or `.gitignore`.

## 2. Source-of-truth ranking

1. **There is currently no live database.** MySQL was uninstalled to reclaim disk space; the DDL survives as `docs/create.sql`, but the *row* exports (INSERT dumps) haven't been located yet. Until they are, nothing that needs the PyPI mirror or the introspected type data can actually run.
2. **`docs/create.sql` (refreshed 2026-08-10) is the final pre-decommission dump** — the last observed truth of the DB. The entity layer (`server/src/main/kotlin/com/wini/owle/entity/**`) is the truth of where the *code* is. **Neither is a superset**; they drifted in both directions and need per-table reconciliation:

   | Area | Dump (DB at decommission) | Entity layer (code) |
   |---|---|---|
   | Callables | `callable_signatures` / `callable_parameters` / `callable_owners`, `type_aliases`; `tools.signature_id` | Present too (`entity/typing/Callable*Entity.kt`) — **in sync** |
   | Inquisitions | `signature_id` + **`body` text NOT NULL** — predicates carry their own Python source, like tools | **Old shape** — still `condition_node_id → type_nodes` (`entity/steps/ExposedTables.kt`) — *code behind* |
   | Data points | `is_prerequisite` column (marks run inputs) | Absent — *code behind* |
   | Run logging | `test_step_run` only | Also `execution_logs`, `test_outcome_expectation`, `test_inputs.generationMethod` — *dump behind* |
   | Sequencing | `step_default_routes`; no `default_next_step_id` on `steps` (contra `Owl-E.md` §2.3) | Same |

   Data scale at decommission (AUTO_INCREMENT high-water marks): `packages` ≈ 4.35M, `python_versions` ≈ 4.34M, `type_schema_refs` ≈ 2.96M, `package_dist_requirements` ≈ 1.03M, `type_nodes` ≈ 991K, `versions` ≈ 439K, `type_instantiations` ≈ 181K. If the export files never surface this is all **regenerable** — `pyside/` re-ingests the PyPI BigQuery dumps, `type_introspector/` re-runs introspection — but finding the exports is far cheaper than re-running the beast. Either way, **M1 of the execution-environment plan is not blocked**: the `CompleteExample` fixture constructs its own self-contained rows.
3. Prose docs use retired vocabulary. Translation table:

   | Prose (`Owl-E.md`) | Actual schema |
   |---|---|
   | "inspectors" | `inquisitions` (+ `error_watches` for the exception side) |
   | "consumer_injections" | `data_point_injections` |
   | "assignment rules" (VALUE_EXACT, VALUE_SPLIT…) | `transforms` + `transform_occurrences` |
   | "data items" | `data_points` |

## 3. Concept map — design narrative → schema

The execution narrative ("we need space to store these data points, we begin by executing this step…") maps 1:1 onto tables that **already exist**:

| Narrative | Schema | Notes |
|---|---|---|
| space to store these data points | `data_points` | typed via `type_node_id`; `store_method` text/blob/file; `preset_value` |
| execute this step | `steps` → `tools` | `tools.body` holds the actual Python source; `tool_params` typed |
| feed the tool from the store | `data_point_reads` (+ `provision_data_points`) | per (step, tool_param); optional transform at read site |
| exception handler: this error → this step | `error_watches` | `error_type` string, `trigger_step_id`; `error_watch_fns` = ordered refinement fns |
| inspections in sequence; true → this step | `inquisitions` | `execution_order`, `trigger_step_id`, args via `inquisition_args`; in the refreshed dump each carries its own **`body`** (Python predicate source) + typed `signature_id` — the entity layer still has the older `condition_node_id` shape |
| simple transformations on a value | `transforms`, `transform_target_types`, `transform_args` | applied at sites via `transform_occurrences` (`read`/`write`/`inquisition`/`inject`), chainable via `transform_occurrence_chains` |
| store the result for later steps | `data_point_writes` (+ `data_point_write_args`) | optional transform at write site |
| default next step, or done | `step_default_routes` | absence of a row = terminal step |
| start over in a new environment | `workflow_junctions` | unconditional (src step) or triggered by an inquisition |
| lifting values across the junction | `data_point_injections` | src data point → target workflow's data point |
| configuring an environment | `workflows.python_version_id` + `workflow_requirements` | backed by a full PyPI metadata mirror: `distributions`, `packages`, `versions`, `package_dist_requirements`, `env_marker_*` (PEP 440/508 modeled relationally) |
| testing a workflow | `test_cases`, `TestInputs`, `test_expected_outcomes`/`test_outcome_expectation`, `test_results` | inputs/expectations bind to data points |
| execution records | `workflow_executions` (+ code-side `execution_logs`, `test_step_run`) | thin today — extended by the execution-environment design |
| grouping/UI structure | `phases`, `phase_relations`, `workflow_folders`, `tags` + `tagged_*` | phases are presentational scoping, not control flow |

### Canonical step lifecycle (normative)

For a step **S** (this is the spec the runner generator implements; also see EXECUTION-ENVIRONMENT.md §3):

1. **Resolve args** — for each tool param: value from its `data_point_reads` row (candidates via `provision_data_points`), passed through any read-site transform chain.
2. **Invoke** the tool body, wrapped per `error_watches`: matched error → route to its trigger step. Unwatched exception → run fails (recorded, not silently).
3. **Writes** — persist the result via `data_point_writes` (write-site transforms apply). Unconditional on normal return: *persistence follows production* — see the decision below.
4. **Inquisitions** in `execution_order` — each a user-authored predicate (own `body`, typed signature) over data points (`data_point_id` + `inquisition_args`). First one evaluating true routes to its trigger step — or, if a `workflow_junctions` row references it, transitions workflow.
5. **Route** — unconditional junction if present, else `step_default_routes`, else done.

**Decided 2026-08-10 — writes are never suppressed, and inquisitions run *after* writes.** The schema forces this: an inquisition's subject and arguments are *data points* — no column anywhere references a tool's raw return value — so a result must already be written for an inquisition to see it. If inquisitions could suppress writes, no inquisition could ever inspect the thing it exists to inspect. Corollaries:
- An inquisition is a **routing predicate attached to a step's completion** — not a step, not a guard. No tool, no phase, no display slot: an annotated edge, not a node.
- Junction injections lift **post-write** state; opting a value out of a junction = not mapping it in `data_point_injections`.
- The "don't keep a bad value" pattern is a remediation step that overwrites. Execution is single-threaded, so the transient value is visible only to the exact path the user routed to.
- *If you want to inquire about a result, give it a home.* A step whose inquisitions reference no data point it writes is inspecting **ambient** state — e.g. a predicate body that polls an external resource — which is legal, fully audited under containment, and mockable (a stub sequence not-ready → not-ready → ready makes a polling loop deterministically testable).

## 4. Module inventory & maturity

| Module | Size | State |
|---|---|---|
| `server/` | 578 .kt | The substantial asset. Full layered CRUD API over the whole domain: `api/{workflows,steps,transforms,typing,environs,user,runs}`, entity + DTO + `dll` (data-logic layer), Koin DI. `server/validators/**` is DEAD — extends a `BaseValidator` that no longer exists; superseded by `type-schema`'s KSP-generated validators (see `Generated Project Elements.md`, rewritten 2026-08-15 as the full KSP reference). Test split unit/integration. |
| `composeApp/` | 438 files | Compose Multiplatform client (desktop/web). Extensive resource set; restored from the docs/ mishap. Maturity not re-audited this session. |
| `common/` | 183 .kt | Shared DTOs/validation. |
| `type-schema/` | 102 .kt | KSP schema-validation codegen. |
| `jep-utils/` | 2 .kt | Spike: JEP embedded Python used to lean on `packaging` for PEP 440/508 parsing (version compares, requirement/marker parsing). Useful server-side; **not** the workflow execution path going forward. |
| `pyside/`, `type_introspector/` | Python | Offline ingestion: PyPI metadata → `packages`/`versions` tables; CPython source introspection (vendored stdlib sources per version) → `type_nodes` ecosystem. |

**Key fixture:** `server/src/test/integration/com/wini/owle/CompleteExample.kt` builds the canonical example workflow in the test DB — "Process and Display Some Data Set": `fetch_json → validate_json → parse_normalize → db_enrich → compute_stats → render_and_upload`, with typed data points (`api_endpoint`, `raw_json`, `validation_report`, `schema_uri`), real requirement rows (requests, pandas, jsonschema, boto3, matplotlib, psycopg2-binary), and Python stub bodies. This is the seed corpus for the runner generator.

## 5. What does NOT exist anywhere (verified by search)

- **No containerization code.** Zero Docker/compose/Testcontainers usage in any module (the only grep hits are vendored CPython sources).
- **No workflow→Python program generator.** The remembered codegen work is not in this repo; the schema is rich enough to regenerate it from spec (see companion doc) — cheaper than archaeology across old projects, as suspected.
- **No mock/access-point model**, no network audit, no run modes. `workflow_executions` is bare (status + timestamps).
- **No environment build pipeline** (requirements are modeled, but nothing resolves/locks/installs them).

## 6. Warts & open threads (small, recorded so they stop being re-discovered)

1. `transform_occurrences.is_persistent` is a generated column testing `occurrence = 'revision'`, but the enum is `('read','write','inquisition','inject')` — **always false**. Relic of a `revision`→`write` rename (the FK name `fk_revision_args_rev` in `data_point_write_args` corroborates). Fix or drop the column.
2. `CompleteExample.kt` references `TypeNodeEntity[11611058]` (tool params for `db_enrich.records` / `compute_stats.rows`) but only seeds nodes …45–…56. Either `refreshTestDbData` supplies it from elsewhere or this fixture throws on first touch — verify when the integration suite next runs.
3. `docs/Current Objective.md` is a preserved ChatGPT transcript, not a live objective. Its substance: `transform_target_types` population should be a **preprocessing automation pass** (unify generic transform templates like `List[T]` against introspected instantiations; emit concrete applicability rows + instantiate return types). Still a real, open task — but downstream of the execution-environment work.
4. `Architecture.md` is a stub whose integration-summary table was never filled in; superseded by this file.
5. CLAUDE.md's dev-commands section predates all of the above; the schema/module claims there should be read through this document.
6. `server/src/main/kotlin/com/wini/owle/entity/ExposedTables.kt` (package root) is a **pre-rename fossil**: it still defines `Revisions`, `Transformations`, and a camelCase-column `Inquisitions`, coexisting with the current per-domain entity files. It's also the smoking gun for wart 1 (`revision` → `write` rename). Quarantine or delete.
7. Tools now carry **both** `tool_params` rows and a `signature_id → callable_signatures` (whose `callable_parameters` describe the same call contract). The runner generator needs one authority — proposal: `callable_signatures` when present, with `tool_params` as the authoring-time input that produces/updates the signature. (Also EXECUTION-ENVIRONMENT §12.)
