import { NavLink, Navigate, Route, Routes } from 'react-router-dom'
import Canvas from './routes/Canvas'
import Library from './routes/Library'
import Runs from './routes/Runs'
import Tokens from './routes/Tokens'

/**
 * The shell.
 *
 * Deliberately thin: a rail, an outlet, and nothing opinionated inside the routes. Screens land
 * here from design work, and the less scaffolding they have to fit into the better.
 */
const NAV = [
  { to: '/canvas', label: 'Canvas' },
  { to: '/library', label: 'Library' },
  { to: '/runs', label: 'Runs' },
  { to: '/tokens', label: 'Tokens' },
]

export default function App() {
  return (
    <div className="flex h-full">
      <nav className="flex w-44 shrink-0 flex-col gap-1 border-r border-border-subtle bg-surface-panel p-3">
        <div className="mb-4 px-2 font-mono text-lg text-accent">Owl-E</div>
        {NAV.map(({ to, label }) => (
          <NavLink
            key={to}
            to={to}
            className={({ isActive }) =>
              [
                'rounded-sm px-2 py-1.5 text-sm transition-colors',
                isActive
                  ? 'bg-surface-selected text-text-bright'
                  : 'text-text-primary hover:bg-surface-hover',
              ].join(' ')
            }
          >
            {label}
          </NavLink>
        ))}
      </nav>

      <main className="min-w-0 flex-1 overflow-auto">
        <Routes>
          <Route path="/" element={<Navigate to="/canvas" replace />} />
          <Route path="/canvas" element={<Canvas />} />
          <Route path="/library" element={<Library />} />
          <Route path="/runs" element={<Runs />} />
          <Route path="/tokens" element={<Tokens />} />
        </Routes>
      </main>
    </div>
  )
}
