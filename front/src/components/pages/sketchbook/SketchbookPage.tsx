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
import Modal from "../../atoms/modal/Modal";
import Letter from "../../atoms/letter/Letter";

function SketchbookPage() {
  const params = useParams() as { uuid: string };
  const { data } = useSketchbook(params.uuid);
  const putSketchbook = usePutSketchbook();
  const deleteSketchbook = useDeleteSketchbook();
  const [
    name,
    // setName
  ] = useState("임시수정");
  const navigate = useNavigate();
  const [now, setNow] = useState(0);
  const [letter, setLetter] = useState(0);

  type ModalName = "sketchbookInfo" | "letter";

  const [isModalOpen, setModalOpen] = useState({
    sketchbookInfo: false,
    letter: false,
  });
  const handleToggleModal = (modalName: ModalName) =>
    setModalOpen((prev) => ({ ...prev, [modalName]: !prev[modalName] }));

  const letterButton = (value: number) => {
    if (data) {
      const len =
        data?.data?.sketchbookCharacterMotionList[now].letterList.length;
      if (letter + value >= 0 && letter + value < len) {
        setLetter(letter + value);
      }
    }
  };

  const characterButton = (value: number) => {
    setLetter(0);
    if (data) {
      const len = data?.data?.sketchbookCharacterMotionList.length;
      if (now + value >= 0 && now + value < len) {
        setNow(now + value);
      }
    }
  };
  return (
    <>
      <Modal
        isOpen={isModalOpen.sketchbookInfo}
        onClose={() => handleToggleModal("sketchbookInfo")}
      >
        <div className={styles.buttonBox}>
          <DefaultButton
            onClick={() =>
              putSketchbook.mutate({
                sketchbookId: Number(data?.data?.id),
                name: name,
              })
            }
          >
            수정
          </DefaultButton>
          <DefaultButton
            onClick={() => deleteSketchbook.mutate(Number(data?.data?.id))}
          >
            삭제
          </DefaultButton>
          <DefaultButton
            onClick={() => {
              navigate(`${Page_Url.WriteLetterToSketchbook}${data?.data?.id}`);
            }}
          >
            편지쓰기
          </DefaultButton>
        </div>
      </Modal>
      <article className={styles.sketchbookContainer}>
        <LNB>
          {data && <div>{data?.data?.name} 스케치북</div>}
          <DefaultButton
            onClick={() => handleToggleModal("sketchbookInfo")}
            custom={true}
          >
            더보기
          </DefaultButton>
        </LNB>
        {data && (
          <figure className={styles.sketchbook}>
            {isModalOpen.letter && (
              <div className={styles.letterBox}>
                <Letter
                  receiver={"나"}
                  sender={
                    data?.data?.sketchbookCharacterMotionList[now]
                      ?.letterList?.[letter].sender.nickname
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
            {data?.data?.sketchbookCharacterMotionList[0] && (
              <div className={styles.characterBox}>
                <DefaultButton
                  onClick={() => handleToggleModal("letter")}
                  custom={true}
                >
                  <img
                    src={
                      data?.data?.sketchbookCharacterMotionList[now]
                        ?.characterMotion?.imageUrl
                    }
                    alt=""
                  />
                  {
                    data?.data?.sketchbookCharacterMotionList[now]
                      ?.characterMotion?.nickname
                  }
                </DefaultButton>
                <div className={styles.letterButtons}>
                  <DefaultButton
                    onClick={() => characterButton(-1)}
                    custom={true}
                  >
                    {"<"}
                  </DefaultButton>
                  <DefaultButton
                    onClick={() => characterButton(1)}
                    custom={true}
                  >
                    {">"}
                  </DefaultButton>
                </div>
              </div>
            )}
          </figure>
        )}
      </article>
    </>
  );
}

export default SketchbookPage;
