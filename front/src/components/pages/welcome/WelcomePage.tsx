import { Link } from "react-router-dom";
import styles from "./WelcomePage.module.scss";
import { Page_Url } from "../../../router/Page_Url";

function WelcomePage() {
  return (
    <div className={styles.WelcomeContainer}>
      <div>{`레터 몬스터 <24/05/02> 1차 임시 배포`}</div>
      <div>
        <img
          className={styles.character}
          src="/src/assets/characterSample/gom.gif"
          alt="레터몬"
        />
      </div>
      <Link to={Page_Url.Main}>체험하기</Link>
    </div>
  );
}

export default WelcomePage;
