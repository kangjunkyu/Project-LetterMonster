import { useTranslation } from "react-i18next";
import convertDateString from "../../../util/date/convertDateString";
import styles from "./Letter.module.scss";

interface Props {
  sender?: string;
  content: string;
  onClick?: () => void;
  onDelete: () => void;
  onReply: () => void;
  item: any;
  isWritePossible: boolean;
}

function Letter({
  sender,
  content,
  item,
  onClick,
  onDelete,
  onReply,
  isWritePossible,
}: Props) {
  const { t } = useTranslation();
  return (
    <article className={styles.letter_container} onClick={onClick}>
      <div className={styles.content_box}>{content}</div>
      <div className={styles.writeTime}>
        {convertDateString(item.write_time)}
      </div>
      <div className={styles.name_box}>From.{sender ? sender : "익명"}</div>
      {isWritePossible && (
        <div className={styles.deleteButton}>
          <button onClick={onDelete}>{t("mypage.characterDelete")}</button>
          {sender && <button onClick={onReply}>{t("sketchbook.reply")}</button>}
        </div>
      )}
    </article>
  );
}

export default Letter;
