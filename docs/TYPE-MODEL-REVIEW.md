# The Type Model — `environs` and `typing`

*A conceptual guide to the two modules that describe what a Python type is and where Python code
comes from. Written 2026-08-19.*

This is not a schema listing — [REPO-STATE.md](REPO-STATE.md) has the inventory. This explains
**why each table is shaped the way it is**, which part of a Python specification it is answering,
how a real-world string becomes rows, and how those rows get turned back into a decision at
execution time.

Two modules, two jobs:

- **`environs`** — where code comes from and whether it will run here. Versions, distributions,
  dependency specifiers, environment markers.
- **`typing`** — what a value *is*. Classes, generics, unions, callables, type variables.

They meet at one column: `type_nodes.version_id`. A type is only meaningful relative to the Python
release it was found in — `asyncio.TaskGroup` exists in 3.11 and not in 3.10, and the model has to
be able to say so.

---

# Part I — `environs`

## 1. A version is not a string

PEP 440 defines a Python version as a structured thing that happens to be *written* as a string:

```
[N!]N(.N)*[{a|b|rc}N][.postN][.devN][+local]
 │   │      │          │       │      └── local identifier   1.0+ubuntu.1
 │   │      │          │       └───────── development release 1.0.dev3
 │   │      │          └───────────────── post-release        1.0.post2
 │   │      └──────────────────────────── pre-release phase   1.0rc1
 │   └─────────────────────────────────── release segment     1.4.2
 └─────────────────────────────────────── epoch               2!1.0
```

Stored as a string, almost every useful question becomes hard. `"1.10.0" < "1.9.0"` is *true* in
lexicographic order and false in fact. Sorting, range queries, "what is the newest release", "does
this satisfy `>=2.1`" — all of it degrades into parsing every row at query time.

So `versions` stores the decomposition:

| Column | Spec element | Note |
|---|---|---|
| `epoch` | `N!` | defaults 0; exists so a project can restart its numbering |
| `major`, `minor`, `micro` | release segment | the `1.4.2` |
| `phase` | `a`/`b`/`rc`/final | `alpha`, `beta`, `candidate`, `final` |
| `phase_number` | the N in `rc1` | |
| `post_number` | `.postN` | a re-release of the same code |
| `dev_number` | `.devN` | precedes everything |
| `local` | `+local` | varchar — deliberately |
| `has_overflow` | — | see §2 |

Now ordering is a SQL `ORDER BY` over integers, and "the newest 3.x" is an index range scan. At
433k version rows that is the difference between a query and a batch job.

**The CHECK constraints are the spec made structural.** PEP 440 says a pre-release phase always
carries a number, and that post/dev releases attach to a final release. Rather than trusting every
writer to honour that, three constraints enforce it:

```sql
phase = 'final' AND phase_number IS NULL  OR  phase <> 'final' AND phase_number IS NOT NULL
post_number IS NOT NULL AND phase = 'final'  OR  post_number IS NULL
dev_number  IS NOT NULL AND phase = 'final' AND post_number IS NULL  OR  dev_number IS NULL
```

A row that violates PEP 440's shape cannot be written. That is worth more than a validator, because
the introspector is not the only thing that will ever write here.

**`local` is a varchar on purpose.** PEP 440 says local identifiers compare segment-wise with
numeric segments beating alphabetic ones, but they are explicitly *not* orderable against each
other in the general case, and they are not permitted in public index uploads. Treating it as an
opaque tag is honest about that.

## 2. Version overflow — why the table exists

This is the concept most likely to look strange without explanation.

PEP 440 puts **no upper bound on the numeric components**. `1.0` is a version; so is

```
20200101000000000000000000.0.0
```

That is spec-compliant. It is also 26 digits, and the column holding it is `BIGINT UNSIGNED` —
maximum 18,446,744,073,709,551,615, twenty digits. The value does not fit. It is not malformed; it
is simply larger than the type chosen to make ordering fast.

