/** A route that has no design yet. Says so plainly rather than pretending to be a layout. */
export default function Placeholder({
  title,
  note,
}: {
  title: string
  note: string
}) {
  return (
    <div className="flex h-full flex-col items-center justify-center gap-2 p-8 text-center">
      <h1 className="text-xl text-text-bright">{title}</h1>
      <p className="max-w-md text-sm text-text-secondary">{note}</p>
    </div>
  )
}
