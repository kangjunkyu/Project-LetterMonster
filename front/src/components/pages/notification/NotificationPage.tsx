import useNotification, {
  useUncheckedNotification,
} from "../../../hooks/notification/useNotification";
import NotificationList from "../../molecules/notification/NotificationList";
import styles from "./NotificationPage.module.scss";

function NotificationPage() {
  const { data } = useNotification();
  const { data: unchecked } = useUncheckedNotification();
  return (
    <article className={styles.notificationPageContainer}>
      <section>
        <h3>새로운 알림</h3>
        <NotificationList List={data} />
      </section>
      <section>
        <h3>알림 목록</h3>
        <NotificationList List={unchecked} />
      </section>
    </article>
  );
}

export default NotificationPage;
