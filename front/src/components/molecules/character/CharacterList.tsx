import DefaultButton from "../../atoms/button/DefaultButton";
import CharacterListItem from "../../atoms/character/CharacterListItem";
import styles from "./CharacterList.module.scss";

function CharacterList({ characterList, characterId, setId, children }: any) {
  return (
    <ul className={`${styles.characterList}`}>
      {characterList?.map((item: any, i: number) => (
        <DefaultButton
          onClick={() => {
            setId(item.characterId);
          }}
          custom={true}
          key={i}
        >
          <CharacterListItem
            item={item}
            characterId={characterId}
          ></CharacterListItem>

          {children}
        </DefaultButton>
      ))}{" "}
    </ul>
  );
}

export default CharacterList;
