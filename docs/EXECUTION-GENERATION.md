# Owl-E Execution Generation — Multiflows → Python

*Design doc, 2026-08-10 (M1 kickoff). How a configured composition compiles to a runnable Python program. Companion to [EXECUTION-ENVIRONMENT.md](EXECUTION-ENVIRONMENT.md) (the container it runs in) and [SIMULATION-MODEL.md](SIMULATION-MODEL.md) (the reality it sees). Architected against the **schema only** — no reliance on populated rows, deliberately, so process gaps surface as design gaps.*

---

## 1. The reframe: a Multiflow is the unit of generation, not a Workflow

A user designs a forest of **reusable** workflows. Junctions (`workflow_junctions`: `src_step_id → target_workflow_id`, optionally gated by an inquisition) let one workflow hand off to another. The same workflow can be a junction target from many places, so **"which workflows are in play" is not a property of any workflow** — it's an emergent fact of a particular composition.

That composition is a **Multiflow**: one **root** workflow guaranteed to run, plus the closure of workflows reachable from it through junctions (the *may-run* set). Generation targets a Multiflow. This is new — a Multiflow exists nowhere in the schema or code yet.

This corrects `server/gen/model/Members.kt`, whose `ExecutionBlueprint` is workflow-rooted. The corrected model is in [`MultiflowBlueprint.kt`](../server/src/main/kotlin/com/wini/owle/gen/model/MultiflowBlueprint.kt): `Multiflow` → `MultiflowBlueprint` → `List<WorkflowExecutionDetail>` (each reusing the existing per-step `StepExecutionDetail`). The old top-level `ExecutionBlueprint` is absorbed into `WorkflowExecutionDetail`; the envelope-only `common/dto/ExecutionBlueprint.kt` stub is deleted.

## 2. Multiflow — data model

Persist it **thin**; let membership stay derived (see §9 for why):

```sql
CREATE TABLE `multiflows` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint unsigned DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(8192) DEFAULT NULL,
  `root_workflow_id` bigint unsigned NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `rootWorkflowId` (`root_workflow_id`),
  CONSTRAINT `multiflows_root_workflow_fk` FOREIGN KEY (`root_workflow_id`) REFERENCES `workflows` (`id`),
  CONSTRAINT `multiflows_users_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
```

A run is now a Multiflow run: `workflow_executions` gains a `multiflow_id` (or a `multiflow_executions` parent with per-workflow child rows) — follow-on, noted in §10.

## 3. Assembly — building the blueprint from the schema (no rows required)

The builder walks **structure**, not data. Starting from `multiflows.root_workflow_id`:

1. **Closure of member workflows.** BFS/DFS over junctions: for the current workflow, gather its `steps`; for each step, gather `workflow_junctions` where `src_step_id = step.id`; each `target_workflow_id` is a member; recurse. De-dup workflows (a workflow appears once no matter how many junctions reach it). Guard cycles (a Multiflow graph may legally loop).
2. **Per workflow → `WorkflowExecutionDetail`.** Resolve `entryStepId` (the `is_workflow_head` step). Gather `data_points` in scope. For each step build a `StepExecutionDetail` (already defined in Members.kt):
   - `toolParamResolutions` ← `data_point_reads` (+ `provision_data_points`) bound to each `tool_param`, through any read-site transform.
   - `resultResolutions` ← `data_point_writes` (which datapoint the result updates) + the write-site `transform_occurrence` and its args.
   - `errorWatches` ← `error_watches` for the step (+ ordered `error_watch_fns` filters).
   - `defaultNext` ← `step_default_routes`.
   - `inquisitionBranches` ← `inquisitions` (ordered by `execution_order`), each pointing at its `trigger_step_id`.
   - `crossWorkflowRoutes` ← `workflow_junctions` on the step, each carrying its `data_point_injections` (src datapoint → target workflow's datapoint, with inject transform).
3. **Reconcile the environment** (§6).

Scoping note (post-rename, see §7): steps and **StepGroups** (currently `phases`) are scoped to a `workflow_id` or nested within an ancestor StepGroup in that workflow's scope; everything routing-related (junctions, injections) hangs off `step_id`. The builder therefore keys entirely on steps and follows junctions across workflow boundaries — StepGroups are presentational scoping and don't affect control flow (they inform naming/comments in the emitted code).

**Testing without a DB:** the builder is validated against a hand-constructed `MultiflowBlueprint` (the way `CompleteExample.kt` hand-builds entities), not seed rows. If the architecture needs a row to make sense, that's a design smell we want to catch.

## 4. Target Python — the emitted program for a Multiflow

One Multiflow → one Python package:

```
/app
  runner.py            # Multiflow orchestration: enter root; dispatch junction handoffs
  workflows/
    wf_<rootId>.py     # one module per member workflow: step_<id>() fns + run(ds) entry
    wf_<id>.py …
  datastore.py         # typed slots per workflow scope; carries injections across handoffs
  tools.py             # tool bodies, verbatim
  inquisitions.py      # inquisition predicate bodies
  transforms.py        # shipped transforms stdlib
  manifest.json
```

**Per step** (mechanical translation of the lifecycle in REPO-STATE §3):

```python
def step_11511650(ds: DataStore) -> Route:                 # from StepExecutionDetail
    emit("step_started", step_id=11511650)
    args = {r.param: ds.read(r.dataPointReadId) for r in TOOLPARAMS[11511650]}  # toolParamResolutions
    try:
        result = tools.validate_json(**args)
    except jsonschema.ValidationError as e:                 # errorWatches → except clauses
        emit("error_watch_triggered", step_id=11511650, error_type=type(e).__qualname__)
        return Route.step(11511655)
    ds.write(10011251, transforms.apply(WRITES[11511650], result))   # resultResolutions (writes ALWAYS run)
    for inq in INQUISITIONS[11511650]:                     # inquisitionBranches, execution_order
        if inq.predicate(ds):
            return inq.route                                # trigger step, or a junction (Route.junction)
    return Route.step(NEXT[11511650])                       # defaultNext / END
