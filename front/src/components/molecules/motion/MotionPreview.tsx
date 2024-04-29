import { useLocation, useNavigate } from "react-router-dom";
import styles from "./MotionPreview.module.scss";
import DefaultButton from "../../atoms/button/DefaultButton";
import { Page_Url } from "../../../router/Page_Url";
import { cancelCharacter } from "../../../api/Api";
import Modal from "../../atoms/modal/Modal";
import { useState } from "react";

interface MotionPreviewProps {
  characterNickname: string;
  characterId: number;
  gif: string;
  motionId: number;
}

function MotionPreview({
  gif,
  characterNickname,
  characterId,
  motionId,
}: MotionPreviewProps) {
  const navigate = useNavigate();
  const location = useLocation();
  const { image } = location.state || {};
  const displayImage = gif || image;
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
                navigate(Page_Url.WriteLetter, {
                  state: {
                    gif: gif,
                    characterId: characterId,
                    characterNickname: characterNickname,
                    motionId: motionId,
                  },
                });
              }
            }}
          >
            편지쓰러가기
          </DefaultButton>
          <DefaultButton
            onClick={() => {
              cancelCharacter(characterId);
              navigate(Page_Url.Sketch);
            }}
          >
            다시 그리기
          </DefaultButton>
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

export default MotionPreview;
