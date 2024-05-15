import { useTranslation } from "react-i18next";
import styles from "./NotificationListItem.module.scss";

interface Props {
  item: {
    id: number;
    type: number;
    sketchbookName: string;
    friendName: string;
    isCheck: boolean;
    receiver_id: number;
  };
}

function NotificationListItem({ item }: Props) {
  const { t } = useTranslation();
  return (
    <li className={styles.notificationItem}>
      {item?.type === 1 && (
        <h4>{`${item?.sketchbookName}${t("noti.sketchbook")}`}</h4>
      )}
      {item?.type === 2 && <h4>{`${item?.friendName}${t("noti.friend")}`}</h4>}
    </li>
  );
}

export default NotificationListItem;
