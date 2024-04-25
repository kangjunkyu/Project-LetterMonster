export enum DrawAction {
  Select = "select",
  Scribble = "freedraw",
  Erase = "destination-out",
}

export const PAINT_OPTIONS = [
  {
    id: DrawAction.Scribble,
    label: "pen",
    icon: <span className="material-icons">edit</span>,
  },
  {
    id: DrawAction.Erase,
    label: "erase",
    icon: <span className="material-symbols-outlined">ink_eraser</span>,
  },
  {
    id: DrawAction.Select,
    label: "hand",
    icon: <span className="material-icons">back_hand</span>,
  },
];
