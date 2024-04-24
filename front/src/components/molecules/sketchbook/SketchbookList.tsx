import { ReactNode } from "react";
import styles from "./SketchbookList.module.scss";

interface Props {
  children: ReactNode;
}

function SketchbookList({ children }: Props) {
  return <div className={styles.sketchbookList}>{children}</div>;
}

export default SketchbookList;