And these are not hypothetical. PyPI contains packages versioned with timestamps, build numbers,
epoch seconds, and the occasional keyboard mash. Any bulk ingest of the index will hit them.

That leaves three options, and only one of them is any good:

1. **Store versions as strings instead.** Solves the rare case by making the common case slow —
   every comparison across 433k rows becomes a parse. Rejected.
2. **Reject the package.** Loses real packages over a formatting curiosity, and silently biases the
   dataset. Rejected.
3. **Store what fits; record separately what didn't; flag the row.** This is what the schema does.

```
versions
  ...numeric components..., has_overflow BOOL

version_overflows
  version_id → versions
  violation  ENUM('epoch','major','minor','micro','micro_extra',
                  'phase_number','post_number','dev_number','whole_version')
  string_value VARCHAR(255)
  UNIQUE (version_id, violation)
```

So `20200101000000000000000000.0.0` is stored with whatever the parser could place in `major`, a
`version_overflows` row saying `violation = 'major', string_value = '20200101000000000000000000'`,
and `has_overflow = true` on the parent.

**`has_overflow` is the important part.** It is the "do not trust my numeric columns" bit. Any query
that orders or compares versions must decide what to do when it encounters one — and because the
flag lives on the parent row, that decision is one boolean away rather than requiring a join to
discover. The honest behaviour is usually to exclude overflowed rows from range comparisons and fall
back to the preserved strings when an exact answer is genuinely needed. Nothing is lost; the
original text is right there.

The unique index on `(version_id, violation)` means each component overflows at most once per
version, which is the only thing that makes sense.

### 2.1 `micro_extra` — a different kind of overflow

One value in that enum is not about magnitude. PEP 440's release segment is `N(.N)*` —
**arbitrarily many** components. `1.2.3.4.5.6` is legal. The table has exactly four slots: epoch,
major, minor, micro.

`micro_extra` is the slot for "there were more release components than the schema has columns". It
is the same mechanism — store what fits, preserve the rest as text, flag the row — applied to
*arity* rather than *size*.

Worth knowing: the current parser in `pyside` detects numeric overflow (`MAX_UINT64`, plus a
20-plus-digit regex as a fast pre-filter) but never emits `micro_extra`. The enum anticipates the
case; the ingest does not yet handle it. A four-component version covers the overwhelming majority
of real packages, so this is a known edge rather than an active problem — but a `1.2.3.4` today
loses its fourth component silently, and that is worth closing before a large re-ingest.

### 2.2 `whole_version` — the backstop

Between per-component overflow and `micro_extra`, every PEP 440 version decomposes: a component too
large is preserved as text, extra components are preserved as text, and the row is flagged. Nothing
observed so far escapes that.

`whole_version` exists so that "nothing observed so far" never has to become a decision made under
pressure. If a version arrives that cannot be represented componentwise at all, it is stored as a
single violation whose `string_value` is the entire original string. The package is kept, the
version is recoverable verbatim, `has_overflow` warns every comparison off the numeric columns, and
no ingest ever faces a choice between dropping a package and storing a lie.

It reuses the existing mechanism rather than adding a column, so the guarantee costs one enum
value.

## 3. Ordering pre-releases

PEP 440's ordering rule is:

```
1.0.dev1  <  1.0a1  <  1.0b1  <  1.0rc1  <  1.0  <  1.0.post1
```

A development release precedes everything. Alpha, beta, and release-candidate precede the final
release. A post-release follows it. This is why the CHECK constraints tie `dev_number` and
`post_number` to `phase = 'final'` — in this model, "final" means "no pre-release phase", and
`.dev`/`.post` are modifiers on that.

**The enum declaration order is load-bearing.** MySQL orders an `ENUM` by declaration order, so
`enum('alpha','beta','candidate','final')` makes `ORDER BY phase` sort correctly with no `CASE` at
any call site — in SQL and in Kotlin alike, since `PrereleasePhase` is declared to match. It was
previously declared `final` first, which ranked prereleases above their own final release. Anyone
reordering these constants silently reverses version comparison everywhere.

