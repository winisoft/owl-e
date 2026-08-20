/**
 * The API client.
 *
 * Every generated endpoint on the server answers the same eight operations over one DTO type, so
 * this is one generic resource rather than sixty-nine hand-written call sites. The three id kinds
 * differ only in how the id reaches the server: numeric and string ids go in `?id=`, composite ids
 * spread their key columns across named query parameters (`?distribution=requests&tag_id=7`).
 */

const BASE = '/api'

export type ApiError = {
  errorCode: string
  message: string
  detail?: string | null
}

export class ApiFailure extends Error {
  constructor(
    readonly status: number,
    readonly error: ApiError | null,
    readonly raw: string,
  ) {
    super(error?.message ?? `Request failed with ${status}`)
    this.name = 'ApiFailure'
  }
}

async function request<T>(path: string, init?: RequestInit): Promise<T> {
  const res = await fetch(`${BASE}${path}`, {
    headers: { 'Content-Type': 'application/json' },
    ...init,
  })

  const body = await res.text()

  if (!res.ok) {
    let parsed: ApiError | null = null
    try {
      parsed = body ? (JSON.parse(body) as ApiError) : null
    } catch {
      // the server answered with something that is not our error shape; keep the raw text
    }
    throw new ApiFailure(res.status, parsed, body)
  }

  return body ? (JSON.parse(body) as T) : (undefined as T)
}

/** A composite id, as the column-name/value pairs the endpoint expects. */
export type CompositeKey = Record<string, string | number>

/**
 * Where the id goes.
 *
 * Scalar ids are a path segment — `IOwleApi` registers `"$basePath/{id}"`. Composite ids cannot
 * be, because a key has several named parts, so `CompositeIdEndpointImpl` reads them from the
 * query string by column name instead.
 */
function idSuffix(id: string | number | CompositeKey): string {
  if (typeof id === 'object') {
    const q = new URLSearchParams(
      Object.entries(id).map(([k, v]) => [k, String(v)]),
    ).toString()
    return `?${q}`
  }
  return `/${encodeURIComponent(String(id))}`
}

/**
 * A resource over one DTO type. `Id` is `number` for ULong keys, `string` for string keys, and
 * [CompositeKey] for composite ones.
 */
export function resource<T, Id extends string | number | CompositeKey = number>(
  path: string,
) {
  return {
    getAll: () => request<T[]>(path),
    getOne: (id: Id) => request<T | null>(`${path}${idSuffix(id)}`),
    create: (dto: T) =>
      request<T>(path, { method: 'POST', body: JSON.stringify(dto) }),
    createAll: (dtos: T[]) =>
      request<T[]>(`${path}/batch`, { method: 'POST', body: JSON.stringify(dtos) }),
    update: (dto: T) =>
      request<T>(path, { method: 'PUT', body: JSON.stringify(dto) }),
    updateAll: (dtos: T[]) =>
      request<T[]>(`${path}/batch`, { method: 'PUT', body: JSON.stringify(dtos) }),
    remove: (id: Id) =>
      request<void>(`${path}${idSuffix(id)}`, { method: 'DELETE' }),
    removeAll: (ids: Id[]) =>
      request<void>(`${path}/batch`, { method: 'DELETE', body: JSON.stringify(ids) }),
  }
}
