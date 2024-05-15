import { useEffect, useState } from "react";
import { useNavigate } from "react-router";
import { useParams, useLocation } from "react-router-dom";
import { useTranslation } from "react-i18next";

import styles from "./WriteLetterPage.module.scss";
import DefaultButton from "../../atoms/button/DefaultButton";
import LNB from "../../molecules/common/LNB";
import CharacterList from "../../molecules/character/CharacterList";
import MotionExample from "../../molecules/motion/MotionExample";

import { useGetCharacterList } from "../../../hooks/character/useCharacterList";
import { useAlert } from "../../../hooks/notice/useAlert";
import { Page_Url } from "../../../router/Page_Url";
import useGetSelectedMotion from "../../../hooks/motion/useGetSelectedMotion";
import LoadingSpinner from "../../atoms/loadingSpinner/LoadingSpinner";
import useWriteLetter from "../../../hooks/letter/useWriteLetter";
import useSearchSketchbook from "../../../hooks/sketchbook/useSearchSketchbook";
import Modal from "../../atoms/modal/Modal";
import SearchList from "../../molecules/search/SearchList";
import { useGetSoloCharacter } from "../../../hooks/character/useCharacter";
import { cancelCharacter } from "../../../api/Api";
import LNBButton from "../../atoms/button/LNBButton";

