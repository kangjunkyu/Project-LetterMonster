import { Link } from "react-router-dom";

import { Page_Url } from "../../../router/Page_Url";
import styles from "./SNB.module.scss";

//아이콘
import red from "../../../assets/GNBIcon/red.png";
import yellow from "../../../assets/GNBIcon/yellow.png";
import green from "../../../assets/GNBIcon/green.png";
import blue from "../../../assets/GNBIcon/blue.png";

function SNB() {
  return (
    <nav className={styles.SNBNav}>
      <Link className={styles.SNBButton} to={Page_Url.WriteLetter}>
        <img src={red} alt="빨강 크레파스" />
        그리기
      </Link>
      <Link className={styles.SNBButton} to={Page_Url.Main}>
        <img src={yellow} alt="노랑 크레파스" />
        스케치북
      </Link>
      <Link className={styles.SNBButton} to={Page_Url.Main}>
        <img src={green} alt="초록 크레파스" />
        편지 쓰기
      </Link>
      <Link className={styles.SNBButton} to={Page_Url.Main}>
        <img src={blue} alt="파랑 크레파스" />
        더보기
      </Link>
    </nav>
  );
}

export default SNB;
