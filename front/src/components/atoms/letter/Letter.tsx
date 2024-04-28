import CrayonBox20 from "../crayonBox/CrayonBox20";
import styles from "./Letter.module.scss";

interface Props {
  sender: string;
  content: string;
}

function Letter({ sender, content }: Props) {
  return (
    <CrayonBox20>
      <article className={styles.letter_container}>
        <div className={styles.name_box}>보낸 사람 {sender}</div>
        <div className={styles.content_box}>{content}</div>
      </article>
    </CrayonBox20>
  );
}

export default Letter;
