# Owl-E Step Execution Lifecycle — the canonical model

*2026-08-10. THE authority for what the generated program does per step. Supersedes the toy per-step sketch in EXECUTION-GENERATION.md §4. Written after Wini corrected a badly oversimplified walk — every phase below maps to exact schema tables, and every configuration-foreshadowed outcome emits a log line (that is the safety thesis: the run's log is a complete, transparent account of everything the configuration anticipated, whether it happened or not).*

Vocabulary: table names are from `docs/create.sql`. "value" always means a typed data-point value.

---

## The unit: a workflow runs steps; a step is NOT one function call

A step is a small machine with six phases. The generator emits code for all six; the runner logs its way through each. Persistence-follows-production (REPO-STATE §3) fixes the order: **resolve → invoke → write → inquire → route → (end/junction)**.

### Phase 0 — Entry
The workflow's head step is `steps.is_workflow_head = true`. On a junction handoff, entry is instead the injected target's head. LOG `step_entered{step, workflow}`.

### Phase 1 — Resolve tool inputs (provisioning)
The tool's parameters are **not** free — each is provisioned from the data store.
- For each `tool_params` row P of the step's tool: find `data_point_reads` DR where `DR.step_id = step` and `DR.tool_param_id = P`.
- The value(s) come from `provision_data_points` (DR → one or more `data_points`).
- If `DR.transform_occurrence_id` is set → a **read-site** transform (`transform_occurrences.occurrence='read'`, `transform_occurrence_args`, possibly chained via `transform_occurrence_chains`) is applied to the provisioned value first.
- LOG per param: `param_resolved{step, tool_param, from_data_point, transform?, value_repr}`.
Result: the kwargs for the call.

### Phase 2 — Invoke, guarded by error watches
- `error_watches` for the step: each has `error_type` (a Python exception) and `trigger_step_id`. `error_watch_fns` are ordered (`position`) refinement predicates over the caught exception deciding whether *this* watch actually applies.
- Emit `try: result = tool_body(**kwargs)` wrapped in `except <error_type> as e:` for each watch; inside, run its `error_watch_fns` filters; if it applies → LOG `error_watch_fired{step, error_type, routed_to=trigger_step}` and route (skip phases 3–4). An unwatched exception → LOG `run_failed{...}` and the run ends failed (never silent).
- LOG `tool_invoked{step, tool, kwargs_repr}` then `tool_returned{step, value_repr}` (or the fired/failed events above). A guarded region that completes with no watch firing LOGs `guarded_ok{step}`.

### Phase 3 — Writes (ALWAYS on normal return, BEFORE inquiring)
A step may perform **multiple** `data_point_writes`, and a write need not be "the tool's result stored somewhere obvious":
- Each `data_point_writes` row: `data_point_id` (target), optional `transform_occurrence_id` (**write-site** transform), and `data_point_write_args` — ordered (`position`) references to **other data points** that feed the write's computation. This is the "a write may need to *read* other data points first" case (e.g. a running timer float checked/updated independently of what the tool just did).
- Apply the write-site transform to the source (tool result, or value built from the write-args) and store. The resulting type is known statically via `transform_target_types` (eligible type → return type). Worked example: tool returns `List[Location]`; `transform_occurrences`(transform_id 345 "Get At Index X", `transform_occurrence_args` index=0) → `Location`; store in data point 890 (type Location).
- LOG per write: `data_point_written{step, data_point, transform?, args?, value_repr}`.

### Phase 4 — Inquisitions (in `execution_order`)
- `inquisitions` for the step, ordered by `execution_order`. Each: a predicate (dump: `body` + `signature_id`; older entity: `condition_node_id`), a subject `data_point_id`, positional `inquisition_args` (other data points), optional inquisition-site transform.
- Evaluate in order. **First true wins** → route to its `trigger_step_id`, OR, if a `workflow_junctions` row references this inquisition, → a junction transition (Phase 6).
- LOG every evaluation, true or false: `inquisition_evaluated{step, inquisition, order, subject, result, routed_to?}`. (False evaluations are logged too — a foreshadowed outcome that did not fire is still an audit fact.)

### Phase 5 — Route
- If Phase 2 or 4 redirected → go there.
- Else consult `step_default_routes` (`step_id` → `next_step_id`). If present → that step.
- If **no default route and nothing redirected** → the workflow ends. LOG `workflow_ended{workflow, at_step}` — this event is what triggers Phase 6.

### Phase 6 — Workflow end / junction handoff
- On the workflow-ended event (or an inquisition-triggered junction), find `workflow_junctions` where `src_step_id =` the ending step (unconditional) or matched by the triggering inquisition.
- For that junction, `data_point_injections` migrate values **out of the ending environment into the target workflow**, landing as **read-only** data points available from the target's instruction one (`src_data_point_id` → `target_data_point_id`, optional inject-site transform).
- LOG `junction_taken{from_step, to_workflow}` and per injection `injection_migrated{src, target, readonly:true, value_repr}`. Then begin the target workflow at Phase 0.
- If there is no junction, the Multiflow finishes when no workflow is live. LOG `multiflow_completed`.

---

## Logging is the product, not decoration

Wini's mandate: *no configuration-structure-foreshadowed runtime outcome may occur without a robust log line articulating everything known about it.* Consequences for the emitter:
- Every phase above emits structured events (JSONL, per EXECUTION-ENVIRONMENT §3) — `param_resolved`, `tool_invoked`, `error_watch_fired`, `data_point_written`, `inquisition_evaluated` (including falses), `route_taken`, `injection_migrated`, `workflow_ended`, `multiflow_completed`, `run_failed`.
- Each event carries the *configured* expectation and the *actual* outcome, so the log alone reconstructs the full decision path — the transparency that the safety story (and the containment report, EXECUTION-ENVIRONMENT §6) rests on.
- Events are emitted by generated code inline at each site, not inferred afterward.

## Transforms are one concept at four sites

`transform_occurrences.occurrence ∈ {read, write, inquisition, inject}` — the same transform machinery attaches at read-time (Phase 1), write-time (Phase 3), inquisition-time (Phase 4), and injection-time (Phase 6). The emitter has **one** `transforms.apply(occurrence, args, value)` path used in all four phases; chains via `transform_occurrence_chains`. Getting this single abstraction right is most of the transform work.

## Open emit-contract questions (still Wini's to pin — do not guess in code)
1. **Inquisition predicate signature** — does `body` receive the data store, its subject value + args, or both? (`signature_id`/`inquisition_args` imply subject+args; confirm.)
2. **Transform arg binding** — order and semantics of `transform_occurrence_args` vs `transform_args`; in-place vs returned.
3. **Re-entry** — if a workflow is entered by a junction more than once in a run, fresh data store each time or preserved?
4. **`store_method`** — `text | blob | file`: how `ds.read/write` differ in-container; whether `file`/`blob` back onto a generated sqlite db or the filesystem (Wini's half-memory — see EXECUTION-ENVIRONMENT §12).
5. **Terminal junction vs return** — does a junction always terminate the source workflow, or can control resume after the target completes?
