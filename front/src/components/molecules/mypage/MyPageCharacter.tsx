// import { useState } from "react";
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
        <div className={styles.titleNoCharacter}>캐릭터 목록</div>
        <div>
          <div>현재 캐릭터가 없어요!</div>
          <button
            className={styles.userNoCharacterButton}
            onClick={goToCreateCharacter}
          >
            캐릭터 그리기
          </button>
        </div>
      </div>
    );
  }

  return (
    <div className={styles.userCharacterListContainer}>
      <div className={styles.title}>
        <div>캐릭터 목록</div>
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
                  onClick={() => handleMainCharacterClick(character.characterId)}
              >
                대표 지정
              </button>
              <button
                  className={styles.deleteButton}
                  onClick={() => deleteCharacter.mutate(character.characterId)}
              >
                삭제
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default MyPageCharacter;
