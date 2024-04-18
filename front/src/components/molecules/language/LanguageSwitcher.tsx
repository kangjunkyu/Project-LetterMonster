import { useTranslation } from "react-i18next";
import DefalutButton from "../../atoms/button/DefalutButton";
import styles from "./LanguageSwitcher.module.scss";

function LanguageSwitcher() {
  const { i18n } = useTranslation();

  const changeLanguage = (language: string) => {
    i18n.changeLanguage(language);
    localStorage.setItem("lng", language);
  };

  return (
    <div className={styles.languageContainer}>
      <DefalutButton onClick={() => changeLanguage("ko")}>한국어</DefalutButton>
      <DefalutButton onClick={() => changeLanguage("jp")}>日本語</DefalutButton>
      <DefalutButton onClick={() => changeLanguage("en")}>
        English
      </DefalutButton>
    </div>
  );
}

export default LanguageSwitcher;
