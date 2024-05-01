import { Link } from "react-router-dom";
import styles from "./WelcomePage.module.scss";
import { Page_Url } from "../../../router/Page_Url";

function WelcomePage() {
  return (
    <div className={styles.WelcomeContainer}>
      <div>{`레터 몬스터는 <24/04/28>일 1차 배포 목표로 준비중입니다.`}</div>
      <div>{`レターモンスターは <24/04/28> 臨時配布を目指して頑張ってます。ლ(•̀ _ •́ ლ)`}</div>
      <Link to={Page_Url.Main}>임시</Link>
    </div>
  );
}

export default WelcomePage;
