import { useNavigate } from "react-router-dom";
import { Page_Url } from "../../../router/Page_Url";
import styles from "./MainPage.module.scss";
import LanguageSwitcher from "../../molecules/language/LanguageSwitcher";
import gom from "../../../assets/characterSample/gom.gif";
import egypt from "../../../assets/characterSample/egypt.gif";
import hojin from "../../../assets/characterSample/hojin_character.gif";
import rabbit from "../../../assets/characterSample/rabbit.gif";
import shinzzang from "../../../assets/characterSample/shinzzang.gif";
import television from "../../../assets/characterSample/television.gif";
import juhyeon from "../../../assets/characterSample/juhyeon.gif";
import attamoma from "../../../assets/characterSample/attamoma.gif";

function MainPage() {
  const navigate = useNavigate();

  return (
    <div className={styles.container}>
      <LanguageSwitcher />
      <div>Letter Monster / 레터몬스터</div>
      <div className={styles.characterDiv}>
        <img
          className={styles.character}
          src={gom}
          alt="gom"
        />
        <img
          className={styles.character}
          src={hojin}
          alt="hojin"
        />
        <img
          className={styles.character}
          src={egypt}
          alt="egypt"
        />
        <img
          className={styles.character}
          src={rabbit}
          alt="rabbit"
        />
        <img
          className={styles.character}
          src={shinzzang}
          alt="shinzzang"
        />
        <img
          className={styles.character}
          src={television}
          alt="television"
        />
        <img
          className={styles.character}
          src={attamoma}
          alt="attamoma"
        />
        <img
          className={styles.character}
          src={juhyeon}
          alt="juhyeon"
        />
      </div>
      <button onClick={() => navigate(Page_Url.Login)}>카카오로그인</button>
      <button onClick={() => navigate(Page_Url.Sketch)}>캐릭터그리기</button>
    </div>
  );
}

export default MainPage;
