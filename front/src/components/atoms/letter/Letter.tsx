import styles from "./Letter.module.scss";

interface Props {
  sender?: string;
  content: string;
}

function Letter({ sender, content }: Props) {
  return (
    <article className={styles.letter_container}>
      <div className={styles.content_box}>{content}</div>
      <div className={styles.name_box}>From.{sender ? sender : "익명"}</div>
    </article>
  );
}

export default Letter;
