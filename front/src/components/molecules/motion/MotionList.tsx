import { useEffect, useState } from "react";
import styles from "./MotionList.module.scss";
import formatMotionName from "../../../hooks/motion/useFormatMotionName";
import { useTranslation } from "react-i18next";
import { useGetMotionList } from "../../../hooks/motion/useGetMotionList";
import useGetSelectedMotion from "../../../hooks/motion/useGetSelectedMotion";
import LoadingSpinner from "../../atoms/loadingSpinner/LoadingSpinner";
import { useAlert } from "../../../hooks/notice/useAlert";

interface Prop {
  characterId: number;
  setGif: (gif: { imageUrl: string }) => void;
  setMotionId: (motinoId: number) => void;
  setCharacterMotionId?: (characterMotionId: number) => void;
  setLoad: () => void;
}

function MotionList({ characterId, setGif, setMotionId, setLoad }: Prop) {
  const { t } = useTranslation();
  const { data: motionList, isLoading } = useGetMotionList();
  const [localMotionId, setLocalMotionId] = useState(0);
  const [clickedMotionIndex, setClickedMotionIndex] = useState(0);
  const { showAlert } = useAlert();

  const {
    data: seletedMotion,
    isLoading: isLoad,
    isFetching,
    isRefetching,
  } = useGetSelectedMotion(characterId, localMotionId);

  const handleMotionClick = async (motionId: number) => {
    if (!isRefetching && !isLoad) {
      setLocalMotionId(motionId);
      setClickedMotionIndex(motionId);
      setGif({ imageUrl: "" });
    } else {
      showAlert("캐릭터가 아직 연습중이에요");
    }
  };

  // 모션 샘플 관련
  useEffect(() => {
    if (!isLoad && !isRefetching && seletedMotion) {
      setMotionId(localMotionId);
      setGif({ imageUrl: seletedMotion?.imageUrl });
      setLoad();
    } else if (isRefetching || isFetching) {
      setLoad();
    }
  }, [isLoad, isRefetching, isFetching]);

  return (
    <>
      <div className={styles.motionListContainer}>
        <div>{t("motion.motions")}</div>
        {isFetching && <LoadingSpinner />}
        <div className={styles.motionSampleList}>
          {!isLoading &&
            motionList?.data &&
            motionList?.data.map(
              (
                motion: {
                  name: string;
                  motionId: number;
                  imageUrl: string;
                },
                index: number
              ) => (
                <div
                  className={`${styles.motionSampleEach} ${
                    clickedMotionIndex === motion.motionId
                      ? styles.selected
                      : ""
                  }`}
                  key={index}
                  onClick={() => {
                    handleMotionClick(motion.motionId);
                  }}
                >
                  <img
                    className={styles.motionSampleImage}
                    src={motion.imageUrl}
                    alt={`${motion.name} animation`}
                  />
                  <div>{formatMotionName(motion.name)}</div>
                </div>
              )
            )}
        </div>
        <div className={styles.motionSampleListHorizontal}>
          {!isLoading &&
            motionList?.data &&
            motionList?.data.map(
              (
                motion: {
                  name: string;
                  motionId: number;
                  imageUrl: string;
                },
                index: number
              ) => (
                <div
                  className={`${styles.motionSampleEachHorizontal} ${
                    clickedMotionIndex === motion.motionId
                      ? styles.selected
                      : ""
                  }`}
                  key={index}
                  onClick={() => {
                    handleMotionClick(motion.motionId);
                  }}
                >
                  <img
                    className={styles.motionSampleImage}
                    src={motion?.imageUrl}
                    alt={`${motion.name} animation`}
                  />
                  <div>{formatMotionName(motion.name)}</div>
                </div>
              )
            )}
        </div>
      </div>
    </>
  );
}

export default MotionList;
