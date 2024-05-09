import { useNavigate, useParams } from "react-router-dom";
import styles from "./SketchbookPage.module.scss";
import useSketchbook from "../../../hooks/sketchbook/useSketchbook";
import DefaultButton from "../../atoms/button/DefaultButton";
import { useState } from "react";
import { Page_Url } from "../../../router/Page_Url";
import LNB from "../../molecules/common/LNB";
import Letter from "../../atoms/letter/Letter";
import { useTranslation } from "react-i18next";

function SketchbookPage() {
  const { t } = useTranslation();
  const params = useParams() as { uuid: string };
  const { data, isLoading } = useSketchbook(params.uuid);
  const navigate = useNavigate();
  const [now, setNow] = useState(-1);
  const [letter, setLetter] = useState(0);

  type ModalName = "sketchbookInfo" | "letter";

  const [isModalOpen, setModalOpen] = useState({
    sketchbookInfo: false,
    letter: false,
  });

  const handleToggleModal = (modalName: ModalName, index: number) => {
    if (now === -1 || index === now) {
      setModalOpen((prev) => ({ ...prev, [modalName]: !prev[modalName] }));
    } else if (index !== now) {
      setNow(index);
      setLetter(0);
      if (!isModalOpen.letter) {
        setModalOpen((prev) => ({ ...prev, [modalName]: !prev[modalName] }));
      }
    }
  };

  const letterButton = (value: number) => {
    if (data) {
      const len =
        data?.data?.sketchbookCharacterMotionList[now].letterList.length;
      if (letter + value >= 0 && letter + value < len) {
        setLetter(letter + value);
      }
    }
  };

  return (
    <>
      <article className={styles.sketchbookContainer}>
        <LNB>
          {data && <h1>{data?.data?.name}</h1>}
          <DefaultButton
            onClick={() => {
              navigate(`${Page_Url.WriteLetterToSketchbook}${data?.data?.id}`);
            }}
            custom={true}
          >
            {t("sketchbook.letter")}
          </DefaultButton>
        </LNB>
        {data && (
          <figure className={styles.sketchbook}>
            {isModalOpen?.letter && (
              <div className={styles.letterBox}>
                <Letter
                  sender={
                    data?.data?.sketchbookCharacterMotionList[now]
                      ?.letterList?.[letter]?.sender?.nickname
                  }
                  content={
                    data?.data?.sketchbookCharacterMotionList[now]?.letterList[
                      letter
                    ]?.content
                  }
                ></Letter>
                <div className={styles.letterButtons}>
                  <DefaultButton onClick={() => letterButton(-1)} custom={true}>
                    {"<"}
                  </DefaultButton>
                  <DefaultButton onClick={() => letterButton(1)} custom={true}>
                    {">"}
                  </DefaultButton>
                </div>
              </div>
            )}
            <div className={styles.characterGrid}>
              {!isLoading &&
                data?.data?.sketchbookCharacterMotionList?.map(
                  (item: any, i: number) => (
                    <DefaultButton
                      onClick={() => {
                        setNow(i);
                        handleToggleModal("letter", i);
                      }}
                      custom={true}
                    >
                      <img src={item?.characterMotion?.imageUrl} />
                      <div>{item?.characterMotion?.nickname}</div>
                    </DefaultButton>
                  )
                )}
            </div>
          </figure>
        )}
      </article>
    </>
  );
}

export default SketchbookPage;
