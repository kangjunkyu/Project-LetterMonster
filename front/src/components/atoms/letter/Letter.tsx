import convertDateString from "../../../util/date/convertDateString";
import styles from "./Letter.module.scss";

interface Props {
  sender?: string;
  content: string;
  onClick?: () => void;
  item: any;
}

function Letter({ sender, content, item, onClick }: Props) {
  return (
    <article className={styles.letter_container} onClick={onClick}>
      <div className={styles.content_box}>{content}</div>
      <div className={styles.writeTime}>
        {convertDateString(item.write_time)}
      </div>
      <div className={styles.name_box}>From.{sender ? sender : "익명"}</div>
    </article>
  );
}

export default Letter;
