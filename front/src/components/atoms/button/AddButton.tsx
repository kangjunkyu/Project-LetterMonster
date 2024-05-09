import styles from "./AddButton.module.scss";
import Add from "../../../assets/commonIcon/addCircle.svg?react";
interface Props {
  onClick: () => void;
  id?: string;
}

function AddButton({ onClick, id }: Props) {
  return (
    <button id={id} onClick={onClick} className={styles.addButtonContainer}>
      <Add width={50} height={50} />
    </button>
  );
}

export default AddButton;
