import { useTranslation } from "react-i18next";
import {
  useDeleteCharacter,
  useSelectMainCharacter,
} from "../../../hooks/character/useCharacter";
import { useGetCharacterList } from "../../../hooks/character/useCharacterList";
import { Page_Url } from "../../../router/Page_Url";
import styles from "./MyPageMolecules.module.scss";
import { useNavigate } from "react-router-dom";
import Modal from "../../atoms/modal/Modal";
import DefaultButton from "../../atoms/button/DefaultButton";
import { useState } from "react";

interface Character {
  characterId: number;
  imageUrl: string;
  mainCharacter: boolean;
  nickname: string;
}

function MyPageCharacter() {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const { data: characterList, isLoading, error } = useGetCharacterList();
  const selectMainCharacter = useSelectMainCharacter();
  const deleteCharacter = useDeleteCharacter();
  const [selectedCharacterId, setSelectedCharacterId] = useState<number | null>(
    null
  );

  const handleMainCharacterClick = (characterId: number) => {
    selectMainCharacter.mutate(characterId);
  };

  const goToCreateCharacter = () => {
    navigate(Page_Url.Sketch);
  };

  type ModalName = "deleteAlert";

  const [isModalOpen, setModalOpen] = useState({
    deleteAlert: false,
  });

  const handleToggleModal = (modalName: ModalName, characterId?: number) => {
    setModalOpen((prevState) => ({
      ...prevState,
      [modalName]: !prevState[modalName],
    }));
    if (modalName === "deleteAlert" && characterId !== undefined) {
      setSelectedCharacterId(characterId);
    }
  };

  if (isLoading) return <div>Loading characters...</div>;
  if (error) return <div>Error loading characters!</div>;
  if (characterList?.length === 0) {
    return (
      <div className={styles.userNoCharacterListContainer}>
        <div className={styles.titleNoCharacter}>
          {t("sketchbookList.empty")}
        </div>
        <div>
          <div>{t("")}</div>
          <button
            className={styles.userNoCharacterButton}
            onClick={goToCreateCharacter}
          >
            {t("writeletter.characterDrawing")}
          </button>
        </div>
      </div>
    );
  }

  return (
    <>
      <div className={styles.userCharacterListContainer}>
        <div className={styles.title}>
          <div>{t("main.characterList")}</div>
        </div>
        <div className={styles.userCharacterList}>
          {characterList.map((character: Character) => (
            <div
              key={character.characterId}
              className={styles.userCharacterCard}
            >
              <div
                className={`${styles.userCharacterInfo} ${
                  character.mainCharacter
                    ? styles.justifyBetween
                    : styles.justifyRight
                }`}
              >
                {character.mainCharacter && (
                  <span className={styles.mainCharacterLabel}>대표</span>
                )}
                <span className={styles.nickname}>{character.nickname}</span>
              </div>
              <img
                src={character.imageUrl}
                alt={character.nickname}
                className={styles.userCharacterImage}
              />
              <div className={styles.userCharacterListUpper}>
                <button
                  className={styles.representButton}
                  onClick={() =>
                    handleMainCharacterClick(character.characterId)
                  }
                >
                  {t("mypage.characterSetRep")}
                </button>
                <button
                  className={styles.deleteButton}
                  onClick={() =>
                    handleToggleModal("deleteAlert", character.characterId)
                  }
                >
                  {t("mypage.characterDelete")}
                </button>
              </div>
            </div>
          ))}
        </div>
      </div>
      {isModalOpen.deleteAlert && selectedCharacterId !== null && (
        <Modal
          isOpen={isModalOpen.deleteAlert}
          onClose={() => handleToggleModal("deleteAlert")}
        >
          <div className={styles.buttonBox}>
            {t("characterList.check")}
            <DefaultButton
              onClick={() => deleteCharacter.mutate(selectedCharacterId)}
            >
              {t("characterList.deleteCharacter")}
            </DefaultButton>
            <DefaultButton
              onClick={() => {
                handleToggleModal("deleteAlert");
              }}
            >
              {t("sketchbook.cancel")}
            </DefaultButton>
          </div>
        </Modal>
      )}
    </>
  );
}

export default MyPageCharacter;
