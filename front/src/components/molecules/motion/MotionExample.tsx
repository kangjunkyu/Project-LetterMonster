import { useEffect, useState } from "react";
import styles from "./MotionExample.module.scss";
import DefaultButton from "../../atoms/button/DefaultButton";
import CharacterListItem from "../../atoms/character/CharacterListItem";
import { useAlert } from "../../../hooks/notice/useAlert";

interface Motion {
  name: string;
  path: string;
}

interface Prop {
  isLoad: boolean;
  motionId: number;
  setMotionId: (motinoId: number) => void;
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

function MotionExample({ isLoad, setMotionId, motionId }: Prop) {
  const [motions, setMotions] = useState<Motion[]>([]);
  const [clickedMotionIndex, setClickedMotionIndex] = useState<number | null>(
    null
  );
  const { showAlert } = useAlert();
  const handleMotionClick = async (index: number) => {
    if (!isLoad) {
      const motionId = index + 1;
      setClickedMotionIndex(index);
      setMotionId(motionId);
    } else {
      showAlert("이전 요청을 처리하는 중이에요.");
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
          className={`${clickedMotionIndex === index ? styles.selected : ""}`}
        >
          <CharacterListItem
            item={{ imageUrl: motion.path, nickname: motion.name }}
            characterId={motionId}
          ></CharacterListItem>
        </DefaultButton>
      ))}
    </ul>
  );
}

export default MotionExample;
