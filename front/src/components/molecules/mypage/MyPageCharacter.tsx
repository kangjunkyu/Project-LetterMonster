// import { useState } from "react";
import { useTranslation } from "react-i18next";
import {
  useDeleteCharacter,
  useSelectMainCharacter,
} from "../../../hooks/character/useCharacter";
import { useGetCharacterList } from "../../../hooks/character/useCharacterList";
import { Page_Url } from "../../../router/Page_Url";
import styles from "./MyPageMolecules.module.scss";
import { useNavigate } from "react-router-dom";

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
  // const [mainCharacterId, setMainCharacterId] = useState<number>();

  // const handleCardClick = (characterId: number) => {
  //   selectMainCharacter.mutate(characterId);
  // };

  const handleMainCharacterClick = (characterId: number) => {
    // setMainCharacterId(characterId);
    selectMainCharacter.mutate(characterId);
  };

  const goToCreateCharacter = () => {
    navigate(Page_Url.Sketch);
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
    <div className={styles.userCharacterListContainer}>
      <div className={styles.title}>
        <div>{t("main.characterList")}</div>
      </div>
      <div className={styles.userCharacterList}>
        {characterList.map((character: Character) => (
          <div
            key={character.characterId}
            className={styles.userCharacterCard}
            // onClick={() => {
            //   handleCardClick(character.characterId);
            // }}
          >
            <div className={styles.userCharacterListUpper}>
              <button
                onClick={() => handleMainCharacterClick(character.characterId)}
              >
                {t("mypage.characterSetRep")}
              </button>
              <button
                onClick={() => deleteCharacter.mutate(character.characterId)}
              >
                {t("mypage.characterDelete")}
              </button>
            </div>
            <img
              src={character.imageUrl}
              alt={character.nickname}
              className={styles.userCharacterImage}
            />
            <div
              className={`${styles.userCharacterInfo} ${
                character.mainCharacter
                  ? styles.justifyBetween
                  : styles.justifyRight
              }`}
            >
              {character.mainCharacter && (
                <span className={styles.mainCharacterLabel}>
                  {t("mypage.characterRep")}
                </span>
              )}
              <span className={styles.nickname}>{character.nickname}</span>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default MyPageCharacter;
