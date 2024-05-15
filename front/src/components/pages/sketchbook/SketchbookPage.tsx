import { useNavigate, useParams } from "react-router-dom";
import styles from "./SketchbookPage.module.scss";
import useSketchbook, {
  useDeleteSketchbook,
  usePutSketchbook,
  usePutSketchbookOpen,
} from "../../../hooks/sketchbook/useSketchbook";
import DefaultButton from "../../atoms/button/DefaultButton";
import { useEffect, useState } from "react";
import { Page_Url } from "../../../router/Page_Url";
import LNB from "../../molecules/common/LNB";
import Letter from "../../atoms/letter/Letter";
import { useTranslation } from "react-i18next";
import { useAlert } from "../../../hooks/notice/useAlert";
import WriteButton from "../../atoms/button/WriteLetterButton";
import Modal from "../../atoms/modal/Modal";
import { useQueryClient } from "@tanstack/react-query";
import {
  useFavoriteSketchbookCheck,
  useFavoriteSketchbookOn,
} from "../../../hooks/sketchbook/useFavorite";
import Star from "../../../assets/commonIcon/star.svg?react";
import FilledStar from "../../../assets/commonIcon/filledStar.svg?react";
import useCheckTokenExpiration from "../../../hooks/auth/useCheckTokenExpiration";
import KakaoShareIcon from "../../atoms/share/kakaoShareIcon";
import CommonShareIcon from "../../atoms/share/commonShareIcon";