## 4. Distributions and packages

Two distinct concepts that are easy to conflate:

- A **distribution** is a name on an index — `requests`, `numpy`. That is its whole identity, which
  is why `distributions` is a bare string-keyed table with no other columns.
- A **package** is a distribution *at a version* — `requests 2.31.0`. Its natural key is the pair,
  and `packages` is keyed on `(version_id, distribution)` accordingly.

Everything hanging off a package — extras, Python requirements, dependency requirements — points
back at **both halves** with a compound foreign key:

```kotlin
foreignKey(
    packageVersion to DistributionPackages.version,
    distribution   to DistributionPackages.distribution,
    name = "ppr_fk_pkg"
)
```

The source notes that the version half is deliberately *not* a reference to `versions`, and that is
the right call: the meaningful parent of "this package requires Python >= 3.8" is the *package*, not
the abstract version number. Referencing `versions` directly would let a child row point at a
version the package doesn't have.

**Extras** (`package_extras`) are PEP 508's optional feature sets — the `[dev]` in
`pip install requests[socks]`. A package declares which extras it offers; a *requirement* can then
ask for one, and markers can test `extra == "dev"` (see §6).

A practical consequence worth carrying forward: these composite keys contain a **VARCHAR** — the
distribution name. Any code that handles composite ids has to cope with key parts that aren't
numbers.

## 5. Specifiers — saying which versions you mean

A dependency rarely names one version; it names a *set*. `VersionOperator` covers PEP 440's
comparison operators:

| Constant | Written | Means |
|---|---|---|
| `LESS_THAN` / `GREATER_THAN` | `<` `>` | strict bounds, excluding pre-releases by default |
| `LESS_THAN_OR_EQ` / `GREATER_THAN_OR_EQ` | `<=` `>=` | inclusive bounds |
| `EQUALS` / `NOT_EQ` | `==` `!=` | with wildcard support |
| `COMPATIBLE` | `~=` | compatible release |
| `EXACT` | `===` | arbitrary equality — string identity, escape hatch |

Two of these need explaining because they are not simple comparisons.

**`~=` (compatible release)** means "at least this, and don't change the component to the left of
the last one specified". `~=2.2` means `>=2.2, ==2.*`. `~=1.4.5` means `>=1.4.5, ==1.4.*`. It is
shorthand for a range whose upper bound depends on *how precisely the lower bound was written* —
which is why `compatible_version_bounds()` in `pyside/db_ops/versions.py` expands it to an explicit
`(lower, upper)` tuple before querying. Storing the operator alone would lose the precision that
determines the upper bound.

**Wildcards** (`==1.4.*`) are why `WildcardSegment` exists. The specifier tables carry an operator
*and* a wildcard position — `NONE`, `MAJOR`, `MINOR`, `MICRO` — so `==1.4.*` is stored as
`EQUALS` + `MINOR`, meaning "match on epoch/major/minor, ignore everything to the right". That turns
into a prefix comparison rather than an equality, and keeping the two facts in separate columns is
what lets the query be built mechanically.

Note `python_version_specifiers` keys on `(pythonVersion, operator, wildcard)` — the operator and
wildcard are part of the *identity* of the specifier, not attributes of it. A Python requirement is
a set of such clauses ANDed together (`>=3.8,<4.0`), so the same version can appear twice under
different operators.

## 6. Environment markers — conditional dependencies

This is the richest part of `environs`, and the part where the shape of the schema is doing the most
work.

### 6.1 What they are

PEP 508 lets a dependency be **conditional on the environment it is being installed into**:

```
requests ; python_version >= "3.8"
pywin32  ; sys_platform == "win32"
pytest   ; extra == "dev"
colorama ; sys_platform == "win32" and python_version < "3.9"
```

