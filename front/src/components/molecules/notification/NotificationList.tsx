import styles from "./NotificationList.module.scss";
import NotificationListItem from "../../atoms/notification/NotificationListItem";

interface Props {
  List: {
    id: number;
    type: number;
    sketchbookName: string;
    friendName: string;
    isCheck: boolean;
    receiver_id: number;
  }[];
}

function NotificationList({ List }: Props) {
  return (
    <ul className={styles.notificationList}>
      {List?.map((item) => (
        <NotificationListItem key={item.id} item={item} />
      ))}
    </ul>
  );
}

export default NotificationList;
