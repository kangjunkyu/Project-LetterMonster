import { Link } from "react-router-dom";
import CommonShareIcon from "../share/commonShareIcon";
import styles from "./SketchbookListItem.module.scss";
import KakaoShareIcon from "../share/kakaoShareIcon";

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
      <KakaoShareIcon
        link={item.shareLink}
        nickname={item.holder.nickname}
        index={index}
      />
      <CommonShareIcon link={item.shareLink} />
      <Link to={url} className={styles.sketchbookListItem}>
        {item.name}
      </Link>
    </li>
  );
}

export default SketchbookListItem;
