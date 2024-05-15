import { ReactNode, useState } from "react";
import styles from "./SketchbookList.module.scss";
import ExpandLess from "../../../assets/commonIcon/expandLess.svg?react";
import ExpandMore from "../../../assets/commonIcon/expandMore.svg?react";

interface Props {
  children: ReactNode;
  title: string;
}

function SketchbookList({ children, title }: Props) {
  const [open, setOpen] = useState(false);
  return (
    <article className={styles.sketchbookListContainer}>
      <h3>{title}</h3>
      <article
        className={`${styles.sketchbookList} ${
          open ? styles.open : styles.close
        }`}
      >
        {children}
      </article>
      <button
        className={styles.moreButton}
        onClick={() => {
          setOpen(!open);
        }}
      >
        {open ? (
          <h5>
            접기
            <ExpandLess width={20} height={20} />
          </h5>
        ) : (
          <h5>
            펼치기
            <ExpandMore width={20} height={20} />
          </h5>
        )}
      </button>
    </article>
  );
}

export default SketchbookList;
