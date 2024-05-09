import { useLocation, useNavigate } from "react-router-dom";
import styles from "./MotionPreview.module.scss";
import DefaultButton from "../../atoms/button/DefaultButton";
import { Page_Url } from "../../../router/Page_Url";
import { cancelCharacter } from "../../../api/Api";
import Modal from "../../atoms/modal/Modal";
import { useState } from "react";
import { useTranslation } from "react-i18next";

interface MotionPreviewProps {
  characterNickname: string;
  characterId: number;
  gif: { imageUrl: string };
  motionId: number;
  go: () => void;
}

function MotionPreview({
  gif,
  characterNickname,
  characterId,
  motionId,
  go,
}: MotionPreviewProps) {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const location = useLocation();
  const { image } = location.state || {};
  const displayImage = gif.imageUrl || image;
  const [isModalOpen, setModalOpen] = useState(false);
  const handleToggleModal = () => setModalOpen((prev) => !prev);

  return (
    <>
      <div className={styles.motionPreviewContainer}>
        <div className={styles.motionImage}>
          <div className={styles.motionCharacterNickname}>
            {characterNickname}
          </div>
          {displayImage && (
            <img
              className={styles.motionImage}
              src={displayImage}
              alt="Image Preview"
            />
          )}
        </div>
        <div className={styles.motionPreviewButtons}>
          <DefaultButton
            onClick={() => {
              if (motionId === 0) {
                setModalOpen(true);
              } else {
                go();
              }
            }}
          >
            {t("motion.create")}
          </DefaultButton>
          <DefaultButton
            onClick={() => {
              cancelCharacter(characterId);
              navigate(Page_Url.Sketch);
            }}
          >
            {t("motion.repaint")}
          </DefaultButton>
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

export default MotionPreview;
