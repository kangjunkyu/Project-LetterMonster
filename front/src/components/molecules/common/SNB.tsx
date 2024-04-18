import { Link } from "react-router-dom";
import { useTranslation } from "react-i18next";

import { Page_Url } from "../../../router/Page_Url";
import styles from "./SNB.module.scss";

//아이콘
import red from "../../../assets/GNBIcon/red.png";
import yellow from "../../../assets/GNBIcon/yellow.png";
import green from "../../../assets/GNBIcon/green.png";
import blue from "../../../assets/GNBIcon/blue.png";

function SNB() {
  const { t } = useTranslation();

  return (
    <nav className={styles.SNBNav}>
      <Link className={styles.SNBButton} to={Page_Url.Sketch}>
        <img src={red} alt="빨강 크레파스" />
        {t("nav.drawing")}
      </Link>
      <Link className={styles.SNBButton} to={Page_Url.SketchbookList}>
        <img src={yellow} alt="노랑 크레파스" />
        {t("nav.sketchbook")}
      </Link>
      <Link className={styles.SNBButton} to={Page_Url.WriteLetter}>
        <img src={green} alt="초록 크레파스" />
        {t("nav.letter")}
      </Link>
      <Link className={styles.SNBButton} to={Page_Url.Main}>
        <img src={blue} alt="파랑 크레파스" />
        {t("nav.more")}
      </Link>
    </nav>
  );
}

export default SNB;
