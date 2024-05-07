import useNotification, {
  useUncheckedNotification,
} from "../../../hooks/notification/useNotification";
import styles from "./NotificationPage.module.scss";

function NotificationPage() {
  const { data } = useNotification();
  const { data: unchecked } = useUncheckedNotification();
  return (
    <>
      <div>{data && <div>{data}</div>}</div>
      <div>{unchecked && <div>{unchecked}</div>}</div>
    </>
  );
}

export default NotificationPage;