Everything after the `;` is a **marker** — a boolean expression over a closed set of variables
describing the target environment. If it evaluates false, the dependency simply isn't required
here. This is how one package metadata file serves Windows and Linux, Python 3.8 and 3.13, and
optional feature sets, all at once.

The variables are fixed by the spec, and `EnvMarkerVariable` enumerates them:
`python_version`, `python_full_version`, `os_name`, `sys_platform`, `platform_machine`,
`platform_python_implementation`, `platform_release`, `platform_version`, `implementation_name`,
`implementation_version`, and `extra`.

`extra` is the odd one out: it is not a property of the machine but of the *request* — it is true
when the user asked for that optional feature set. That is what makes `pytest ; extra == "dev"` work.

### 6.2 Why a tree and not a string

Markers nest arbitrarily:

```
python_version >= "3.8" and (sys_platform == "win32" or extra == "dev")
```

If the marker is stored as text, then every question about it — "which packages need something only
on Windows?", "would this dependency apply under Python 3.12?" — requires parsing every row at query
time, and the answer can't be indexed. Worse, evaluation and querying end up as two separate
implementations of the same grammar, which will drift.

So the marker is **shredded into an expression tree** at ingest, and stored as nodes:

```
env_marker_expressions          the node table
  id, requirement_id, parent_id, mode ∈ {LOGICAL, COMPARATIVE}

env_marker_logicals             the body of a LOGICAL node
  expression_id, operator ∈ {AND, OR}, left_child_id, right_child_id

env_marker_comparisons          the body of a COMPARATIVE node (a leaf)
  expression_id, variable, operator, value
```

`mode` is the discriminator saying which body table to look in — a classic supertype/subtype split.
`parent_id` gives the tree its shape; the root has `parent_id IS NULL` and belongs to a requirement.

### 6.3 A worked example

`python_version >= "3.8" and (sys_platform == "win32" or extra == "dev")` becomes:

```
                    ┌──────────────────────────┐
                    │ expr #1  LOGICAL  (root) │
                    │ logicals: AND, L=#2 R=#3 │
                    └────────────┬─────────────┘
              ┌──────────────────┴──────────────────┐
   ┌──────────▼────────────┐          ┌─────────────▼────────────┐
   │ expr #2  COMPARATIVE  │          │ expr #3  LOGICAL         │
   │ python_version >= 3.8 │          │ logicals: OR, L=#4 R=#5  │
   └───────────────────────┘          └─────────────┬────────────┘
                                   ┌────────────────┴───────────────┐
                        ┌──────────▼───────────┐      ┌─────────────▼────────┐
                        │ expr #4 COMPARATIVE  │      │ expr #5 COMPARATIVE  │
                        │ sys_platform == win32│      │ extra == dev         │
                        └──────────────────────┘      └──────────────────────┘
```

Five rows in `env_marker_expressions`, one in `env_marker_logicals` for #1, one for #3, and three in
`env_marker_comparisons` for the leaves.

### 6.4 Evaluating it

To decide whether a dependency applies, you build an **environment** — a map from each marker
variable to the value it has *here*:

```
python_version               → "3.12"
sys_platform                 → "linux"
platform_machine             → "x86_64"
implementation_name          → "cpython"
extra                        → whatever the caller asked for, or unset
```

Then walk the tree from the root and fold:

- At a **COMPARATIVE** node: look up `variable` in the environment, compare it to `value` using
  `operator`, yield a boolean. The comparison is *string* comparison for most variables — but
  `python_version` and `python_full_version` compare as **PEP 440 versions**, which is exactly why
  §1's decomposition matters. `"3.10" < "3.9"` as text; `3.10 > 3.9` as versions.
- At a **LOGICAL** node: evaluate both children and combine with `AND` or `OR`.
- The root's boolean is the answer.

That is the *execution* direction. The *query* direction is what the tree really buys: because
comparisons are rows, "every requirement conditional on Windows" is
`WHERE variable = 'sys_platform' AND value = 'win32'` — an indexable predicate rather than a
`LIKE '%win32%'` over marker text.

