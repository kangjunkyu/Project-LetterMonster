import { useNavigate } from "react-router-dom";
import { Page_Url } from "../../../router/Page_Url";
import styles from "./MainPage.module.scss";
import LanguageSwitcher from "../../molecules/language/LanguageSwitcher";
import lemon from "../../../assets/characterSample/test_dab.gif";

function MainPage() {
  const navigate = useNavigate();

  return (
    <div className={styles.mainContainer}>
      <LanguageSwitcher />
      <h1>Letter Monster</h1>
      <h2>내 캐릭터로 편지보내기!</h2>
      <div className={styles.characterDiv}>
        <img className={styles.character} src={lemon} alt="lettermon" />
      </div>
      <button
        className={styles.startButton}
        onClick={() => navigate(Page_Url.Login)}
      >
        로그인 하기
      </button>
    </div>
  );
}

export default MainPage;
