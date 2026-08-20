# Owl-E — UI design brief

*For a designer with no prior exposure to the project. Written 2026-08-19.*

This describes what to design and why, not how to build it. Where something is settled it says so;
where it is open it says that too, and §16 collects the questions only Wini can answer.

---

## 1. What Owl-E is

Owl-E is a desktop-class tool for building **workflows** — automation pipelines that call Python
tools, pass typed values between them, branch on conditions, and can be run, tested, and watched.
Think of it as an IDE for a thing that isn't code but behaves like it: it has a project tree,
elements that nest, an execution log, and a strong sense of "where am I right now".

Two things follow, and they shape everything:

**It is a professional tool, used for hours.** Density beats generosity. The reference point is
JetBrains or VS Code, not a marketing site. Small type, tight rows, information available without
clicking. The palette is **Darcula** — tokens live in `web/src/styles/theme.css`, and `/tokens` in
the running app renders every one.

**Almost everything is a container.** Folders hold folders and workflows. Workflows hold groupers,
steps, and data points. Groupers hold groupers and steps. Steps hold a tool, inquisitions, error
watches, reads, and writes. The user is *always somewhere* in that tree, and the UI's main job is to
make the current scope legible and moving between scopes cheap.

---

## 2. The shell

```
┌──────────┬────────────────────────────────────────────────┐
│ nav rail │  breadcrumb                                    │
│          ├────────────────────────────────────────────────┤
│ Workflows│                                                │
│ Air      │                                                │
│ Execut.. │           the workable area                    │
│ Parliam..│                                                │
│ Benchm.. │                                                │
├──────────┤                                                │
│ detail   │                                                │
│ panel    │                                                │
└──────────┴────────────────────────────────────────────────┘
```

**Nav rail** — narrow, does not expand; there isn't enough horizontal real estate to spend. Five
destinations: Workflows, Air, Executions, Parliament, Benchmarking. It switches *destinations*; the
tree lives in the workable area.

**Detail panel** — beneath the nav, same column. A read-only contextual quick-look at whatever is
focused. Never editable, never authoritative — just "what am I looking at". §12.

**Workable area** — the current scope. This is what "full" means in §4: the whole of *this*, not the
whole window.

**Breadcrumb** — above the workable area, always present, at every view size. §11.

---

## 3. The element card

**The central pattern.** Every workflow-configuration element — folder, workflow, grouper, step,
data point, inquisition, error watch, junction — is the same basic object, so learning one teaches
all of them.

```
┌─────────────────────────────────────────────────────────┐
│ [icon]  Name of the thing            ← actions slide in │
│                                                         │
│  Description                                            │
│  contains 10 groupers (3 nested) · 25 steps (18 nested) │
└─────────────────────────────────────────────────────────┘
```

- **Icon, top left** — type-specific and deliberately chosen (§8). Primary signal of *what kind of
  thing this is*, and it carries real onboarding weight.
- **Name**, immediately right of the icon.
- **Quick actions**, top right — hidden until hover, then **sliding out leftward from the corner**,
  sliding back on hover-end.
- **Body** — description, plus a **count breakdown** where the element contains things.

Why the sliding panel: the action sets run eight to fourteen buttons per type. Always-visible is
noise; a context menu is too slow for things used constantly. Sliding from the corner keeps the
resting card clean and puts the controls where the eye already is.

**Colour.** Steps and groupers carry a user-assigned `#RRGGBB`. Treat it as an accent — left border
or outer glow — never a fill. It must not fight the Darcula ground.

---

## 4. Three view sizes, and the cubby model

Wini's vocabulary, explicit in the toolbars: **compact**, **medium**, **full**.

**Compact** — the resting state. Header plus the most relevant text. Many visible at once.

**Medium** — the card grows in place to show contents or detail. Siblings stay visible around it.
Resizable by click-and-drag.

**Full** — the element fills the **workable area** — not the whole window. The nav rail, detail
panel, and breadcrumb remain. Reached by double-click or the toolbar's `expand_to_full`.

**In full view the header stays pinned to the top of the workable area**, so the quick-actions panel
remains in the same corner it occupies in compact view. The control surface never moves. This is
what makes the three sizes read as one object resizing rather than three different screens.

