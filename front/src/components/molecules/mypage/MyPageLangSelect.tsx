import { useTranslation } from "react-i18next";
import DefaultButton from "../../atoms/button/DefaultButton";

function MyPageLangSelect() {
  const { i18n } = useTranslation();

  const changeLanguage = (language: string) => {
    i18n.changeLanguage(language);
    localStorage.setItem("lng", language);
  };

  return (
    <div>
      <div>언어 설정</div>
      <DefaultButton onClick={() => changeLanguage("ko")}>한국어</DefaultButton>
      <DefaultButton onClick={() => changeLanguage("jp")}>日本語</DefaultButton>
      <DefaultButton onClick={() => changeLanguage("en")}>
        English
      </DefaultButton>
    </div>
  );
}

export default MyPageLangSelect;
