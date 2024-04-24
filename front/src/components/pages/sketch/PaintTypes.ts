export type Shape = {
  id: string;
  color: string;
  x?: number;
  y?: number;
};

export type Scribble = Shape & {
  points: number[];
};

export type Erase = {
  points: number[];
};