### The cubby model — and what it means for what you can see

Think of a workflow's contents as an **automat**: a wall of cubbies, no conveyor belt. When you
expand one cubby to fill the wall, the others are **outside the visual scope**. That is not a
problem to be solved. It is how the model works.

Concretely: a step expanded to full sits alongside the workflow's data points in the *structure*,
but you cannot see them while it is expanded. **This is fine**, because Owl-E's convention for
reaching things is **summoning, not proximity** — you ask for a thing by name through a search and
it arrives, the way you expect a delivery without needing to see the shelf it came off.

There is an argument for letting the data store "cut through" so the user needn't return to the
workflow scope to browse names — but that is deliberately *not* the primary mental model. Spatial
wiring (drag a pipe from an output to an input) is a good way to *visualise* how steps connect,
especially during an execution, and a poor way to *author*. Design for summoning.

Every card's toolbar carries the size controls, and they are stateful:

| Current size | Buttons offered |
|---|---|
| compact | `expand_to_medium`, `expand_to_full` |
| medium | `collapse_to_compact`, `expand_to_full` |
| full | `collapse_to_compact`, `collapse_to_medium` |

---

## 5. Special views

Beyond compact/medium/full, a type may have **one or more named views for a particular job** — views
that don't correspond to a size at all, reached by their own button rather than by resizing.

Known so far:

| Type | View | Reached by | What it is for |
|---|---|---|---|
| Workflow | **Environment** | the `python` icon button | Establishing and changing what the workflow runs on. §14. |
| Step | **Tool binding** | the bottom bar's assign-tool | Choosing a tool and fulfilling its parameters. §13. |

These occupy the workable area like a full view and keep the breadcrumb, but their content is
purpose-built rather than "this element, bigger". The pattern is worth naming because more will
appear — anywhere a type has a job complex enough that the generic card cannot carry it.

---

## 6. Edit mode

Editing is **in place with explicit save**, not a dialog. It is a state of the card:

- **Not editing** — the toolbar shows a single `edit` button.
- **Editing** — `edit` is replaced by **cancel** (revert to the properties as they were when edit
  began) and **save** (persist, leave edit mode). Editable fields become inputs in place.

Some types expand on entering edit mode: a data point or step in compact view **expands to medium**
so there is room.

What each type makes editable is in §9. Two consequences: the card needs a visibly distinct editing
appearance, and cancel must be genuinely non-destructive — the pre-edit values are held and the user
can trust that.

**Note what is *not* here.** A workflow's Python version is not an editable field alongside name and
description. It has its own button and its own view, because changing it is not an edit — it is a
migration. §14.4.

---

## 7. Two control surfaces

A distinction that resolves where any given button belongs:

**Quick actions (top right)** — operations *on the element as a thing*. Edit, copy, clone, delete,
dissolve, resize, trace. These are the toolbars in §9.

**Bottom bar (some types only)** — operations *on the element's composition*. Not "make editable" or
"copy" but "select tool", "assign inquisition", "add error watch". Heavier, they open sub-surfaces
rather than firing immediately, and there are enough of them on a step to need their own home.

The **step** clearly needs both. Others may; §16.

---

## 8. Element inventory

The icon set exists — 238 SVGs under `composeApp/src/desktopMain/composeResources/`. Two names are
historical: **`phase*` means grouper**, **`inspector*` means inquisition**.

| Element | Icon | What it is | Contains |
|---|---|---|---|
| **Workflow folder** | `folder` | Organises workflows. Nests. | folders, workflows |
| **Workflow** | `workflow` | One automation pipeline. | groupers, steps, data points |
| **Grouper** | `phase` | A container on the canvas. Nests. **Groups spatially, does not sequence.** | groupers, steps |
| **Step** | `step`, or `workflow_head` when it is the entry point | One tool invocation — the unit of work. | tool binding, inquisitions, error watches, reads, writes |
| **Data point** | `data_item` | A typed named value living in a workflow. | — |
| **Inquisition** | `inspector` | A condition at a step that can redirect execution. | redirects |
| **Error watch** | `error_watch` | Watches for a named failure and routes it. | error watch functions |
| **Junction** | `junction` | A branch point between workflows. | — |
| **Tool** | `function` | The Python callable a step invokes. | parameters |
| **Transform** | `functions` | A value transformation applied to a result. | args |

