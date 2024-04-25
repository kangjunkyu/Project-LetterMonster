import { useLocation } from "react-router-dom";
import styles from "./MotionPage.module.scss";
import MotionList from "../../molecules/motion/MotionList";
import MotionPreview from "../../molecules/motion/MotionPreview";
import { useState } from "react";

function MotionPage() {
  const location = useLocation();
  const { characterId, nickname } = location.state || {};
  const [gif, setGif] = useState("");
  const [motionId, setMotionId] = useState(0);

  return (
    <div className={styles.motionContainer}>
      <div>
        <MotionPreview
          gif={gif}
          characterNickname={nickname}
          characterId={characterId}
          motionId={motionId}
        />
      </div>
      <div>
        <MotionList
          setGif={setGif}
          setMotionId={setMotionId}
          characterId={characterId}
        />
      </div>
    </div>
  );
}

export default MotionPage;
