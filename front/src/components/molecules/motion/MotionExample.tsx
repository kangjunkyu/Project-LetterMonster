import { useState } from "react";
import styles from "./MotionExample.module.scss";
import DefaultButton from "../../atoms/button/DefaultButton";
import CharacterListItem from "../../atoms/character/CharacterListItem";
import { useAlert } from "../../../hooks/notice/useAlert";
import { useGetMotionList } from "../../../hooks/motion/useGetMotionList";

interface Prop {
  isLoad: boolean;
  motionId: number;
  setMotionId: (motinoId: number) => void;
}

function MotionExample({ isLoad, setMotionId, motionId }: Prop) {
  const { data } = useGetMotionList();
  const [clickedMotionIndex, setClickedMotionIndex] = useState<number | null>(
    null
  );
  const { showAlert } = useAlert();
  const handleMotionClick = async (motionId: number) => {
    if (!isLoad) {
      setClickedMotionIndex(motionId);
      setMotionId(motionId);
    } else {
      showAlert("이전 요청을 처리하는 중이에요.");
    }
  };

  return (
    <ul className={`${styles.characterList}`}>
      {data?.data &&
        data?.data.map(
          (
            motion: { name: string; motionId: number; imageUrl: string },
            index: number
          ) => (
            <DefaultButton
              onClick={() => {
                handleMotionClick(motion.motionId);
              }}
              custom={true}
              key={index}
              className={`${
                clickedMotionIndex === motion.motionId ? styles.selected : ""
              }`}
            >
              <CharacterListItem
                item={{
                  imageUrl: motion.imageUrl,
                  nickname: motion.name,
                }}
                characterId={motionId}
              ></CharacterListItem>
            </DefaultButton>
          )
        )}
    </ul>
  );
}

export default MotionExample;
