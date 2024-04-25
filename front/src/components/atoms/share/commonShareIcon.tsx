import icon from "../../../assets/commonIcon/shareLink.svg";
import useCopyClipboard from "../../../hooks/common/useCopyClipboard";
import styles from "./shareIcon.module.scss";

interface Props {
  link: string;
}

function CommonShareIcon({ link }: Props) {
  const copy = useCopyClipboard();
  return (
    <button
      className={styles.commonShareIconContainer}
      onClick={(e) => {
        e.stopPropagation();
        copy(link);
      }}
    >
      <img src={icon} alt="공유하기 버튼" />
    </button>
  );
}

export default CommonShareIcon;
