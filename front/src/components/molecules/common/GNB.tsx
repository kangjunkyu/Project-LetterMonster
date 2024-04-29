import { Link } from "react-router-dom";
import styles from "./GNB.module.scss";
import { useTranslation } from "react-i18next";

import { Page_Url } from "../../../router/Page_Url";

// 아이콘
import sketchbook from "../../../assets/GNBIcon/sketchbook.svg";
import draw from "../../../assets/GNBIcon/draw.svg";
import letter from "../../../assets/GNBIcon/letter.svg";
import more from "../../../assets/GNBIcon/more.svg";

function GNB() {
  const { t } = useTranslation();
  return (
    <header className={styles.header}>
      <Link className={styles.GNBbutton} to={Page_Url.Sketch}>
        <img src={draw} alt="그리기" />
        {t("nav.drawing")}
      </Link>
      <Link className={styles.GNBbutton} to={Page_Url.SketchbookList}>
        <img src={sketchbook} alt="스케치북" />
        {t("nav.sketchbook")}
      </Link>
      <Link className={styles.GNBbutton} to={Page_Url.WriteLetter}>
        <img src={letter} alt="편지 쓰기" />
        {t("nav.letter")}
      </Link>
      <Link className={styles.GNBbutton} to={Page_Url.MyPage}>
        <img src={more} alt="메인" />
        {t("nav.more")}
      </Link>
    </header>
  );
}

export default GNB;
