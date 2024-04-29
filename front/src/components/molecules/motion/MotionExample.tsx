import { useEffect, useState } from "react";
import styles from "./MotionExample.module.scss";
import { useGetMotionSelect } from "../../../hooks/motion/useGetMotionSelect";
import DefaultButton from "../../atoms/button/DefaultButton";
import CharacterListItem from "../../atoms/character/CharacterListItem";

interface Motion {
  name: string;
  path: string;
}

interface Prop {
  characterId: number;
  setGif: (gif: string) => void;
  setMotionId: (motinoId: number) => void;
  setCharacterMotionId?: (characterMotionId: number) => void;
}

async function loadMotions(): Promise<Motion[]> {
  const motionModules = import.meta.glob("../../../assets/motion/*.gif", {
    eager: true,
  });
  const motions: Motion[] = await Promise.all(
    Object.entries(motionModules).map(async ([path, resolver]) => {
      const name = path.split("/").pop()?.replace(".gif", "") ?? "Unknown";
      const module = resolver as { default: string };
      return { name, path: module.default };
    })
  );
  return motions;
}

function MotionExample({ characterId, setGif, setMotionId }: Prop) {
  const [motions, setMotions] = useState<Motion[]>([]);
  const [clickedMotionIndex, setClickedMotionIndex] = useState<number | null>(
    null
  );
  const getMotionSelect = useGetMotionSelect();

  const handleMotionClick = async (index: number) => {
    const motionId = index + 1;
    setClickedMotionIndex(index);
    setGif("");
    const data = await getMotionSelect(characterId, motionId);
    if (data) {
      setMotionId(motionId);
      setGif(data);
    } else {
      console.log("No motion data available");
    }
  };
  // 모션 샘플 관련
  useEffect(() => {
    loadMotions().then(setMotions);
  }, []);

  return (
    <ul className={`${styles.characterList}`}>
      {motions.map((motion, index) => (
        <DefaultButton
          onClick={() => {
            handleMotionClick(index);
          }}
          custom={true}
          key={index}
          className={`${clickedMotionIndex === index ? "selected" : ""}`}
        >
          <CharacterListItem
            item={{ imageUrl: motion.path, nickname: motion.name }}
            characterId={index}
          ></CharacterListItem>
        </DefaultButton>
      ))}
    </ul>
  );
}

export default MotionExample;
