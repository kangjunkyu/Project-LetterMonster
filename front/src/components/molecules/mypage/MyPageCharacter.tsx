import { useState } from "react";
import { useSelectMainCharacter } from "../../../hooks/character/useCharacter";
import { useGetCharacterList } from "../../../hooks/character/useCharacterList";
import styles from "./MyPageMolecules.module.scss";

interface Character {
  characterId: number;
  imageUrl: string;
  mainCharacter: boolean;
  nickname: string;
}

function MyPageCharacter() {
  const { data: characterList, isLoading, error } = useGetCharacterList();
  const selectMainCharacter = useSelectMainCharacter();
  const [selectedCharacterId, setSelectedCharacterId] = useState<number | null>(
    null
  );

  const handleSelectChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    const characterId = parseInt(event.target.value, 10);
    setSelectedCharacterId(characterId);
    // selectMainCharacter.mutate(characterId);
  };

  const handleCardClick = (characterId: number) => {
    setSelectedCharacterId(characterId);
    selectMainCharacter.mutate(characterId);
  };

  if (isLoading) return <div>Loading characters...</div>;
  if (error) return <div>Error loading characters!</div>;

  console.log(characterList);

  return (
    <div className={styles.userCharacterListContainer}>
      <div className={styles.title}>
        <div>캐릭터 목록</div>
        <select onChange={handleSelectChange} value={selectedCharacterId || ""}>
          <option value="">대표 캐릭터 선택</option>
          {characterList.map((character: Character) => (
            <option key={character.characterId} value={character.characterId}>
              {character.nickname}
            </option>
          ))}
        </select>
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
            <img
              src={character.imageUrl}
              alt={character.nickname}
              className={styles.userCharacterImage}
            />
            <div className={styles.userCharacterInfo}>
              <div
                className={
                  character.mainCharacter ? styles.userMainCharacter : ""
                }
              >
                {character.mainCharacter}
                {character.nickname}
                {character.mainCharacter && (
                  <span
                    style={{
                      backgroundColor: "red",
                      color: "white",
                      marginLeft: "5px",
                      padding: "2px 5px",
                      borderRadius: "3px",
                    }}
                  >
                    대표
                  </span>
                )}
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default MyPageCharacter;
