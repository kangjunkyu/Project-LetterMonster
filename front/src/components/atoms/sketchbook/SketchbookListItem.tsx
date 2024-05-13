import { Link } from "react-router-dom";
import CommonShareIcon from "../share/commonShareIcon";
import styles from "./SketchbookListItem.module.scss";
import KakaoShareIcon from "../share/kakaoShareIcon";
import Lock from "../../../assets/commonIcon/lock.svg?react";
import LockOpen from "../../../assets/commonIcon/lockOpen.svg?react";
import Next from "../../../assets/commonIcon/next.svg?react";

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
    uuid: string;
    tag: number;
    isWritePossible: boolean;
  };
  index: number;
}
function SketchbookListItem({ item, url, index }: Props) {
  return (
    <li className={styles.itemContainer}>
      {/* <KakaoShareIcon
        link={item.shareLink}
        nickname={item.holder.nickname}
        index={index}
      />
      <CommonShareIcon link={item.shareLink} /> */}
      <Link to={url} className={styles.sketchbookListItem}>
        <div
          className={`${styles.badge} ${
            item.isPublic ? styles.open : styles.close
          }`}
        >
          {item.isPublic ? (
            <LockOpen width={20} height={20} />
          ) : (
            <Lock width={20} height={20} />
          )}
        </div>
        <h5>{item.name}</h5>
        <Next width={10} height={10} />
      </Link>
    </li>
  );
}

export default SketchbookListItem;
