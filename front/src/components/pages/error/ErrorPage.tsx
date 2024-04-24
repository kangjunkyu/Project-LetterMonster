import { useNavigate } from "react-router-dom";
import { useTranslation } from "react-i18next";
import styles from "./ErrorPage.module.scss";

import DefaultButton from "../../atoms/button/DefaultButton";
import { Page_Url } from "../../../router/Page_Url";

function ErrorPage() {
  const navigate = useNavigate();
  const { t } = useTranslation();
  return (
    <div className={styles.errorContainer}>
      <img src="/image.png" alt="에러 페이지" />
      {t("error")}
      <div className={styles.buttonBox}>
        <DefaultButton onClick={() => navigate(Page_Url.Main)}>
          {t("button.goToMain")}
        </DefaultButton>
        <DefaultButton onClick={() => navigate(-1)}>
          {t("button.back")}
        </DefaultButton>
      </div>
    </div>
  );
}

export default ErrorPage;
