export enum DrawAction {
  Select = "select",
  Scribble = "freedraw",
}

export const PAINT_OPTIONS = [
  {
    id: DrawAction.Select,
    label: "Select Shapes",
    icon: <span className="material-icons">arrow_circle_left</span>,
  },
  {
    id: DrawAction.Scribble,
    label: "Scribble",
    icon: <span className="material-icons">edit</span>,
  },
];
