import styles from "./AddButton.module.scss";
import Write from "../../../assets/GNBIcon/letter.svg?react";

interface Props {
  onClick: () => void;
  id?: string;
}

function WriteButton({ onClick, id }: Props) {
  return (
    <button
      id={id}
      onClick={onClick}
      className={`${styles.addButtonContainer} ${styles.write}`}
    >
      <Write width={50} height={50} />
    </button>
  );
}

export default WriteButton;