```

**Per workflow:** a `run(ds)` that trampolines from `entryStepId` until `END` or a junction route.

**Multiflow orchestration** (`runner.py`): call the root workflow's `run(ds)`. A `Route.junction(j)` doesn't leave the process — it (a) builds the target workflow's datastore by applying that junction's `data_point_injections` (src values → target datapoints, inject transform applied), (b) calls the target workflow's `run(target_ds)`, (c) resumes per the junction's semantics. Cross-workflow control flow is ordinary function calls because the whole Multiflow shares one interpreter (see §5).

### Emit-logic gaps to confirm (this is the part that lives in Wini's head)
Flagged so they become documentation, not assumptions:
- **Transform application** (`transforms.apply`): exact contract for read- vs write-site transforms and chained `transform_occurrence_chains` — arg binding order, in-place vs returned value.
- **Inquisition predicate signature**: does the body receive the datastore, the just-written value, both? (Schema says its subject is a data point — so datastore — but confirm.)
- **Injection on re-entry**: if a workflow is entered by junctions more than once in a run, is its datastore fresh each time or preserved? (Affects whether `run()` is re-entrant or spins a new scope.)
- **`store_method` file/blob**: how `ds.read/write` differ for `text` vs `blob` vs `file` datapoints in the container.
- **Terminal junction vs terminal step**: does a junction always end the source workflow, or can control return after the target completes?

## 5. The payoff: a Multiflow is one container, not many

EXECUTION-ENVIRONMENT §9 assumed a junction exits the container and the orchestrator provisions the target workflow's environment. **The Multiflow concept improves on that:** because a Multiflow reconciles its members into one environment (§6), every member runs in the **same** container, and intra-Multiflow junctions are in-process Python calls — faster, simpler, no per-hop container spin-up, and the whole run shares one gateway/audit context. The container-handoff model is only needed if a junction ever crosses into an environment-*incompatible* workflow; for M1, a Multiflow is single-container by construction. (This refines EXECUTION-ENVIRONMENT §9 and its junction guardrails — hop budget/cycle detection still apply, now as in-process recursion limits.)

## 6. Environment reconciliation across members

Each member workflow declares its own `python_version_id` and `workflow_requirements`. The Multiflow's single image must satisfy all of them:
- **Python version:** members must share a compatible version. Validate at assembly; reject a Multiflow whose members demand incompatible interpreters (or, later, split it across containers — out of scope for M1).
- **Requirements:** union all members' `workflow_requirements`, then resolve/lock once (EXECUTION-ENVIRONMENT §8's uv pipeline) for the whole Multiflow.

## 7. Phases → Grouper (rename)

`phases` are being renamed to **Groupers**. New generation artifacts use "Grouper" in prose and identifiers. The existing schema/entity/DTO rename (`phases`, `phase_relations`, `Phase*`, `parent_phase_id`, `tagged_phases`, `PhaseTag`) is a **separate mechanical refactor**, tracked, not performed here — doing it mid-generation-design would just add noise. Groupers remain control-flow-irrelevant: they scope and name, they don't route.

## 8. Schema-first discipline

Per the self-imposed constraint: the builder and emitter are designed against table/entity structure with **no dependency on populated rows**. The recovered insert dumps stay on the shelf. The test fixture is a hand-built blueprint. Rationale: if generation can be expressed purely from schema shape, the model is sound; if it secretly needs data to make decisions, that's a process gap worth exposing now rather than after wiring a DB.

## 9. Open decisions

1. **Membership: derived (recommended) vs stored.** Derived = the closure is recomputed from junction wiring at assembly; nothing to drift, honors reuse, Multiflow stays thin `{name, root}`. Stored = a `multiflow_workflows` join table for a curated/frozen set (needed only if users must pin membership independent of live junction wiring). Recommend derived; add the table later if a real need appears.
2. **Root cardinality: single (recommended) vs many.** Modeled as one `root_workflow_id`. "At least one must execute" is satisfied by the root; extends to multiple guaranteed entries via a `multiflow_roots` join table if that ever means "several independent flows in one image." Recommend single.
3. **Environment incompatibility** (§6): reject vs multi-container split. Recommend reject for M1.
4. **Run binding:** `workflow_executions.multiflow_id` vs a new `multiflow_executions` parent with child per-workflow runs. Leaning parent table (cleaner audit across hops).

## 10. Delivery — the ball, rolling

- **Done this turn:** the reframe; corrected model (`MultiflowBlueprint.kt`); this doc.
- **Next (on your nod to the §9 calls):** (a) `Multiflow` DTO + `Schema.Multiflow` constants + Exposed table/entity, following the `Workflow` pattern; delete the `common/dto/ExecutionBlueprint` stub; (b) the **blueprint builder** — entities → `MultiflowBlueprint`, the schema walk in §3, unit-tested against a hand-built fixture; (c) the **emitter** — `MultiflowBlueprint` → the §4 Python, golden-file-tested. (b) is where your domain insight gets encoded once; (c) is the dumb walk.
- **Then:** run it (M1 proper) — generate for a `CompleteExample`-shaped Multiflow and execute in `python:3.12-slim`.
