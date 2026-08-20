import Placeholder from './Placeholder'

export default function Canvas() {
  return (
    <Placeholder
      title="Workflow canvas"
      note="Groupers nest as containers, steps sit inside them, edges follow defaultNextStep. Positions come from posX/posY on the server, so this needs rendering and dragging — not a layout engine."
    />
  )
}