Also available: `default_next`, `step_add`/`step_remove`/`step_next`/`step_settings`,
`phase_add`/`phase_delete`/`phase_dissolve`, `data_item_add`/`_edit`/`_search`/`_readonly`,
`clone`, `paste`, `copy`, `trash`, `pencil`, `check`, `filter`/`filter_off`, `find_data`,
`collapse_all`/`expand_all`, `inspect_earlier`/`inspect_later`, `param`, `params`, `provision`,
`revision`, `dependencies`, `libraries`, `workspaces`.

---

## 9. Action reference

**Specified, not proposed.** This is Wini's, tabulated.

Conventions: **copy** puts the element on the clipboard; **clone** duplicates it in place including
members; **paste** appears only when the clipboard holds a legal payload for this scope; **delete**
always prompts; **dissolve** deletes the container while preserving its members, reparenting them
upward.

### 9.1 Workflow folder

**Compact body** — name · description · `contains x workflowFolders`, or
`contains x workflowFolders (y nested)` when nesting runs deeper than one level, where *x* is the
total and *y* the count sitting inside children · `contains x workflows`, same treatment.

**Toolbar** — edit *(or cancel + save)* · new workflow folder · new workflow · add existing
workflow · copy · clone · paste *(folders and workflows only)* · dissolve · size controls · delete

### 9.2 Workflow

**Compact body** — name · description · pythonVersion · `contains x data points` ·
`contains x steps in y groups`

**Toolbar** — edit *(or cancel + save)* · **python** *(opens the environment view — §14)* · create
grouper · create new step · add existing step · copy · clone · paste *(groupers and steps only)* ·
size controls · delete

### 9.3 Grouper

**Compact body** — name · description · `contains x groups` and `contains x steps`, both with the
nested treatment.

**Toolbar** — edit *(name, description, **colour**)* · new grouper within · create new step · add
existing step · copy · clone · paste *(groupers and steps only)* · dissolve · size controls · delete

### 9.4 Data point

**Compact body** — name · description

**Medium body** — name · description · value · `typeNode.fullyQualifiedPath` · store *(dropdown)* ·
`read-only value` when max writes is 0 · `may be read by x members` · `one write permitted` /
`x writes permitted` · `x steps may overwrite` — or `0 steps may overwrite` **in a warning style**
when writes are permitted but none exist.

**Toolbar** — edit *(name, description, value, typeNode, store, max writes — expands to medium if
needed)* · copy · clone · paste *(a single data point)* · **trace writes** *(when writes are
permitted)* · **trace reads** *(when reads exist)* · size controls · delete

### 9.5 Step

Icon is `workflow_head` when the step is the workflow head, `step` otherwise.

**Compact body** — name · description

**Medium body** — name · description · the tool *(§13)* · the default next step's name, or
`May end workflow` when none is assigned.

**Toolbar** — edit *(name, description, tool, is-workflow-head — reassigning prompts a dialog first
— and default next step)* · **trace route to** *(when not the head: steps pointing here by
default-next, plus inquisitions and error watches that redirect here)* · **next step** *(disabled
when one is already assigned)* · size controls · delete

**Bottom bar** — assign tool, assign inquisition, add error watch, add data point read, add data
point write.

---

## 10. Trace actions — a family

Three toolbars carry a *trace*: **trace writes**, **trace reads**, **trace route to**. The same idea
in three places — *what reaches this, what does this reach* — graph queries surfaced as UI.

They want one consistent treatment. Open question: panel, in-place highlighting, or navigation.
Highlighting is best when the related elements are on screen; a panel is the only option when they
are not, which under the cubby model (§4) is often. It likely needs both.

This family will grow — every relationship in the schema is a candidate trace.

---

## 11. Breadcrumb, history, and getting back

```
Root ▸ Client Projects ▸ Invoice Automation ▸ Extract Phase ▸ Parse PDF
```