### 6.5 Two modelling notes

**`variable` is the `EnvMarkerVariable` enum**, not free text. The distinction matters more than it
looks: a marker naming something outside PEP 508's closed set — `sys.platform`, `python-version` —
is not a condition that happens to be false, it is a marker nobody can evaluate. Stored as text such
a typo ingests cleanly and the dependency silently never applies, with nothing to say why. As an
enum it fails at load, which is where it should fail.

**Logicals are strictly binary** — one left child, one right child. `a and b and c` must be stored
as a nesting, and both `(a and b) and c` and `a and (b and c)` evaluate identically for `and`/`or`,
so either is correct in isolation. But two writers choosing differently produce structurally
different trees for the same marker, which makes structural comparison and deduplication
unreliable.

**The convention is left-associative** — `((a and b) and c)` — matching how the expression reads and
how every parser generator resolves it by default. It is a convention rather than a constraint: the
schema cannot enforce it, so a writer that nests the other way produces a valid tree that simply
will not compare equal to its twin. Anything that ingests markers should fold left and say so.

---

# Part II — `typing`

## 7. Why every type is one row in one table

Python type expressions are **recursive**. `dict[str, list[int | None]]` is a type; so is each piece
of it. Whatever represents a type has to be able to point at other types, including itself, at
arbitrary depth.

So `type_nodes` is a universal spine: every type of every kind is a row, and everything else in the
module either hangs off a node or joins two nodes together.

```
type_nodes
  id, version_id, namespace, name, kind, alias, is_generic, dedup_hash
```

`version_id` scopes the node to a Python release — the seam with `environs`. `namespace` + `name`
locate a named type (`asyncio` + `Task`). `kind` says what *form* of type expression it is, and
therefore which satellite tables apply.

## 8. The kinds, and what they mean in Python

`kind` is the discriminator, and it is the one part of the module whose axis needs settling.

The forms a Python type expression can take are:

| Form | Python | Where its detail lives |
|---|---|---|
| **base** | a named class — `int`, `asyncio.Task` | the node itself |
| **instantiation** | `list[str]`, `dict[str, int]` | `type_instantiations` + `_args` |
| **union** | `str \| int`, `Optional[X]` | `type_union_members` |
| **typevar** | `T = TypeVar("T")` | `typevar_bounds` / `_constraints` / `_variances` |
| **callable** | a function signature as a value | `callable_signatures` + `_parameters` |
| **alias** | `Vector = list[float]` | `type_aliases` |

**The axis is type-expression form, and only that.** `TypeNodeKind` previously also declared
`method`, `attribute`, `class`, `generic`, `primitive`, and `function`; all six are gone, because
each sat on a different axis:

- `method` / `attribute` — a method is not a *type*; it is a **name bound to a type on an owner**,
  which is exactly what `type_schema_refs.member_kind` records, and it already covers `property` and
  `classvar` too. The same fact expressible in two places is a disagreement waiting to happen.
- `class` — indistinguishable from `base`; the introspector writes `base` for classes.
- `primitive` — `int` and `str` *are* classes in Python, so they are `base` like any other.
- `generic` — `is_generic` is already a boolean column on the same row.
- `function` — a signature is `callable`.

What is left is the five forms above, each of which names a distinct shape of type expression and
points at a distinct satellite table. An alias is not a sixth form: an alias node takes the kind of
whatever it aliases, and the `type_aliases` row is what marks it as one (§16).

## 9. Structural identity — what `dedup_hash` is for

Here is the problem it solves. The introspector walks module after module. In `json` it encounters
`list[str]`. In `csv` it encounters `list[str]` again. These are **the same type** — but nothing
about the second encounter says so, because the type has no name to look it up by.

For *named* types this is easy: `(version, namespace, name)` is a natural key, and `asyncio.Task` is
`asyncio.Task` wherever you meet it. For **structural** types — instantiations, unions, callables —
there is no name. Identity is determined entirely by *shape*.

