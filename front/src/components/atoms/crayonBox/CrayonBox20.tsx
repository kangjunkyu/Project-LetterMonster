import { ReactNode } from "react";
import "./CrayonBox20.scss";

interface Props {
  children: ReactNode;
}

function CrayonBox20({ children }: Props) {
  return <section className="BoxContainer">{children}</section>;
}

export default CrayonBox20;