function SketchbookPage() {
  const { t } = useTranslation();
  const params = useParams() as { uuid: string };
  const { data, isLoading } = useSketchbook(params.uuid);
  const navigate = useNavigate();
  const [now, setNow] = useState(-1);
  const [letter, setLetter] = useState(0);
  const [name, setName] = useState(data?.data?.name);
  const { showAlert } = useAlert();
  const mutateSketchbookName = usePutSketchbook();
  const deleteSketchbook = useDeleteSketchbook();
  const mutateSketchbookOpen = usePutSketchbookOpen();
  const queryClient = useQueryClient();
  const mutateSketchbookFavorite = useFavoriteSketchbookOn();
  const { data: Favorite } = useFavoriteSketchbookCheck(data?.data?.id);

  useEffect(() => {
    setName(data?.data?.name);

    // 클린업 함수
    return () => {
      setName("");
      queryClient.invalidateQueries({ queryKey: ["sketchbook"] });
    };
  }, [data, setName]);

  type ModalName = "sketchbookInfo" | "letter" | "deleteAlert" | "renameAlert";

  const [isModalOpen, setModalOpen] = useState({
    sketchbookInfo: false,
    letter: false,
    deleteAlert: false,
    renameAlert: false,
  });

  const handleToggleModal = (modalName: ModalName, index: number) => {
    if (data?.data?.sketchbookCharacterMotionList[now]?.letterList === null) {
      return showAlert(`${t("notification.noregist")}`);
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

  const handleUserNicknameChange = (nickname: string) => {
    if (nickname.startsWith(" ") || nickname.includes("　")) {
      showAlert(`${t("paint.pleaseDont")}`);
    } else if (nickname.length > 10) {
      showAlert(`${t("paint.pleaseTen")}`);
    } else {
      mutateSketchbookName.mutate({ sketchbookId: data?.data?.id, name: name });
      handleToggleModal("sketchbookInfo", 0);
      handleToggleModal("renameAlert", 0);
    }
  };

  const writeLetter = () => {
    localStorage.getItem("accessToken")
      ? navigate(`${Page_Url.WriteLetterToSketchbook}${data?.data?.id}`, {
          state: {
            sketchbookName: data?.data?.name,
          },
        })
      : navigate(Page_Url.Sketch, {
          state: {
            sketchbookId: data?.data?.id,
            sketchbookName: data?.data?.name,
            fromUuid: data?.data?.uuid,
          },
        });
  };

  const inputEnter = (
    e:
      | React.KeyboardEvent<HTMLButtonElement>
      | React.KeyboardEvent<HTMLInputElement>
  ) => {
    if (e.key === "Enter") {
      handleUserNicknameChange(name);
    }
  };

  const checkToken = useCheckTokenExpiration();

  return (
    <>
      <article className={styles.sketchbookContainer}>
        <LNB>
          {data && (
            <h1
              onClick={() => {
                if (
                  localStorage.getItem("accessToken") &&
                  data?.data?.isWritePossible
                )
                  handleToggleModal("sketchbookInfo", 0);
              }}
            >{`${data?.data?.name} ▼ ${
              data?.data?.isPublic
                ? `${t("sketchbook.public")}`
                : `${t("sketchbook.private")}`
            } `}</h1>
          )}
          <DefaultButton onClick={() => writeLetter()} custom={true}>
            {t("sketchbook.letter")}
          </DefaultButton>
          {Favorite?.data ? (
            <button
              onClick={() => {
                mutateSketchbookFavorite.mutate(data?.data?.id);
              }}
            >
              <FilledStar width={30} height={30} />
            </button>
          ) : (
            checkToken(localStorage.getItem("accessToken")) && (
              <button
                onClick={() => {
                  mutateSketchbookFavorite.mutate(data?.data?.id);
                }}
              >
                <Star width={30} height={30} />
              </button>
            )
          )}
        </LNB>
        <WriteButton id="writeButton" onClick={() => writeLetter()} />
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
                      key={i}
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
            <div className={styles.buttonBox}>
              <DefaultButton
                onClick={() => handleToggleModal("renameAlert", 0)}
              >
                {t("sketchbook.rename")}
              </DefaultButton>
              <DefaultButton
                onClick={() => handleToggleModal("deleteAlert", 0)}
              >
                {t("sketchbook.delete")}
              </DefaultButton>
              <DefaultButton
                onClick={() => {
                  mutateSketchbookOpen.mutate(data?.data?.id);
                  handleToggleModal("sketchbookInfo", 0);
                }}
              >
                {data?.data?.isPublic
                  ? `${t("sketchbook.changePrivate")}`
                  : `${t("sketchbook.changePublic")}`}
              </DefaultButton>
              <div className={styles.linkBox}>
                <CommonShareIcon link={data?.data?.shareLink} />
                <KakaoShareIcon
                  link={data?.data?.shareLink}
                  nickname={data?.data?.holder.nickname}
                  index={0}
                />
              </div>
            </div>
          </Modal>
        )}
        {isModalOpen.deleteAlert && (
          <Modal
            isOpen={isModalOpen.deleteAlert}
            onClose={() => handleToggleModal("deleteAlert", 0)}
          >
            <div className={styles.buttonBox}>
              {t("sketchbook.check")}
              <DefaultButton
                onClick={() => deleteSketchbook.mutate(data?.data?.id)}
              >
                {t("sketchbook.delete")}
              </DefaultButton>
              <DefaultButton
                onClick={() => {
                  handleToggleModal("sketchbookInfo", 0);
                  handleToggleModal("deleteAlert", 0);
                }}
              >
                {t("sketchbook.cancel")}
              </DefaultButton>
            </div>
          </Modal>
        )}
        {isModalOpen.renameAlert && (
          <Modal
            isOpen={isModalOpen.renameAlert}
            onClose={() => handleToggleModal("renameAlert", 0)}
          >
            <div className={styles.buttonBox}>
              <input
                placeholder={t("sketchbook.rename")}
                defaultValue={name}
                onChange={(e) => setName(e.target.value)}
                onKeyDown={(e: React.KeyboardEvent<HTMLInputElement>) =>
                  inputEnter(e)
                }
              />
              <DefaultButton onClick={() => handleUserNicknameChange(name)}>
                {t("sketchbook.rename")}
              </DefaultButton>
              <DefaultButton
                onClick={() => {
                  handleToggleModal("sketchbookInfo", 0);
                  handleToggleModal("renameAlert", 0);
                }}
              >
                {t("sketchbook.cancel")}
              </DefaultButton>
            </div>
          </Modal>
        )}
      </article>
    </>
  );
}

export default SketchbookPage;
