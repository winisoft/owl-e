# Owl-E web

React + TypeScript + Vite, talking to the Ktor server over `/api`.

## Running

Two processes. The server first:

    ./gradlew :server:run

then the UI:

    npm --prefix web run dev

Ktor listens on **9631**, Vite on **5173**, and Vite proxies `/api` through — so the browser
only ever sees one origin and there is no CORS configuration to keep in step.

Open <http://localhost:5173/tokens> to confirm both halves are up: the strip at the top of that
page reports whether the server answered.

## What is here

    src/styles/theme.css   Darcula, as tokens. Raw palette and semantic names kept apart.
    src/styles/index.css   Tailwind, plus the tokens bridged into its colour namespace.
    src/api/client.ts      One generic resource; every endpoint answers the same eight operations.
    src/types/workflow.ts  The DTO shapes a canvas and its inspector read.
    src/routes/Tokens.tsx  Live specimen of the theme, and the server check.

## Conventions

Components reference **semantic** tokens (`--surface-panel`, `--text-secondary`) and never the raw
`--dc-*` palette. Re-theming then means rebinding one block rather than auditing every component.

Tokens are bridged into Tailwind, so `bg-surface-panel` and `text-secondary` work as utilities.
Plain Tailwind colour names still render — anything pasted in from design work will show up — but
using the semantic names is what makes it read as Owl-E.