- The **root folder is always first** and is permanent — one per user, undeletable.
- Each segment returns to that scope.
- Segments carry their element's icon, reinforcing the vocabulary in a second place.
- **Overflow**: deep paths need a middle collapse (`Root ▸ … ▸ Extract Phase ▸ Parse PDF`) with the
  hidden segments reachable from the ellipsis.

**History is separate.** The breadcrumb shows *containment*; history shows *chronology* — jumping
back to a previously visited element by type and id. Different questions; merging them is a common
mistake. Design a back control plus a recents list.

---

## 12. The detail panel

Read-only summary of whatever is focused. A quick look, not an inspector: no editing, no tabs.

- **Folder** — name, description, full path, counts, created/updated
- **Workflow** — name, description, Python version, counts, last run and outcome
- **Grouper** — name, description, colour, parent, counts
- **Step** — name, description, tool, whether it is the head, next step, counts of inquisitions /
  error watches / reads / writes
- **Data point** — name, description, type, store method, value, read and write counts

With nothing focused it shows the **current scope** rather than going blank — a "where am I" readout
as well as a "what is this" one. Under the cubby model this matters more than it would otherwise,
because siblings are often off-screen.

---

## 13. The tool binding view

A **special view** (§5) on the step, reached from the bottom bar.

### 13.1 The problem

A step invokes a **tool** — a Python function with typed **parameters**. Each parameter is fulfilled
by a **data point read**, and where types don't match, one or more **transforms** convert.

It is a **type-directed binding surface**: typed sources connected to typed sinks with a conversion
pipeline between. That framing tells you the job — make types visible, make mismatches obvious, make
resolution cheap.

### 13.2 Three modes, not three phases

Wini described three "phases". Design them as **modes moved between freely**:

1. **Find a tool** — faceted search.
2. **Create a tool** — the escape hatch from (1), not a later step.
3. **Bind the parameters** — the real work, returned to whenever bindings change.

A wizard would be the mistake: changing the tool later, with bindings already made, has to be a
graceful path rather than a reset.

### 13.3 Finding a tool

The third appearance of one search pattern (data points, tools, transforms). **Design it once with
pluggable facets.**

Tool facets: **return type** (type `str`, results narrow to tools returning strings), **name** (free
text), **tags** (autocompleted, accumulating). Results are cards showing the function name, its
parameters, and its description. Facets are additive and each shows its contribution — a result
count that moves as criteria are added is what tells the user they are converging.

### 13.4 Binding — the layout

**Left to right is data flow.** The parameter list is the spine on the right; binding expands
leftward from the slot, putting the source to the left of what it feeds.

```
                                        ┌──────────────────────────────────┐
                                        │  ƒ  read_and_split               │
                                        ├──────────────────────────────────┤
                                        │  ◯  source_path  : str           │  unfulfilled
                                        │  ◐  encoding     : str           │  bound via transform
                                        │  ●  delimiter    : str  = ","    │  default, muted
                                        └──────────────────────────────────┘
```

Clicking an empty slot opens leftward:

```
┌─ data point search ────┐                          ┌──────────────────────┐
│ ☑ filter to only  str  │                          │  ◯  source_path : str│
│ ────────────────────── │                          └──────────────────────┘
│ 🗄 src_img_path    str │
│ 🗄 out_dir         str │
└────────────────────────┘
```

With the filter **unchecked** and a mismatched data point chosen, transform columns appear between
source and slot, one per hop, until the chain produces the parameter's type:

```
┌─ source ───────┐ ┌─ transform ──────┐ ┌─ transform ──┐   ┌──────────────────┐
│ 🗄 csv_line     │→│ .split(',')      │→│ [0]          │ → │ ◯ source_path:str│
│    str          │ │ str → list[str]  │ │ list[str]→str│   └──────────────────┘
└─────────────────┘ └──────────────────┘ └──────────────┘
```

Each column states what it receives and what it emits. The chain is complete when the last emitted
type equals the parameter's — and **the slot must not read as filled until it does**.

### 13.5 Four additions

