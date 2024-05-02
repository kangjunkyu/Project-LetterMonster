import { useLocation, useNavigate } from "react-router-dom";
import styles from "./MotionPage.module.scss";
import MotionList from "../../molecules/motion/MotionList";
import MotionPreview from "../../molecules/motion/MotionPreview";
import { useCallback, useState } from "react";
import LNB from "../../molecules/common/LNB";
import DefaultButton from "../../atoms/button/DefaultButton";
import { Page_Url } from "../../../router/Page_Url";
import Modal from "../../atoms/modal/Modal";

function MotionPage() {
  const location = useLocation();
  const navigate = useNavigate();
  const { characterId, nickname } = location.state || {};
  const [gif, setGif] = useState("");
  const [motionId, setMotionId] = useState(0);
  const [isModalOpen, setModalOpen] = useState(false);
  const handleToggleModal = () => setModalOpen((prev) => !prev);

  const onHandleClick = useCallback(() => {
    navigate(Page_Url.MotionResult, { state: { gif: gif } });
  }, [gif, navigate]);

  return (
    <>
      <div className={styles.motionRealContainer}>
        <LNB>
          <h1>모션 선택</h1>
          <DefaultButton
            onClick={() => {
              console.log(motionId);
              if (motionId == 0) {
                setModalOpen(true);
              } else {
                onHandleClick();
              }
            }}
            custom={true}
          >
            모션 결정
          </DefaultButton>
        </LNB>
        <div className={styles.motionContainer}>
          {/* <div> */}
          <MotionPreview
            gif={gif}
            characterNickname={nickname}
            characterId={characterId}
            motionId={motionId}
          />
          {/* </div>
        <div> */}
          <MotionList
            setGif={setGif}
            setMotionId={setMotionId}
            characterId={characterId}
          />
          {/* </div> */}
        </div>
      </div>
      {isModalOpen && (
        <Modal isOpen={isModalOpen} onClose={handleToggleModal}>
          <div>Motion을 선택해주세요!</div>
        </Modal>
      )}
    </>
  );
}

export default MotionPage;
