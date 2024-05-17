import { useTranslation } from "react-i18next";
import styles from "./SketchGuidePage.module.scss";

const GuidePage = ({ onClose }: any) => {
  const { t } = useTranslation();

  return (
    <div className={styles.overlay} onClick={onClose}>
      <div className={styles.guideContainer}>
        <h1>{t("guide.1")}</h1>
        <div className={styles.guideContent}>
          <h2>{t("guide.2")}</h2>
          <p>{t("guide.3")}</p>
          <h2>{t("guide.4")}</h2>
          <p>{t("guide.5")}</p>
          <h2>{t("guide.6")}</h2>
          <p>{t("guide.7")}</p>
          <p>{t("guide.8")}</p>
          <br />
          <div className={styles.guide2}>
            <h3>{t("guide.9")}</h3>
            <h3>
              {t("guide.10")}{" "}
              <span className={styles.highlight}>{t("guide.11")}</span>{" "}
              {t("guide.12")}
            </h3>
          </div>
          <br />
          <br />
          <div className={styles.guide1}>
            <h3>
              <span>Tip!!</span> {t("guide.13")} &nbsp;
              <span>{t("guide.14")}</span> {t("guide.15")}
            </h3>
            <h3>{t("guide.16")}</h3>
          </div>
        </div>
      </div>
    </div>
  );
};

export default GuidePage;
