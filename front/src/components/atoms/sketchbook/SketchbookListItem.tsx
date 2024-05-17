import { Link } from "react-router-dom";
import styles from "./SketchbookListItem.module.scss";
import Lock from "../../../assets/commonIcon/lock.svg?react";
import LockOpen from "../../../assets/commonIcon/lockOpen.svg?react";
import Next from "../../../assets/commonIcon/next.svg?react";
import Thumb from "../../../assets/commonIcon/thumb.svg?react";

interface Props {
  url: string;
  item: {
    id: string;
    isPublic: boolean;
    shareLink: string;
    name: string;
    holder: {
      nickname: string;
      nicknameTag: number;
    };
    userNickName?: string;
    uuid: string;
    tag: number;
    isWritePossible: boolean;
  };
  index: number;
  publicMode?: boolean;
}
function SketchbookListItem({ item, url, publicMode }: Props) {
  return (
    <li className={styles.itemContainer}>
      <Link to={url} className={styles.sketchbookListItem}>
        <div
          className={`${styles.badge} ${
            publicMode ? "" : item.isPublic ? styles.open : styles.close
          }`}
        >
          {!publicMode &&
            (item.isPublic ? (
              <LockOpen width={20} height={20} />
            ) : (
              <Lock width={20} height={20} />
            ))}
          {publicMode && <Thumb width={20} height={20} />}
        </div>
        <h5>
          {item?.name} -{" "}
          {item?.holder ? item?.holder?.nickname : item?.userNickName}
        </h5>
        <Next width={10} height={10} />
      </Link>
    </li>
  );
}

export default SketchbookListItem;
