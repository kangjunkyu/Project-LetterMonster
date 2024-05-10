import { useTranslation } from "react-i18next";
import DefaultButton from "../../atoms/button/DefaultButton";
import styles from "./LanguageSwitcher.module.scss";

function LanguageSwitcher() {
  const { i18n } = useTranslation();

  const changeLanguage = (language: string) => {
    i18n.changeLanguage(language);
    localStorage.setItem("lng", language);
    if (localStorage.getItem("accessToken")) {
      console.log("qkRNa");
    }
  };

  return (
    <div className={styles.languageContainer}>
      <DefaultButton onClick={() => changeLanguage("ko")}>한국어</DefaultButton>
      <DefaultButton onClick={() => changeLanguage("jp")}>日本語</DefaultButton>
      <DefaultButton onClick={() => changeLanguage("en")}>
        English
      </DefaultButton>
    </div>
  );
}

export default LanguageSwitcher;
