import { useLocation } from "react-router-dom";
import styles from "./MotionPage.module.scss";
import MotionList from "../../molecules/motion/MotionList";

function MotionPage() {
  const location = useLocation();
  const { image } = location.state || {};

  return (
    <div className={styles.motionContainer}>
      <div>
        {image && <img className={styles.motionImage} src={image} alt="Uploaded" />}
      </div>
      <div>
        <MotionList/>
      </div>
    </div>
  );
}

export default MotionPage;
