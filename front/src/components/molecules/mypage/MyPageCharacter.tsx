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

  const handleCardClick = (characterId: number) => {
    selectMainCharacter.mutate(characterId);
  };

  const goToCreateCharacter = () => {
    navigate(Page_Url.Sketch);
  };

  if (isLoading) return <div>Loading characters...</div>;
  if (error) return <div>Error loading characters!</div>;
  if (characterList.length === 0) {
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

  console.log(characterList);

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
            onClick={() => {
              console.log(character.characterId);
              handleCardClick(character.characterId);
            }}
          >
            <div className={styles.userCharacterListUpper}>
              <button
                onClick={() =>
                  selectMainCharacter.mutate(character.characterId)
                }
              >
                대표 지정
              </button>
              <button
                onClick={() => deleteCharacter.mutate(character.characterId)}
              >
                삭제
              </button>
            </div>
            <img
              src={character.imageUrl}
              alt={character.nickname}
              className={styles.userCharacterImage}
            />
            <div className={styles.userCharacterInfo}>
                <span className={styles.nickname}>{character.nickname}</span>
                {character.mainCharacter && (
                  <span className={styles.mainCharacterLabel}>대표</span>
                )}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default MyPageCharacter;
