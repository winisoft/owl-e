import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import tailwindcss from '@tailwindcss/vite'

// Ktor listens on 9631 (see server/src/main/kotlin/com/wini/owle/Main.kt). Proxying /api here
// means the browser only ever talks to one origin, so there is no CORS configuration to keep in
// step on the server side.
export default defineConfig({
  plugins: [react(), tailwindcss()],
  server: {
    port: 5173,
    proxy: {
      '/api': {
        target: 'http://localhost:9631',
        changeOrigin: true,
      },
    },
  },
})
