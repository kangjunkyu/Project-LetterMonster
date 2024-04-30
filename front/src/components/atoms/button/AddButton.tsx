import styles from "./AddButton.module.scss";
import Add from "../../../assets/commonIcon/addCircle.svg?react";
interface Props {
  onClick: () => void;
}

function AddButton({ onClick }: Props) {
  return (
    <button onClick={onClick} className={styles.addButtonContainer}>
      <Add width={50} height={50} />
    </button>
  );
}

export default AddButton;
