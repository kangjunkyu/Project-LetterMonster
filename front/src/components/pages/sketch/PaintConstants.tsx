export enum DrawAction {
  Select = "select",
  Scribble = "freedraw",
}

export const PAINT_OPTIONS = [
  {
    id: DrawAction.Select,
    label: "손",
    icon: <span className="material-icons">arrow_circle_left</span>,
  },
  {
    id: DrawAction.Scribble,
    label: "펜",
    icon: <span className="material-icons">edit</span>,
  },
];
