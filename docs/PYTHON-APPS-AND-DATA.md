# The two Python apps, and where the data actually is

*Written 2026-08-20, from the source and from the live databases. Supersedes recollection.*

Short version: **`type_introspector` has never touched PyPI.** The PyPI work is entirely
`pyside`'s. And the expensive half of that work is already done and sitting in
`owle_test_archive` — what was never finished is the cheap, local, re-runnable half.

---

## 1. What each app does

### `type_introspector` — CPython's type system

Reads CPython **standard library source** from `cpython_srcs/<version>/`, discovers classes by AST
parsing plus runtime introspection, and writes the type graph: `type_nodes`, `type_aliases`,
`typevar_*`, `callable_signatures`, `type_node_relations`, `type_schema_refs`.

Three phases, per `main.py`:

1. modules & classes discovered, inheritance resolved
2. type nodes inserted — base types, aliases, typevars, synthetics, callables
3. relations inserted — ownership, composition, subclassing

**It has no concept of a package, a distribution, a version specifier, or PyPI.** Grepping the
whole module for `wildcard`, `requires_python`, `COMPATIBLE`, or `VersionOperator` returns nothing
outside the vendored CPython sources themselves. It never had this functionality to lose.

Its input is a directory of `.py` files. Its output is the `typing` module of the schema.

### `pyside` — PyPI

Reads **PyPI JSON API metadata** and writes the `environs` module: `distributions`, `packages`,
`versions`, `package_python_requirements`, `package_dist_requirements`, `package_extras`, tags.

It runs in two distinct phases, and this distinction is the whole point of this document:

| | Phase | What it does | Cost |
|---|---|---|---|
| **A** | `processors/api_data.py` → `process_api_data()` | Hits `pypi.org` per (distribution, version), rate-limited with jitter, stores the raw response in `packages.info` (`longtext`) | **Slow. Network-bound. Months.** |
| **B** | `processors/infos.py` → `process_info_data()` | Reads `packages.info` back out, parses it, derives `requires_dist`, `requires_python`, extras, classifiers | **Fast. Local. No network.** |

Phase B never calls PyPI. `build_package_info_model` → `get_info` is a plain
`SELECT id, info FROM packages` — it reads the column phase A filled.

Both phases are driven by CSV files of `(distribution, version)` pairs in a work directory
(`C:\Users\wini\Downloads\info_processing\workdir\`), which is why coverage is patchy rather than
uniform: it processed whatever was listed.

---

## 2. Where the data is

All figures from `owle_test_archive`, measured 2026-08-20.

### The asset

| | Count |
|---|---:|
| `packages` rows (distribution × version) | **6,651,103** |
| …with `info` JSON populated | **454,669** (6.84%) |
| distinct distributions | 611,668 |
| …with at least one version carrying `info` | **112,101** (18.3%) |

The stored JSON is **intact and complete**. Sampling 50,000 rows: 99.96% is valid JSON (22
malformed out of 50,000). A spot check on `pandas 2.2.3` returns `requires_python = ">=3.9"` and
**85** `requires_dist` entries — the full dependency metadata, not a stub.

### What was derived from it

| Table | Rows in archive |
|---|---:|
| `tagged_packages` | 126,291 |
| `package_python_requirements` | **3,761** |
| `package_extras` | 1,202 |
| `package_dist_requirements` | **0** |

**This is the gap.** 454,669 packages have their metadata banked, and fewer than 4,000 had
Requires-Python extracted from it. The dependency graph — `package_dist_requirements` — is
completely empty.

Phase A ran for months and succeeded. Phase B barely ran at all.

### What phase B would yield

Extrapolating from a 50,000-row sample of the rows that have `info`:

- **~64%** declare a `requires_python` → roughly **289,000** packages with real Requires-Python,
  against 3,761 today
- **7.53** dependency lines per package on average → roughly **3.4 million**
  `package_dist_requirements` rows, against 0 today

All of it derivable **locally, from data already on disk, with no network access.**

---

## 3. On "which packages are the relevant ones"

The worry was that identifying the packages worth having requires download statistics from
BigQuery, and that navigating that again for one day's work is not worth it.

Two reasons that is not needed.

**The selection already happened.** Whatever drove those CSV work files produced a set that is
demonstrably popularity-weighted. Coverage for well-known packages:

| Distribution | Versions | With info |
|---|---:|---:|
| boto3 | 1,788 | 1,788 |
| setuptools | 609 | 575 |
| django | 387 | 305 |
| pytest | 183 | 181 |
| numpy | 144 | 118 |
| pandas | 112 | 109 |
| flask | 61 | 60 |

That is not what a random 6.84% sample looks like. The high-value packages are already in hand.

**A better signal is about to exist locally.** Once phase B populates
`package_dist_requirements`, the **in-degree of the dependency graph** — how many packages depend
on a given distribution — is a pure-local relevance ranking. For Owl-E's purposes it is arguably a
*better* signal than download counts: a package that thousands of other packages build on is
structurally important, whereas download counts are heavily skewed by CI systems re-pulling the
same wheels. No external service, no credentials, no re-familiarisation.

---

## 4. What to do

Ordered, cheapest first.

**1. Treat `owle_test_archive.packages.info` as the crown jewels.** It represents months of
rate-limited API calls. Nothing should be done that risks it. It is currently the only copy.

**2. Run phase B against the banked JSON.** `process_info_data()` already does exactly this, but it
is driven by CSV files from a work directory that may not survive. It wants re-pointing at a query
— `SELECT distribution, version_str FROM packages WHERE info IS NOT NULL` — instead of a directory
listing. That is a small change to one function, and it turns a lost work directory from a blocker
into a non-issue.

**3. Expect ~289k Requires-Python and ~3.4M dependency rows.** That is enough to make the
environment view's constraint chart real rather than illustrative.

**4. Then rank by dependency in-degree** and decide whether any further PyPI fetching is worth
doing at all. It may well not be.

**5. Only then consider phase A again**, if at all, for gaps the in-degree analysis says actually
matter.

### Schema caveat

The archive's `packages` table predates the current schema:

```sql
-- archive
PRIMARY KEY (`version_id`,`distribution`)   -- version_id is AUTO_INCREMENT
`info` longtext                              -- the raw PyPI response
```

The live `DistributionPackages` has no `info` column and does not auto-increment `version_id`.
Any migration has to decide whether `info` comes across as a column, moves to a side table, or
stays in the archive as the source that phase B reads from. **Staying put is the cheapest and
safest option** — phase B can read `owle_test_archive.packages` and write into `owle_dev`, keeping
the raw payloads out of the working schema entirely.
