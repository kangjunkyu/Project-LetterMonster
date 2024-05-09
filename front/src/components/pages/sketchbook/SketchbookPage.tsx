import { useNavigate, useParams } from "react-router-dom";
import styles from "./SketchbookPage.module.scss";
import useSketchbook, {
  useDeleteSketchbook,
  usePutSketchbook,
} from "../../../hooks/sketchbook/useSketchbook";
import DefaultButton from "../../atoms/button/DefaultButton";
import { useState } from "react";
import { Page_Url } from "../../../router/Page_Url";
import LNB from "../../molecules/common/LNB";
import Letter from "../../atoms/letter/Letter";
import { useTranslation } from "react-i18next";
import { useAlert } from "../../../hooks/notice/useAlert";
import WriteButton from "../../atoms/button/WriteLetterButton";
import Modal from "../../atoms/modal/Modal";

function SketchbookPage() {
  const { t } = useTranslation();
  const params = useParams() as { uuid: string };
  const { data, isLoading } = useSketchbook(params.uuid);
  const navigate = useNavigate();
  const [now, setNow] = useState(-1);
  const [letter, setLetter] = useState(0);
  const { showAlert } = useAlert();

  type ModalName = "sketchbookInfo" | "letter";

  const [isModalOpen, setModalOpen] = useState({
    sketchbookInfo: false,
    letter: false,
  });

  const mutateSketchbookName = usePutSketchbook();
  const deleteSketchbook = useDeleteSketchbook();

  const handleToggleModal = (modalName: ModalName, index: number) => {
    if (data?.data?.sketchbookCharacterMotionList[now]?.letterList === null) {
      return showAlert("비회원은 편지를 못봐요");
    }
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
          {data && (
            <h1
              onClick={() => handleToggleModal("sketchbookInfo", 0)}
            >{`${data?.data?.name} ▼`}</h1>
          )}
          <DefaultButton
            onClick={() => {
              navigate(`${Page_Url.WriteLetterToSketchbook}${data?.data?.id}`);
            }}
            custom={true}
          >
            {t("sketchbook.letter")}
          </DefaultButton>
        </LNB>
        <WriteButton
          id="writeButton"
          onClick={() =>
            navigate(`${Page_Url.WriteLetterToSketchbook}${data?.data?.id}`)
          }
        />
        {data && (
          <figure className={styles.sketchbook}>
            {isModalOpen?.letter &&
              data?.data?.sketchbookCharacterMotionList[now]?.letterList && (
                <div className={styles.letterBox}>
                  <Letter
                    sender={
                      data?.data?.sketchbookCharacterMotionList[now]
                        ?.letterList?.[letter]?.sender?.nickname
                    }
                    content={
                      data?.data?.sketchbookCharacterMotionList[now]
                        ?.letterList?.[letter]?.content
                    }
                  ></Letter>
                  <div className={styles.letterButtons}>
                    <DefaultButton
                      onClick={() => letterButton(-1)}
                      custom={true}
                    >
                      {"<"}
                    </DefaultButton>
                    <DefaultButton
                      onClick={() => letterButton(1)}
                      custom={true}
                    >
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
        {isModalOpen.sketchbookInfo && (
          <Modal
            isOpen={isModalOpen.sketchbookInfo}
            onClose={() => handleToggleModal("sketchbookInfo", 0)}
          >
            <DefaultButton
              onClick={() =>
                mutateSketchbookName.mutate(data?.data?.id, data?.data?.name)
              }
            >
              수정
            </DefaultButton>
            <DefaultButton
              onClick={() => deleteSketchbook.mutate(data?.data?.id)}
            >
              삭제
            </DefaultButton>
          </Modal>
        )}
      </article>
    </>
  );
}

export default SketchbookPage;
