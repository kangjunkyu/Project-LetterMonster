import styles from "./AlertNotice.module.scss";
import useTrigger from "../../../hooks/notice/useTrigger";

interface Props {
  content: string;
}

function AlertNotice({ content }: Props) {
  const { trigger } = useTrigger();

  return (
    <div
      className={`${styles.noticeTransition} ${
        trigger ? styles.alertContainer : styles.disappear
      }`}
    >
      {content}
    </div>
  );
}

export default AlertNotice;