`dedup_hash char(64) UNIQUE` is the slot for a content hash over that shape:

- for `base` — the version, namespace, and name
- for `instantiation` — the origin's hash plus the ordered argument hashes
- for `union` — the *set* of member hashes, order-independent (see §11)
- for `callable` — the parameter and return hashes, in order

Compute it bottom-up and two independently-discovered `list[str | None]` nodes collide on the unique
index instead of becoming two rows.

Why it matters beyond tidiness: **assignability rests on identity**. Asking "can step A's output
feed step B's input" ultimately reduces to comparing type nodes. If the same type can exist as
several rows, every comparison has to be structural at query time, and the relationship tables
(which join *node ids*) stop meaning what they appear to mean.

**The natural key is not an alternative here.** `(version_id, namespace, name, kind)` identifies
named types fine, but structural types have no namespace and no name — every `list[X]` instantiation
in a given version would collapse to `(version, NULL, NULL, 'instantiation')` and collide with every
other. Structural identity has to be computed; there is nothing else to key on. So the hash stays.

**And it cannot be nullable.** MySQL's `UNIQUE` permits unlimited NULLs — that is the SQL standard's
behaviour and there is no setting that changes it, no partial index, no "distinct nulls" mode. A
nullable hash therefore enforces nothing until it is written, and a half-populated column raises no
complaint at all. The fix is not to constrain NULL, it is to have none: every node has a structural
form, so every node can be hashed, and the column is `NOT NULL`. The question of what to do about
missing hashes then cannot arise.

## 10. Inheritance

```
type_node_relations
  parent_node, child_node, relation_kind ∈ {inheritance, instantiation}
  PRIMARY KEY (child_node, parent_node)
```

A join table rather than a parent pointer, because Python has **multiple inheritance** —
`class C(A, B)` is ordinary. Keying on `(child, parent)` and indexing that way makes "what are C's
bases" and "what inherits from A" both cheap.

**`position` records base order**, and it is not cosmetic. `class C(A, B)` and `class C(B, A)` are
different classes: the method resolution order differs, and with it which `__init__` runs and which
implementation of a shared method wins. Without the column that distinction is simply lost, and any
question of the form "what actually resolves here" becomes unanswerable.

**One relationship per pair.** Because `relation_kind` sits outside the primary key, a given
(child, parent) pair holds exactly one relationship. If a node could ever both inherit from and
instantiate the same origin, that is unrepresentable — worth knowing as a deliberate bound rather
than discovering later as a constraint violation.

## 11. Unions are sets

```
type_union_members
  union_node, member_node
  PRIMARY KEY (union_node, member_node)
```

No position column, and that is correct: `str | int` and `int | str` are **the same type**. Python's
union is unordered and flattening — `(str | int) | float` is `str | int | float`, and duplicates
collapse.

This is also why the union's `dedup_hash` must hash an *order-independent* digest of its members.
Hashing them in discovery order would make the two spellings distinct, which is exactly the bug the
hash exists to prevent.

## 12. Generics — origin plus ordered arguments

```
type_instantiations       instantiation_node, origin_node
type_instantiation_args   instantiation_id, param_node, param_position
                          UNIQUE (instantiation_id, param_position)
```

`dict[str, int]` decomposes into an **origin** (`dict`) and an ordered argument list (`str`, `int`).
Order is essential here in a way it is not for unions — `dict[str, int]` and `dict[int, str]` are
different types — hence `param_position` and the unique index on it.

`type_generic_params` is the other side of the same coin: it records that a *class* is generic over
certain type variables and in what order — `class Mapping(Generic[K, V])` gives K position 0 and V
position 1. Together they let an instantiation be checked against its origin's arity, and let
`Mapping[str, int]` be resolved to `K=str, V=int`.

## 13. Type variables — bounds, constraints, variance

Three separate tables, because they are three separate concepts that PEP 484 keeps apart:

