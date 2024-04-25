import { useEffect, useState } from "react";
import styles from "./MotionList.module.scss";
// import { useGetMotionSelect } from "../../../hooks/motion/useGetMotionSelect";

interface Motion {
  name: string;
  path: string;
  characterId: number;
  motionId: number;
}

async function loadMotions(): Promise<Motion[]> {
  const motionModules = import.meta.glob("../../../assets/motion/*.gif", {
    eager: true,
  });
  const motions: Motion[] = await Promise.all(
    Object.entries(motionModules).map(async ([path, resolver]) => {
      const name = path.split("/").pop()?.replace(".gif", "") ?? "Unknown";
      const module = resolver as { default: string };
      return { name, path: module.default, characterId: 0, motionId: 1 };
    })
  );
  return motions;
}

function MotionList() {
  const [clickedMotionIndex, setClickedMotionIndex] = useState<number | null>(
    null
  );
  const handleMotionClick = (index: number) => {
    setClickedMotionIndex(index);
  };

  // const [selectedMotion, setSelectedMotion] = useState<Motion | null>(null);
  // const { data: motionData, refetch } = useGetMotionSelect(
  //   selectedMotion?.characterId ?? 0,
  //   selectedMotion?.motionId ?? 0
  // );
  // const handleSelectMotion = (motion: Motion) => {
  //   setSelectedMotion(motion); // 선택된 모션을 상태로 설정합니다.
  //   refetch(); // 쿼리를 수동으로 다시 실행합니다.
  // };

  // 모션 샘플 관련
  const [motions, setMotions] = useState<Motion[]>([]);

  useEffect(() => {
    loadMotions().then((motions) => setMotions(motions));
  }, []);

  return (
    <>
      <div className={styles.motionListContainer}>
        <div>Motion 종류</div>
        <div className={styles.motionSampleList}>
          {motions.map((motion, index) => (
            <div
              className={`${styles.motionSampleEach} ${
                clickedMotionIndex === index ? styles.selected : ""
              }`}
              key={index}
              onClick={() => {
                // handleSelectMotion(motion);
                handleMotionClick(index);
              }}
            >
              <img
                className={styles.motionSampleImage}
                src={motion.path}
                alt={`${motion.name} animation`}
              />
              <div>
                {/* {index + 1}번 :  */}
                {motion.name}
              </div>
            </div>
          ))}
        </div>
      </div>
    </>
  );
}

export default MotionList;