**Offer computed chains rather than hop-by-hop hunting.** The system knows every transform's input
types and output type, so a route from `str` to `int` is a graph search it can run itself. On a
mismatch, lead with **complete resolutions** ranked by length — `str → .split(',') → list[str] →
[0] → str` — with manual hop selection underneath for what the search misses. Making the user
assemble a three-hop chain one dropdown at a time, when the answer is computable, is the difference
between clever and paperwork.

**The slot is a state machine.** At minimum:

| State | Reads as |
|---|---|
| empty, required | yellow stroke — needs attention |
| empty, has a default | muted — `using default: ","`, with an override affordance |
| bound, types match | settled, neutral |
| bound through a transform chain | settled, but visibly doing work |
| bound, type unresolved | error — must block saving |
| variadic (`*args` / `**kwargs`) | a repeating shape |
| positional-only / keyword-only | affects whether the name shows as a label or a position |

That last row is real: the schema carries `isPositionalOnly` and `isKeywordOnly` per parameter,
because a generated call has to be legal Python.

**Collapse optional parameters.** Most Python functions have more optional than required. Twelve
visible slots when two are required buries the actual work — put the defaulted ones behind a
`3 optional parameters` disclosure.

**The type filter is the real control.** Checked it prevents mistakes and shortens the list;
unchecked it opens the entire transform apparatus. Default it **checked**, and make unchecking feel
like deliberately entering a more powerful mode.

### 13.6 What it does not need

It does **not** need the workflow's data points visible alongside it. Under the cubby model (§4) they
are outside the visual scope while the step is expanded, and that is correct. The data point
**search** is how they are reached — summoned by name, not selected by proximity. Designing this
surface to keep a data-point browser permanently on screen would be solving a problem the convention
already answers, at the cost of the space the binding work actually needs.

---

## 14. The workflow environment view

A **special view** (§5) on the workflow, reached by the `python` icon button. This is where a user
**begins** when first designing a workflow, and where they return when the environment has to change.

### 14.1 The insight: version-first is backwards

The schema currently makes `pythonVersion` a required property of a workflow, which implies the
first question is *"which Python?"*

**That is not how anyone actually reasons about this.** The real sequence is:

1. I know what I want the workflow to *do* — scrape a site, parse the HTML, write a CSV, chart it.
2. That means libraries: `requests`, `beautifulsoup4`, `pandas`, `matplotlib`.
3. Each of those declares a **Requires-Python**.
4. The **intersection** of those constraints is the set of Python versions that can run this at all.
5. I pick from that set — usually the newest that everything supports.

Version is the **derived answer**, not the opening question. And Owl-E can actually do this
derivation, because `package_python_requirements` holds every distribution's Requires-Python as a
structured operator-plus-version rather than a string. Nothing currently asks it.

So the environment view should be organised around **intent → constraint → choice**, and the Python
version should be presented as a *conclusion the user confirms*, not a dropdown they guess at.

### 14.2 The centrepiece: a constraint chart

The intersection is hard to reason about as a list and trivial to read as a chart. Something like:

```
Python           3.9    3.10   3.11   3.12   3.13   3.14
                 ──────────────────────────────────────────
requests         ████████████████████████████████████████    >=3.8
beautifulsoup4   ████████████████████████████████████████    >=3.7
pandas                         ██████████████████            >=3.11,<3.14
matplotlib              ███████████████████████████████      >=3.10
numpy  (via pandas)  ███████████████████████████             >=3.9,<3.13
                 ──────────────────────────────────────────
viable                         ████████████                  3.11 – 3.12
recommended                           ▲ 3.12
```

This makes three things visible at once that are otherwise buried: which versions are viable, *which
package is responsible* for each edge of that range, and how much headroom there is. It is the
single most useful artefact this view can produce, and it serves both the setup case and the
migration case (§14.4) without changing shape.

Direct dependencies and transitive ones should be visually distinct — `numpy (via pandas)` above —
because "what can I remove to widen this range" is a question only answerable if you can tell which
rows you actually chose.

### 14.3 First-time setup

The empty state matters more here than anywhere else in the product, because this is where a
workflow *begins*. A blank form asking for a Python version is the worst possible opening.

