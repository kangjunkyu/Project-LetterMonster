import DefaultButton from "../../atoms/button/DefaultButton";
import CharacterListItem from "../../atoms/character/CharacterListItem";
import styles from "./CharacterList.module.scss";
import lemonImage from "../../../assets/lemon/lemon.png";

const PropsBasicItem = {
  // id: 104,
  id: 228,
  nickname: "LEMON",
  imageUrl: lemonImage,
};

function CharacterList({ characterList, characterId, setId, children }: any) {
  return (
    <ul className={`${styles.characterList}`}>
      <DefaultButton
        onClick={() => {
          setId(228);
          // setId(104);
        }}
        custom={true}
      >
        <CharacterListItem
          item={PropsBasicItem}
          // characterId={104}
          characterId={228}
        ></CharacterListItem>
        {children}
      </DefaultButton>
      {Array.isArray(characterList) &&
        characterList?.map((item: any, i: number) => (
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
