import { ReactNode, useState } from "react";
import styles from "./SketchbookList.module.scss";
import ExpandLess from "../../../assets/commonIcon/expandLess.svg?react";
import ExpandMore from "../../../assets/commonIcon/expandMore.svg?react";
import { useTranslation } from "react-i18next";

interface Props {
  children: ReactNode;
  title: string;
}

function SketchbookList({ children, title }: Props) {
  const { t } = useTranslation();
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
            {t("button.close")}
            <ExpandLess width={20} height={20} />
          </h5>
        ) : (
          <h5>
            {t("button.open")}
            <ExpandMore width={20} height={20} />
          </h5>
        )}
      </button>
    </article>
  );
}

export default SketchbookList;