Three viable entry points, and the design should offer all three:

- **Start from intent** — add distributions by searching `distributions` (596k of them, from PyPI).
  Each added draws its band on the chart. The viable range narrows live.
- **Start from a template** — a named starting set (`Data processing`, `Web scraping`,
  `Image pipeline`). Pre-populates several distributions and lands the user in the same place with
  something to react to rather than a blank page.
- **Start from an existing workflow** — copy another workflow's environment wholesale. In practice
  this will be the most-used path once a user has more than one workflow.

Then, throughout:

**Extras.** `package_extras` exists — `requests[socks]`. Each added distribution should expose its
available extras as toggles, because they change the dependency set and therefore the chart.

**Transitive dependencies** arrive automatically from `package_dist_requirements` and should be
shown as *derived* — visible, greyed, not directly removable. Users need to see them (they are why
the range is narrow) without mistaking them for choices.

**Environment markers** — most dependencies are conditional (`; sys_platform == "win32"`). Owl-E
runs workflows in a controlled environment, which means the marker evaluation context is **known**.
Evaluate them and show only what actually applies, rather than making the user read PEP 508
expressions. This is a real simplification the containment model buys, and it should be taken.

**Conflicts** need naming, not just reporting. `No viable Python version` is useless;
`pandas >=3.11 and numpy <3.13 leave 3.11–3.12; adding tensorflow (<3.11) empties the range` is
actionable. Always name the packages forming each edge.

### 14.4 Changing the version — the conversation

Wini's framing exactly: *"upgrading from 3.9 to 3.13 is actually potentially pretty disruptive so
let's talk about what you'd also need to change to make that work."*

This is **not an edit**, which is why the Python version is not an editable field next to name and
description (§6). It is a migration with consequences in three separate places, and the view should
present it as an **impact report before anything changes**:

**1. Package viability** — from `package_python_requirements`. For each distribution:

| Outcome | Presentation |
|---|---|
| already supports the target | fine, no action |
| a newer version supports it | offer the bump, name the version |
| no version supports it | **blocking** — drop it, or replace it, and the user must decide |

The "offer the bump" case should be batchable: *"upgrade these 4 packages"* as one action, leaving
the genuinely blocked ones for individual decisions.

**2. Type availability** — the one users will not anticipate. `type_nodes` are scoped to a Python
version, so a tool whose signature references something that does not exist in the target (say
`asyncio.TaskGroup`, absent before 3.11) breaks. **Downgrades are more dangerous here than
upgrades**, and neither is obvious from the package list. The report must name the affected *steps*,
not the types — "3 steps use tools unavailable in 3.10" with a way to see which.

**3. Transform chains** — bindings built on types that changed shape are the second-order casualty.
Worth at least counting, even if the first version can only say "may need review".

Two design requirements that follow:

- **Nothing changes until the user accepts the whole report.** Half-migrating is worse than not
  migrating.
- **The report is the same chart from §14.2**, with the target version marked and the blocking bands
  highlighted. One visual language for "what can run" and "what would break if I moved".

### 14.5 The non-nullable version, resolved

`workflows.python_version_id` is not nullable, so a workflow always has a version. That does not
conflict with intent-first, and the resolution is a small one:

**A new workflow is born with the newest stable release**, which is what an empty requirement set
resolves to anyway. The field is satisfied from the moment the workflow exists, and the environment
view **revises** rather than initialises — as packages are added, the viable range narrows, and if
the current version falls outside it the view says so and offers the recommendation.

The alternative — making the user pick a version before anything about the workflow is known — is
the easter-egg hunt this design exists to avoid. Owl-E's job is to prepare the field, not to ask the
user to guess at it and find out later they guessed wrong.

### 14.6 What is settled versus proposed

Settled by Wini: the environment is reached by the `python` icon button; it is a special view rather
than a size; the Python version is not edited alongside name and description; a version change
requires a conversation about what else must change.

Everything else in §14 is my proposal — in particular the inversion to intent-first, the constraint
chart, and the three-part impact report. §16 asks the question that most affects it.

---

## 15. Tooltips

Required, and specified rather than left to a component default.