- **`typevar_bounds`** — `T = TypeVar("T", bound=Sized)`. An upper bound: T may be `Sized` or any
  subtype of it. One bound.
- **`typevar_constraints`** — `T = TypeVar("T", str, bytes)`. An enumerated set: T is *exactly* one
  of these, not a subtype. Several constraints.
- **`typevar_variances`** — covariant, contravariant, or invariant. Governs whether `Box[Cat]` is
  usable where `Box[Animal]` is expected. Uniquely indexed on the node, since a TypeVar has exactly
  one variance.

The distinction between bound and constraints matters and is easy to blur: a bound is "anything
below this", constraints are "one of exactly these". A TypeVar may have one or the other, never both.
No CHECK can enforce that — it spans two tables — so it is a service-layer rule, worth writing down
where the rule can be seen.

## 14. Callables

```
callable_signatures    type_node, return_type_node, docstring, is_static, is_classmethod
callable_parameters    signature, position, param_name, param_type_node,
                       is_vararg, is_kwarg, default_value_text
                       UNIQUE (signature, position)
```

A function's *type* is its signature, so the signature hangs off a `type_node` and the node is the
thing other types can point at. Parameters are positioned and uniquely indexed, which is what makes
the signature reconstructable.

`docstring` is worth keeping populated: when the execution environment is choosing or describing
tools, the docstring is the human-readable account of what the callable does.

**`default_value_text` is text, not a value** — and it has to be, because a default can be an
arbitrary expression (`def f(x=os.cpu_count())`). The consequence is that defaults are for
*display*, not evaluation. Worth stating as intent so nobody later tries to trust them.

**Calling convention** is recorded, because modern Python distinguishes where an argument may be
passed:

```python
def f(a, b, /, c, d, *, e, f):
    #      ^          ^
    #      |          └── everything after * is keyword-only
    #      └───────────── everything before / is positional-only
```

`is_vararg` and `is_kwarg` mark `*args` and `**kwargs` themselves, but not the *sections* those
markers delimit — so they cannot say that `a` may not be passed by name, or that `e` may not be
passed positionally. `is_positional_only` and `is_keyword_only` carry that, and the execution
environment needs both to emit a call that Python will accept.

## 15. Composition — `type_schema_refs`

Inheritance says what a class *is a kind of*. This says what it *contains*.

```
type_schema_refs
  id, parent_ref_id, class_node_id, name,
  member_kind ∈ {attribute, method, property, classvar},
  type_node_id, description, position
```

Read one row as: *on class `class_node_id`, there is a member called `name`, of kind `member_kind`,
whose type is `type_node_id`.*

This is what serialization needs — to write a value out you must know its fields — and what the UI
needs to render a value or offer its members for binding in a workflow step.

The `type_node_id` half is what makes it useful. `name = "cancel", member_kind = method` tells you a
method exists; only the type node tells you what it takes and returns, and that is what a workflow
needs in order to decide whether it can wire something into it.

**Composition is a walk over nodes, not a tree of refs.** There were two readings available here,
and the table used to carry a `parent_ref_id` for the second:

- **Point `type_node_id` at the member's own type**, and let *that* node carry its own schema refs.
  Each type describes itself exactly once, and composition is a graph walk.
- **Nest refs directly**, describing the member's members inline — which restates whatever the
  member's own type already says, in a second place that can disagree with the first.

The first is the composable reading and the one the model takes; `parent_ref_id` is gone. Describing
`Foo.bar: Baz` means pointing at the `Baz` node, and `Baz`'s own refs describe `Baz`. Nothing is
said twice.

## 16. Aliases

```
type_aliases   id (= type_nodes.id, and the FK), target
```

A 1:1 extension table where the primary key *is* the foreign key — an elegant way to say "some nodes
have this extra fact, at most once". `Vector = list[float]` gives an alias node whose target is the
`list[float]` node.