function LetterWritePage() {
  const location = useLocation();
  const { t } = useTranslation();
  const sketchbookId = useParams() as { sketchbookId: string }; // 스케치북 아이디
  const {
    // gif,
    characterId: chId,
    // nickname,
    motionId: mId,
    sketchbookName,
    fromUuid,
  } = location.state || {};
  const [content, setContent] = useState(""); // 편지내용
  const [to, setTo] = useState(
    sketchbookName ? sketchbookName : t("writeletter.sketchbookSelectSentence")
  );
  const [target, setTarget] = useState(0); // 편지보낼스케치북
  const { data: characterList } = useGetCharacterList();
  const [characterId, setCharacterId] = useState(chId | 0);
  const [motionId, setMotionId] = useState(mId | 0);
  const { showAlert } = useAlert();
  const navigate = useNavigate();
  const [mounted, setMounted] = useState(false);
  const [uuid, setUuid] = useState("");
  const [searchKeyword, setSearchKeyword] = useState(
    sketchbookName ? sketchbookName : ""
  );
  const { data: searchResult } = useSearchSketchbook(searchKeyword);
  const { data: staticCharacter } = useGetSoloCharacter(characterId);
  const [isModalOpen, setModalOpen] = useState({
    findsketchbook: false,
  });

  type ModalName = "findsketchbook";

  const handleToggleModal = (modalName: ModalName) =>
    setModalOpen((prev) => ({ ...prev, [modalName]: !prev[modalName] }));

  const {
    data: selectedMotion,
    isLoading: isLoad,
    isFetching,
    isRefetching,
    isError,
  } = useGetSelectedMotion(characterId, motionId);
  const write = useWriteLetter();

  const onClickHandler = () => {
    write({
      characterId: characterId,
      content: content,
      target: target,
      motionId: motionId,
      characterMotionId: selectedMotion?.characterMotionId,
      setContent: setContent,
      isLoad: isLoad,
      uuid: uuid,
    });
  };

  const motionSeleted = async (motionId: number) => {
    setMotionId(motionId);
  };

  useEffect(() => {
    if (sketchbookId.sketchbookId) {
      setTarget(Number(sketchbookId.sketchbookId));
    }
  }, [sketchbookId.sketchbookId]);

  useEffect(() => {
    if (mounted && isError && motionId != 0)
      showAlert("이 동작은 못하겠대요. 다른 동작으로 시도해주세요!");
  }, [isError]);

  useEffect(() => {
    if (sketchbookId && sketchbookId.sketchbookId && !target) {
      setTarget(Number(sketchbookId.sketchbookId));
    }
  }, [sketchbookId, target]);

  useEffect(() => {
    setMounted(true);
  }, []);

  useEffect(() => {
    searchResult?.data?.map((item: any) => {
      if (item?.id === target) {
        setUuid(item?.uuid);
      }
    });
    if (fromUuid) {
      setUuid(fromUuid);
    }
  }, [target]);

  return (
    <div className={styles.writeContainer}>
      <LNB>
        <h1>{t("writeletter.title")}</h1>
        <LNBButton onClick={() => onClickHandler()}>
          {t("writeletter.send")}
        </LNBButton>
      </LNB>
      <section className={styles.letterBox}>
        <article>
          {localStorage.getItem("accessToken") && (
            <>
              <figure>
                <p>{t("writeletter.characterSelect")}</p>
                {characterList && (
                  <CharacterList
                    characterList={characterList}
                    characterId={characterId}
                    setId={setCharacterId}
                  ></CharacterList>
                )}
                {!characterList?.data && (
                  <div className={styles.characterList}>
                    <button
                      onClick={() => navigate(Page_Url.Sketch)}
                      className={styles.buttonItem}
                    >
                      {t("writeletter.characterDrawing")}
                    </button>
                  </div>
                )}
              </figure>
              {characterId != 0 && (
                <figure>
                  <p>{t("writeletter.motionSelect")}</p>
                  <MotionExample
                    isLoad={isFetching}
                    motionId={motionId}
                    setMotionId={motionSeleted}
                  />
                </figure>
              )}
            </>
          )}
          {!localStorage.getItem("accessToken") && (
            <div className={styles.characterList}>
              <button
                onClick={() => navigate(Page_Url.Sketch)}
                className={styles.buttonItem}
              >
                {t("writeletter.characterDrawing")}
              </button>
            </div>
          )}
          {chId && !localStorage.getItem("accessToken") && (
            <>
              <div className={styles.imgbox}>
                <img
                  className={styles.img}
                  src={staticCharacter?.data?.imageUrl}
                  alt=""
                />
                <h5 className={styles.imgNickname}>
                  {staticCharacter?.data?.nickname}
                </h5>
                <DefaultButton
                  onClick={() => {
                    cancelCharacter(chId);
                    navigate(Page_Url.Sketch);
                  }}
                >
                  다시그리기
                </DefaultButton>
              </div>
              {characterId != 0 && (
                <figure>
                  <p>{t("writeletter.motionSelect")}</p>
                  <MotionExample
                    isLoad={isFetching}
                    motionId={motionId}
                    setMotionId={motionSeleted}
                  />
                </figure>
              )}
            </>
          )}
          <figure>
            <span>{t("writeletter.sketchbookSelect")}</span>
            {isModalOpen.findsketchbook && (
              <Modal
                isOpen={isModalOpen.findsketchbook}
                onClose={() => handleToggleModal("findsketchbook")}
              >
                <input
                  type="text"
                  className={`${styles.sendList} ${styles.boxComponent}`}
                  placeholder={t("writeletter.sketchbookSearch")}
                  value={searchKeyword}
                  onChange={(e) => {
                    setSearchKeyword(e.target.value);
                  }}
                />
                <SearchList>
                  {searchResult?.data?.map(
                    (item: {
                      id: number;
                      uuid: string;
                      name: string;
                      tag: number;
                      userNickName: string;
                    }) => (
                      <DefaultButton
                        key={item.id}
                        onClick={() => {
                          setTarget(item.id);
                          setTo(
                            `${item.name} - ${item.tag} - ${item.userNickName}`
                          );
                          handleToggleModal("findsketchbook");
                        }}
                        custom={true}
                      >
                        <div>
                          {item.name} - {item.tag} - {item.userNickName}
                        </div>
                      </DefaultButton>
                    )
                  )}
                  <DefaultButton
                    onClick={() => handleToggleModal("findsketchbook")}
                  >
                    {t("close")}
                  </DefaultButton>
                </SearchList>
              </Modal>
            )}
            <button
              className={`${styles.sendList} ${styles.boxComponent}`}
              onClick={() => handleToggleModal("findsketchbook")}
              disabled={sketchbookName ? true : false}
            >
              {to}
            </button>
          </figure>
        </article>
        <article>
          {isFetching && <LoadingSpinner />}
          {!isRefetching && <img src={selectedMotion?.imageUrl} />}
          <figure className={styles.writeBox}>
            <div>{t("writeletter.letterContent")}</div>
            <textarea
              name="letterContent"
              id="letterContent"
              className={styles.letterContent}
              cols={30}
              rows={10}
              onChange={(e) => setContent(e.target.value)}
            ></textarea>
            <DefaultButton onClick={() => onClickHandler()}>
              {t("writeletter.send")}
            </DefaultButton>
          </figure>
        </article>
      </section>
    </div>
  );
}

export default LetterWritePage;
