import { useNavigate } from "react-router-dom";
import { Page_Url } from "../../../router/Page_Url";
import styles from "./MainPage.module.scss";

function MainPage() {
  const navigate = useNavigate();

  return (
    <div className={styles.container}>
      <div>Letter Monster / 레터몬스터</div>
      <div className={styles.characterDiv}>
        <img
          className={styles.character}
          src="/src/assets/characterSample/gom.gif"
          alt=""
        />
        <img
          className={styles.character}
          src="/src/assets/characterSample/hojin_character.gif"
          alt=""
        />
        <img
          className={styles.character}
          src="/src/assets/characterSample/egypt.gif"
          alt=""
        />
        <img
          className={styles.character}
          src="/src/assets/characterSample/rabbit.gif"
          alt=""
        />
        <img
          className={styles.character}
          src="/src/assets/characterSample/shinzzang.gif"
          alt=""
        />
        <img
          className={styles.character}
          src="/src/assets/characterSample/television.gif"
          alt=""
        />
        <img
          className={styles.character}
          src="/src/assets/characterSample/attamoma.gif"
          alt=""
        />
        <img
          className={styles.character}
          src="/src/assets/characterSample/juhyeon.gif"
          alt=""
        />
      </div>
      <button onClick={() => navigate(Page_Url.Login)}>카카오로그인</button>
    </div>
  );
}

export default MainPage;