**Many aliases, one target — already.** Each alias is its own node with its own `type_aliases` row,
so `Vector = list[float]` and `Vec = list[float]` are two alias nodes pointing at the same target
node. The 1:1 constraint is per *alias*, not per target: an alias resolves to exactly one thing,
which is what an alias means. Many-to-one needs nothing added.

`type_nodes.alias`, a varchar answering the different question "this node is also known by this
name", is gone. It duplicated part of what `type_aliases` says while being unable to say the rest of
it, and two places to look for aliasing is one too many.

---

# Part III — How this gets used

The model earns its keep by answering four questions at execution time.

**Can step A's output feed step B's input?** Walk from A's output node: is it B's input node
(identity — hence §9), a subtype of it (`type_node_relations`), a member of it if the target is a
union (`type_union_members`), or a compatible instantiation (same origin, assignable args, respecting
variance from `typevar_variances`)? This is the core safety question, and every part of it is a
traversal from `type_nodes`.

**What does this value look like?** `type_schema_refs` by `class_node_id`, following `type_node_id`
to describe each member. Serialization, UI rendering, and offering a value's fields as bindable
inputs all use the same walk.

**Does this tool exist in the environment we're about to run?** `type_nodes.version_id` →
`python_versions` → `versions`. `asyncio.TaskGroup` scoped to 3.11+ is simply absent from the 3.10
node set.

**Will this dependency install here?** Take the requirement, evaluate its marker tree against the
target environment (§6.4), and if true, check the version specifier against available packages
(§5) — with overflowed versions (§2) excluded from range comparison or resolved from their
preserved strings.

Each of these is a graph traversal rooted in one of the two spines — `type_nodes` or `versions` —
which is the payoff for having a single spine per module rather than a table per Python construct.

---

# Appendix — decisions taken, and what is left

Everything below was an open question when this document was first written. Ten of eleven are
settled and applied; the schema, the Kotlin model and both databases agree.

## Applied

| | Decision |
|---|---|
| 1 | `env_marker_comparisons.variable` is the `EnvMarkerVariable` enum. A marker naming something outside PEP 508's set now fails at load rather than silently never matching. |
| 2 | `TypeNodeKind` is the five type-expression forms. `method`, `attribute`, `class`, `generic`, `primitive` and `function` are removed. |
| 3 | `dedup_hash` stays and is `NOT NULL`. The natural key cannot substitute — structural types have no name to key on — and a nullable unique column enforces nothing until written. |
| 4 | `phase` is declared `alpha, beta, candidate, final`, so `ORDER BY phase` is correct without a `CASE` anywhere. |
| 5 | `callable_parameters` carries `is_positional_only` and `is_keyword_only`, so a generated call site can be legal. |
| 6 | `micro_extra` documented as an arity overflow, and `whole_version` added as the backstop that guarantees no ingest ever chooses between dropping a package and storing a lie. |
| 7 | `type_node_relations.position` records base order, which fixes the method resolution order. |
| 8 | Marker logicals fold **left** — `((a and b) and c)`. A convention, not a constraint: the schema cannot enforce it, so writers must agree. |
| 9 | `type_schema_refs.parent_ref_id` removed. A member points at its own type's node, and that node carries its own refs. |
| 10 | `type_nodes.alias` removed; `type_aliases` is the single account of aliasing. Many aliases may target one node already. |
| 11 | `ClassInheritance` and `TypeParameter` DTOs deleted, superseded by `TypeNodeRelation` and `TypeGenericParam`. |

## Left open

**`micro_extra` is still never emitted.** The enum value and its meaning are settled; the `pyside`
parser detects numeric overflow but does not yet split release segments past the fourth, so a
`1.2.3.4` loses its fourth component silently. This is ingest work, not schema work, and it belongs
with the next introspector run.

**Structural hashing is unimplemented.** `dedup_hash` is now `NOT NULL`, which means the ingest must
produce one for every node — bottom-up, over the normalised structural form, order-independent for
unions (§9, §11). Nothing writes it yet.
