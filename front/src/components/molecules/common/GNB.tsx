import { Link } from "react-router-dom";
import styles from "./GNB.module.scss";
import { Page_Url } from "../../../router/Page_Url";

// 아이콘
import sketchbook from "../../../assets/GNBIcon/sketchbook.svg";
import draw from "../../../assets/GNBIcon/draw.svg";
import more from "../../../assets/GNBIcon/more.svg";

function GNB() {
  return (
    <header className={styles.header}>
      <Link className={styles.GNBbutton} to={Page_Url.Main}>
        <img src={draw} alt="그리기" />
        캐릭터그리기
      </Link>
      <Link className={styles.GNBbutton} to={Page_Url.Main}>
        <img src={sketchbook} alt="스케치북" />
        스케치북
      </Link>
      <Link className={styles.GNBbutton} to={Page_Url.Main}>
        <img src={sketchbook} alt="편지 쓰기" />
        편지쓰기
      </Link>
      <Link className={styles.GNBbutton} to={Page_Url.Main}>
        <img src={more} alt="메인" />
        더보기
      </Link>
    </header>
  );
}

export default GNB;
