import styles from "./Letter.module.scss";

interface Props {
  sender?: string;
  receiver?: string;
  content: string;
}

function Letter({ receiver, sender, content }: Props) {
  return (
    <article className={styles.letter_container}>
      <div className={styles.receivername_box}>{receiver}</div>
      <div className={styles.name_box}>{sender}</div>
      <div className={styles.content_box}>{content}</div>
    </article>
  );
}

export default Letter;
