import { useLocation, useNavigate } from "react-router-dom";
import styles from "./SketchPage.module.scss";
import DefaultButton from "../../atoms/button/DefaultButton";
import { Page_Url } from "../../../router/Page_Url";
import LNB from "../../molecules/common/LNB";
import { useTranslation } from "react-i18next";

function SketchResultPage() {
  const { t } = useTranslation();
  const location = useLocation();
  const navigate = useNavigate();
  const { characterId, nickname, image } = location.state || {};

  const handleResultToMotion = () => {
    navigate(Page_Url.Motion, {
      state: { characterId, nickname, image },
    });
  };

  const handleResultToLetter = () => {
    navigate(Page_Url.WriteLetter, {
      state: { characterId: characterId, motionId: 0 },
    });
  };

  return (
    <>
      <div className={styles.sketchResultContainer}>
        <LNB>
          <h1>{t("paintResult.title")}</h1>
          <div></div>
        </LNB>
        <div>
          <img
            className={styles.sketchResultImagePreview}
            src={image}
            alt="imageFromSketch"
          />
        </div>
        <div>
          {nickname} {t("paintResult.birth")} !!
        </div>
        <div className={styles.sketchResultButton}>
          <DefaultButton onClick={handleResultToMotion}>
            {t("paintResult.move")}
          </DefaultButton>
          <DefaultButton onClick={handleResultToLetter}>
            {t("paintResult.letter")}
          </DefaultButton>
        </div>
      </div>
    </>
  );
}

export default SketchResultPage;
