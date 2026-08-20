import { useEffect, useState } from 'react'

/**
 * A live specimen of the theme, and a check that the server is reachable.
 *
 * Two jobs. It shows every semantic token rendered rather than described, which is what design
 * work needs to refer to. And the strip at the top proves the whole loop — Vite proxying `/api`
 * to Ktor — so "does the backend work" is never a question mixed into a UI problem.
 */

const SURFACES = [
  ['surface-sunken', 'behind everything'],
  ['surface-base', 'the editor ground'],
  ['surface-panel', 'rails, sidebars'],
  ['surface-raised', 'cards, popovers'],
  ['surface-hover', 'hover state'],
  ['surface-selected', 'selection'],
] as const

const TEXT = [
  ['text-bright', 'headings, active'],
  ['text-primary', 'body'],
  ['text-secondary', 'captions, meta'],
  ['text-disabled', 'unavailable'],
  ['text-link', 'links'],
] as const

const NODES = [
  ['node-step', 'Step'],
  ['node-grouper', 'Grouper'],
  ['node-inquisition', 'Inquisition'],
  ['node-junction', 'Junction'],
  ['node-datapoint', 'Data point'],
  ['node-tool', 'Tool'],
] as const

const STATUS = [
  ['error', 'failed'],
  ['warning', 'attention'],
  ['success', 'completed'],
  ['info', 'neutral'],
  ['running', 'in flight'],
] as const

const TYPE_SCALE = [
  ['--text-2xl', 'The quick brown fox'],
  ['--text-xl', 'The quick brown fox'],
  ['--text-lg', 'The quick brown fox'],
  ['--text-base', 'The quick brown fox'],
  ['--text-sm', 'The quick brown fox — body default'],
  ['--text-xs', 'The quick brown fox — dense labels'],
] as const

type Reachability = 'checking' | 'up' | 'down'

function ServerStrip() {
  const [state, setState] = useState<Reachability>('checking')
  const [detail, setDetail] = useState('')

  useEffect(() => {
    let cancelled = false
    fetch('/api/workflow')
      .then((res) => {
        if (cancelled) return
        // any HTTP answer means Ktor is listening and the proxy is wired; the status only tells
        // us whether that particular route is happy
        setState('up')
        setDetail(`HTTP ${res.status} from /api/workflow`)
      })
      .catch((err: unknown) => {
        if (cancelled) return
        setState('down')
        setDetail(err instanceof Error ? err.message : String(err))
      })
    return () => {
      cancelled = true
    }
  }, [])

  const colour =
    state === 'up' ? 'bg-success' : state === 'down' ? 'bg-error' : 'bg-warning'
  const label =
    state === 'up'
      ? 'server reachable'
      : state === 'down'
        ? 'server unreachable — is ./gradlew :server:run going?'
        : 'checking…'

  return (
    <div className="mb-8 flex items-center gap-3 rounded-md border border-border-subtle bg-surface-panel px-4 py-3">
      <span className={`inline-block h-2.5 w-2.5 rounded-full ${colour}`} />
      <span className="text-sm text-text-primary">{label}</span>
      {detail && (
        <span className="ml-auto font-mono text-xs text-text-secondary">{detail}</span>
      )}
    </div>
  )
}

function Section({
  title,
  children,
}: {
  title: string
  children: React.ReactNode
}) {
  return (
    <section className="mb-10">
      <h2 className="mb-3 text-xs uppercase tracking-wider text-text-secondary">
        {title}
      </h2>
      {children}
    </section>
  )
}

function Swatch({ token, note }: { token: string; note: string }) {
  return (
    <div className="flex items-center gap-3">
      <div
        className={`h-10 w-16 shrink-0 rounded-sm border border-border-strong bg-${token}`}
      />
      <div className="min-w-0">
        <div className="font-mono text-xs text-text-primary">{token}</div>
        <div className="text-xs text-text-secondary">{note}</div>
      </div>
    </div>
  )
}

export default function Tokens() {
  return (
    <div className="p-8">
      <h1 className="mb-1 text-2xl text-text-bright">Darcula tokens</h1>
      <p className="mb-8 text-sm text-text-secondary">
        Every semantic token, rendered. Components reference these names, never the raw palette
        beneath them.
      </p>

      <ServerStrip />

      <Section title="Surfaces">
        <div className="grid grid-cols-2 gap-4 md:grid-cols-3">
          {SURFACES.map(([token, note]) => (
            <Swatch key={token} token={token} note={note} />
          ))}
        </div>
      </Section>

      <Section title="Text">
        <div className="rounded-md border border-border-subtle bg-surface-panel p-4">
          {TEXT.map(([token, note]) => (
            <div key={token} className="flex items-baseline gap-4 py-1">
              <span className={`w-40 shrink-0 font-mono text-xs text-${token}`}>
                {token}
              </span>
              <span className={`text-sm text-${token}`}>
                Steps run until something redirects them.
              </span>
              <span className="ml-auto text-xs text-text-disabled">{note}</span>
            </div>
          ))}
        </div>
      </Section>

      <Section title="Workflow elements">
        <div className="flex flex-wrap gap-3">
          {NODES.map(([token, label]) => (
            <div
              key={token}
              className="rounded-md border-l-4 bg-surface-raised px-4 py-2"
              style={{ borderLeftColor: `var(--${token})` }}
            >
              <div className="text-sm text-text-primary">{label}</div>
              <div className="font-mono text-xs text-text-secondary">--{token}</div>
            </div>
          ))}
        </div>
      </Section>

      <Section title="Status">
        <div className="flex flex-wrap gap-3">
          {STATUS.map(([token, label]) => (
            <span
              key={token}
              className={`rounded-sm px-2 py-1 font-mono text-xs text-${token}`}
              style={{ background: 'var(--surface-raised)' }}
            >
              {label} · --status-{token}
            </span>
          ))}
        </div>
      </Section>

      <Section title="Type scale">
        <div className="rounded-md border border-border-subtle bg-surface-panel p-4">
          {TYPE_SCALE.map(([token, sample]) => (
            <div key={token} className="flex items-baseline gap-4 py-1.5">
              <span className="w-28 shrink-0 font-mono text-xs text-text-secondary">
                {token}
              </span>
              <span style={{ fontSize: `var(${token})` }}>{sample}</span>
            </div>
          ))}
          <div className="mt-4 border-t border-border-subtle pt-3">
            <div className="font-mono text-sm">
              JetBrains Mono — <span className="text-accent">def</span>{' '}
              <span className="text-node-tool">run_step</span>(
              <span className="text-node-datapoint">payload</span>): ...
            </div>
          </div>
        </div>
      </Section>
    </div>
  )
}
