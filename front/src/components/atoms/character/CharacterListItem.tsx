import formatMotionName from "../../../hooks/motion/useFormatMotionName";
import styles from "./CharacterListItem.module.scss";

function CharacterListItem({ item, characterId }: any) {
  return (
    <li
      className={`${styles.characterListItem} ${
        item.characterId === characterId ? styles.selected : ""
      } ${item.motionId === characterId ? styles.selected : ""}`}
    >
      <img
        src={item.imageUrl}
        className={`${styles.characterImg}`}
        alt="캐릭터 사진"
      ></img>
      <div>{formatMotionName(item.nickname)}</div>
    </li>
  );
}

export default CharacterListItem;
