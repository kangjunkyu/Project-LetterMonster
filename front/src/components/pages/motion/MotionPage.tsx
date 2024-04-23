import { useLocation } from "react-router-dom";
import styles from "./MotionPage.module.scss";

function MotionPage() {
  const location = useLocation();
  const { image } = location.state || {};

  return (
    <div className={styles.motionContainer}>
      <div>
        {image && <img className={styles.motionImage} src={image} alt="Uploaded" />}
      </div>
      <div>
        <div>Motion 종류</div>
        <div>1번 : dav</div>
        <div>2번 : jesse_dance</div>
        <div>3번 : jumping_jacks</div>
        <div>4번 : jumping</div>
        <div>5번 : wave_hello</div>
        <div>6번 : zombie</div>
      </div>
    </div>
  );
}

export default MotionPage;
