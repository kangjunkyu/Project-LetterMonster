import { useNavigate } from "react-router-dom";
import { useTranslation } from "react-i18next";
import styles from "./ErrorPage.module.scss";

import DefalutButton from "../../atoms/button/DefalutButton";
import { Page_Url } from "../../../router/Page_Url";

function ErrorPage() {
  const navigate = useNavigate();
  const { t } = useTranslation();
  return (
    <div className={styles.errorContainer}>
      <img src="/image.png" alt="에러 페이지" />
      {t("error")}
      <div className={styles.buttonBox}>
        <DefalutButton onClick={() => navigate(Page_Url.Main)}>
          {t("button.goToMain")}
        </DefalutButton>
        <DefalutButton onClick={() => navigate(-1)}>
          {t("button.back")}
        </DefalutButton>
      </div>
    </div>
  );
}

export default ErrorPage;
