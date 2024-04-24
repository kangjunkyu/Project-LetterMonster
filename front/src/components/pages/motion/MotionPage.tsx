// import { useLocation } from "react-router-dom";
import styles from "./MotionPage.module.scss";
import MotionList from "../../molecules/motion/MotionList";
import MotionPreview from "../../molecules/motion/MotionPreview";

function MotionPage() {
  // const location = useLocation();
  // const { image } = location.state || {};

  return (
    <div className={styles.motionContainer}>
      <div>
        <MotionPreview/>
      </div>
      <div>
        <MotionList/>
      </div>
    </div>
  );
}

export default MotionPage;
