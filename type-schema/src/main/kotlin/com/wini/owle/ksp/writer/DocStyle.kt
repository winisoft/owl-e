package com.wini.owle.ksp.writer


/**
 * The stylesheet the generated reference pages carry with them.
 *
 * Darcula, deliberately and only Darcula — these pages are read by someone who spends the day in
 * the IDE that palette comes from, so the page is grounded in that world rather than given a
 * light and dark variant that would land in neither. Every colour is painted explicitly so the
 * page holds whatever ground the host renders it on.
 *
 * The colour roles are lifted from Darcula's own token meanings rather than invented: an error
 * code is a compile-time constant, so it takes the constant purple; a KDoc note takes the italic
 * doc-comment green; type names take the annotation yellow. A reader who knows the theme can
 * read the page's structure from its colours before reading a word.
 */
internal object DocStyle {

    const val CSS: String = """
:root {
  --ground:        #2B2B2B;  /* editor background */
  --chrome:        #3C3F41;  /* tool window       */
  --gutter:        #313335;  /* line-number strip */
  --rule:          #4E5254;
  --text:          #A9B7C6;  /* default text      */
  --muted:         #808080;  /* line comment      */
  --keyword:       #CC7832;  /* keyword orange    */
  --constant:      #9876AA;  /* static final      */
  --string:        #6A8759;  /* string literal    */
  --number:        #6897BB;  /* numeric literal   */
  --annotation:    #BBB529;  /* annotation        */
  --identifier:    #FFC66D;  /* method name       */
  --doc:           #629755;  /* doc comment       */
  --link:          #589DF6;
  --focus:         #466D94;

  --mono: "JetBrains Mono", "Cascadia Code", "SF Mono", Consolas, "Liberation Mono", monospace;
  --step--1: 0.78rem;
  --step-0:  0.92rem;
  --step-1:  1.05rem;
  --step-2:  1.35rem;
  --step-3:  1.9rem;
}

*, *::before, *::after { box-sizing: border-box; }

body {
  margin: 0;
  background: var(--ground);
  color: var(--text);
  font-family: var(--mono);
  font-size: var(--step-0);
  line-height: 1.65;
  -webkit-font-smoothing: antialiased;
}

a { color: var(--link); text-decoration: none; }
a:hover { text-decoration: underline; }
:focus-visible { outline: 2px solid var(--focus); outline-offset: 2px; }

.shell {
  display: grid;
  grid-template-columns: minmax(200px, 260px) minmax(0, 1fr);
  gap: 0;
  min-height: 100vh;
  align-items: start;
}

/* --- tool window ------------------------------------------------------------------ */

.rail {
  position: sticky;
  top: 0;
  max-height: 100vh;
  overflow-y: auto;
  background: var(--chrome);
  border-right: 1px solid var(--rule);
  padding: 1.75rem 1.25rem;
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.rail-title {
  font-size: var(--step--1);
  letter-spacing: 0.14em;
  text-transform: uppercase;
  color: var(--muted);
  margin: 0;
}

.rail nav ul { list-style: none; margin: 0; padding: 0; display: flex; flex-direction: column; gap: 0.15rem; }
.rail nav > ul > li > a { color: var(--keyword); }
.rail nav ul ul { padding-left: 0.9rem; border-left: 1px solid var(--rule); margin-top: 0.15rem; }
.rail nav ul ul a { color: var(--constant); font-size: var(--step--1); }
.rail nav a { display: block; padding: 0.1rem 0.35rem; border-radius: 2px; }
.rail nav a:hover { background: var(--gutter); text-decoration: none; }

.count {
  color: var(--number);
  font-variant-numeric: tabular-nums;
}

/* --- editor pane ------------------------------------------------------------------ */

.pane {
  min-width: 0;
  padding: 2.5rem clamp(1.25rem, 4vw, 3.5rem) 6rem;
  max-width: 100ch;
}

.doc-title {
  font-size: var(--step-3);
  font-weight: 700;
  color: var(--identifier);
  margin: 0 0 0.35rem;
  text-wrap: balance;
}

.lede {
  border-left: 3px solid var(--doc);
  background: var(--gutter);
  padding: 0.85rem 1.1rem;
  margin: 1.25rem 0 0.75rem;
  color: var(--doc);
  font-style: italic;
  max-width: 72ch;
}

.tally { color: var(--muted); font-size: var(--step--1); margin: 0 0 2.5rem; }
.tally strong { color: var(--number); font-weight: 400; font-variant-numeric: tabular-nums; }

.family { margin: 0 0 3rem; }

.family > h2 {
  font-size: var(--step-2);
  font-weight: 700;
  color: var(--keyword);
  margin: 0;
  padding-left: 0.85rem;
  border-left: 3px solid var(--keyword);
  text-wrap: balance;
}

.family > .blurb {
  color: var(--muted);
  margin: 0.6rem 0 1.75rem 0.85rem;
  max-width: 68ch;
}

.entries { display: flex; flex-direction: column; gap: 0.9rem; }

.entry {
  background: var(--gutter);
  border: 1px solid var(--rule);
  border-radius: 3px;
  padding: 1rem 1.15rem;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.entry > header { display: flex; flex-wrap: wrap; align-items: baseline; gap: 0.4rem 0.9rem; }

.code {
  font-size: var(--step-1);
  font-weight: 700;
  color: var(--constant);
  word-break: break-all;
}

.type { color: var(--annotation); font-size: var(--step--1); }
.type::before { content: "@"; color: var(--muted); }

.message { margin: 0; color: var(--text); max-width: 70ch; }

.notes {
  margin: 0;
  color: var(--doc);
  font-style: italic;
  font-size: var(--step--1);
  max-width: 70ch;
}

.raised { font-size: var(--step--1); }

.raised summary {
  cursor: pointer;
  color: var(--muted);
  list-style: none;
}
.raised summary::-webkit-details-marker { display: none; }
.raised summary::before {
  content: "\25B8";
  display: inline-block;
  width: 1em;
  color: var(--keyword);
  transition: transform 120ms ease;
}
.raised[open] summary::before { transform: rotate(90deg); }
@media (prefers-reduced-motion: reduce) {
  .raised summary::before { transition: none; }
}
.raised summary:hover { color: var(--text); }

.fields {
  list-style: none;
  margin: 0.65rem 0 0 1em;
  padding: 0;
  display: flex;
  flex-wrap: wrap;
  gap: 0.3rem 0.4rem;
}

.fields li {
  background: var(--chrome);
  border: 1px solid var(--rule);
  border-radius: 2px;
  padding: 0.05rem 0.4rem;
  color: var(--string);
  white-space: nowrap;
}
.fields li .owner { color: var(--number); }
.fields li .dot { color: var(--muted); }

.none { color: var(--muted); font-size: var(--step--1); }

/* --- categories ------------------------------------------------------------------- */

.category { margin: 0 0 4.5rem; }

.category-title {
  font-size: var(--step-3);
  font-weight: 700;
  color: var(--identifier);
  margin: 0 0 0.5rem;
  padding-bottom: 0.4rem;
  border-bottom: 1px solid var(--rule);
  text-wrap: balance;
}

.category > .blurb { color: var(--muted); margin: 0 0 2.5rem; max-width: 70ch; }

.code.model { color: var(--number); }

/* --- tables ----------------------------------------------------------------------- */

.scroller { overflow-x: auto; }

table {
  border-collapse: collapse;
  width: 100%;
  font-size: var(--step--1);
}

thead th {
  text-align: left;
  font-weight: 400;
  color: var(--muted);
  text-transform: uppercase;
  letter-spacing: 0.1em;
  font-size: 0.7rem;
  padding: 0 0.75rem 0.4rem 0;
  border-bottom: 1px solid var(--rule);
  white-space: nowrap;
}

tbody td {
  padding: 0.35rem 0.75rem 0.35rem 0;
  border-bottom: 1px solid rgba(78, 82, 84, 0.35);
  vertical-align: top;
}
tbody tr:last-child td { border-bottom: 0; }

.f-name  { color: var(--identifier); white-space: nowrap; }
.f-type  { color: var(--number); white-space: nowrap; }
.f-desc  { color: var(--text); min-width: 22ch; }
.f-rules { display: flex; flex-wrap: wrap; gap: 0.25rem; }

a.rule {
  color: var(--constant);
  border: 1px solid var(--rule);
  border-radius: 2px;
  padding: 0 0.3rem;
  white-space: nowrap;
}
a.rule:hover { background: var(--chrome); text-decoration: none; }

.fields li a { color: inherit; }
.fields li:hover { border-color: var(--constant); }

/* --- http verbs ------------------------------------------------------------------- */

.verb {
  display: inline-block;
  min-width: 5.5ch;
  text-align: center;
  font-weight: 700;
  font-size: 0.72rem;
  letter-spacing: 0.06em;
  padding: 0.05rem 0.4rem;
  border-radius: 2px;
  border: 1px solid currentColor;
}
.v-get    { color: var(--number); }
.v-post   { color: var(--string); }
.v-put    { color: var(--identifier); }
.v-delete { color: #C75450; }

.tags { display: flex; flex-wrap: wrap; gap: 0.3rem; margin: 0; }
.tag {
  font-size: var(--step--1);
  color: var(--annotation);
  border: 1px solid var(--rule);
  border-radius: 2px;
  padding: 0 0.35rem;
}

@media (max-width: 760px) {
  .shell { grid-template-columns: minmax(0, 1fr); }
  .rail { position: static; max-height: none; border-right: 0; border-bottom: 1px solid var(--rule); }
}
"""
}
