import { useEffect, useState } from "react";
import styles from "./MotionList.module.scss";
import formatMotionName from "../../../hooks/motion/useFormatMotionName";
import { useGetMotionSelect } from "../../../hooks/motion/useGetMotionSelect";

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

function MotionList({ characterId, setGif, setMotionId }: Prop) {
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
    <>
      <div className={styles.motionListContainer}>
        <div>캐릭터 모션 종류</div>
        <div className={styles.motionSampleList}>
          {motions.map((motion, index) => (
            <div
              className={`${styles.motionSampleEach} ${
                clickedMotionIndex === index ? styles.selected : ""
              }`}
              key={index}
              onClick={() => {
                handleMotionClick(index);
              }}
            >
              <img
                className={styles.motionSampleImage}
                src={motion.path}
                alt={`${motion.name} animation`}
              />
              <div>{formatMotionName(motion.name)}</div>
            </div>
          ))}
        </div>
      </div>
    </>
  );
}

export default MotionList;
