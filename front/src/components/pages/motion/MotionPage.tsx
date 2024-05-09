import { useLocation, useNavigate } from "react-router-dom";
import styles from "./MotionPage.module.scss";
import MotionList from "../../molecules/motion/MotionList";
import MotionPreview from "../../molecules/motion/MotionPreview";
import { useState } from "react";
import LNB from "../../molecules/common/LNB";
import DefaultButton from "../../atoms/button/DefaultButton";
import { Page_Url } from "../../../router/Page_Url";
import Modal from "../../atoms/modal/Modal";
import { useTranslation } from "react-i18next";
import { useAlert } from "../../../hooks/notice/useAlert";

function MotionPage() {
  const { t } = useTranslation();
  const location = useLocation();
  const navigate = useNavigate();
  const { characterId, nickname } = location.state || {};
  const [gif, setGif] = useState({ imageUrl: "" });
  const [motionId, setMotionId] = useState(0);
  const [load, setLoad] = useState(false);
  const [isModalOpen, setModalOpen] = useState(false);
  const handleToggleModal = () => setModalOpen((prev) => !prev);
  const { showAlert } = useAlert();

  const switchLoad = () => {
    setLoad(!load);
  };

  const onHandleClick = () => {
    if (!load) {
      navigate(Page_Url.MotionResult, {
        state: {
          gif: gif.imageUrl,
          characterId: characterId,
          nickname: nickname,
          motionId: motionId,
        },
      });
    } else {
      showAlert(t("motion.practice"));
    }
  };

  return (
    <>
      <div className={styles.motionRealContainer}>
        <LNB>
          <h1>{t("motion.title")}</h1>
          <DefaultButton
            onClick={() => {
              onHandleClick();
            }}
            custom={true}
          >
            {t("motion.create")}
          </DefaultButton>
        </LNB>
        <div className={styles.motionContainer}>
          <MotionPreview
            gif={gif}
            characterNickname={nickname}
            characterId={characterId}
            motionId={motionId}
            go={onHandleClick}
          />
          <MotionList
            setGif={setGif}
            setMotionId={setMotionId}
            characterId={characterId}
            setLoad={switchLoad}
          />
        </div>
      </div>
      {isModalOpen && (
        <Modal isOpen={isModalOpen} onClose={handleToggleModal}>
          <div>{t("motion.warn")}</div>
        </Modal>
      )}
    </>
  );
}

export default MotionPage;