- **Every icon-only button has one.** The toolbars are eight to fourteen unlabelled icons; the
  tooltip is the only thing naming them.
- **Destructive and unfamiliar actions get a description, not a label.** `dissolve` should not say
  "Dissolve" — it should say what will happen, with the consequence filled in: *"Delete this grouper
  and move its 12 steps to Extract Phase"*. Same for delete, and for clone where the member
  duplication is not obvious.
- **Disabled buttons explain why.** A disabled `next step` says a default next step is already
  assigned. This is the reason to show a disabled control rather than hide it — and it is worth
  distinguishing from paste, which Wini specified as *hidden* unless legal.
- Consistent delay and placement; keyboard shortcut shown where one exists.

---

## 16. States and interaction

**Card states** — rest, hover (actions revealed), focused, selected, multi-selected, editing,
dragging, drop-target-valid, drop-target-invalid, disabled.

**Selection** — single or multi? Multi-select makes bulk copy/delete/move worth having and needs an
"N selected" toolbar mode. Affects the card, so decide early.

**Drag and drop** — the tree is reparentable. If supported, the card needs drop-target states and
the same legality rules as paste.

**Empty states** — an empty folder, a workflow with no steps, a search with no matches, a tool with
no parameters, and above all **a workflow with no environment** (§14.3). Common in a tool where
users start from nothing, and the right place for the primary create action.

**Loading** — roughly five levels are prefetched, so navigation is mostly instant. Spinners are an
exception, not a default. The environment view is the one place a genuine wait is plausible
(resolving dependencies across 596k distributions) and it should be designed for.

**Errors** — the server returns structured errors with code, message, and detail. One treatment: a
toast for transient failures, inline text for anything attached to a specific element.

**Keyboard** — arrows between cards, Enter to go full, Escape to go up a level, `/` to focus search,
Delete to delete, Ctrl+C/V. A tool used for hours needs this.

---

## 17. Open questions

1. **What are Air, Parliament, and Benchmarking?** Workflows and Executions are clear. Air is the
   access-point authoring plane; Parliament is undescribed.
2. **Which other types get a bottom bar, and which get special views?** Step has both. Workflow has
   a special view; does it need a bottom bar too?
3. **How do traces present?** Panel, in-place highlight, or navigation. §10.
4. **Single or multi select?**
5. **Drag-to-reparent in v1?**
6. **Does search cover the current scope or the whole prefetched tree?**
7. **Where does the canvas fit?** `posX`/`posY` mean a workflow's full view could be spatial. Under
   the cubby model this reads as *another special view* — "wiring" alongside "environment" — rather
   than as the default. That would resolve it cleanly, but it is Wini's call.
8. **Multiflows** — workflows composed of workflows — exist in the schema with no home yet.
9. **Inquisitions, error watches, and junctions** have icons and schema but no action sets yet.

---

## 18. What to ask for first

1. **The element card**, one type (grouper), in all three sizes, quick actions hidden and revealed,
   and in edit mode. This one component sets the language for everything else.
2. **The workflow environment view** (§14) — empty state, mid-resolution with four packages and a
   live constraint chart, and the migration impact report. This is where a workflow begins, it is
   the most novel screen in the product, and nothing else in the brief depends on it.
3. **The Explorer** — details and icons views over a realistic folder tree, with breadcrumb, search,
   and the detail panel showing a focused item.
4. **The step's tool binding view** (§13) — one parameter bound directly, one through a two-hop
   transform chain, one unfulfilled.

Reference in the repo:
- `web/src/models/` — the UI data models: the element card and its actions (`element.ts`), scope,
  clipboard and traces (`scope.ts`), the environment and migration (`environment.ts`), tool binding
  (`binding.ts`). These are the vocabulary; a mockup that names these fields is one that can be
  wired up rather than reinterpreted.
- `web/src/styles/theme.css` — Darcula tokens, semantic names
- `http://localhost:5173/tokens` — every token rendered live
- `web/src/types/workflow.ts` — real field names and shapes
- `composeApp/src/desktopMain/composeResources/` — the 238 icons
- `docs/TYPE-MODEL-REVIEW.md` — how versions, packages, and requirements actually work
