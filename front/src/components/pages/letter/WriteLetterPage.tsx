import { useEffect, useState } from "react";
import { useNavigate } from "react-router";
import { useParams, useLocation } from "react-router-dom";
import { useTranslation } from "react-i18next";

import styles from "./WriteLetterPage.module.scss";
import DefaultButton from "../../atoms/button/DefaultButton";
import LNB from "../../molecules/common/LNB";
import CharacterList from "../../molecules/character/CharacterList";
import MotionExample from "../../molecules/motion/MotionExample";

import { useSketchbookListAll } from "../../../hooks/sketchbook/useSketchbookList";
import { useGetCharacterList } from "../../../hooks/character/useCharacterList";
import { useAlert } from "../../../hooks/notice/useAlert";
import { Page_Url } from "../../../router/Page_Url";
import useGetSelectedMotion from "../../../hooks/motion/useGetSelectedMotion";
import LoadingSpinner from "../../atoms/loadingSpinner/LoadingSpinner";
import useWriteLetter from "../../../hooks/letter/useWriteLetter";
import useSearchSketchbook from "../../../hooks/sketchbook/useSearchSketchbook";
import Modal from "../../atoms/modal/Modal";
import SearchList from "../../molecules/search/SearchList";

function LetterWritePage() {
  const location = useLocation();
  const { t } = useTranslation();
  const sketchbookId = useParams() as { sketchbookId: string }; // 스케치북 아이디
  const {
    gif,
    characterId: chId,
    // nickname,
    motionId: mId,
  } = location.state || {};
  const [content, setContent] = useState(""); // 편지내용
  const [to, setTo] = useState(t("writeletter.sketchbookSelectSentence"));
  const [target, setTarget] = useState(0); // 편지보낼스케치북
  const { data: sketchbookList, isLoading } = useSketchbookListAll();
  const { data: characterList } = useGetCharacterList();
  const [characterId, setCharacterId] = useState(chId | 0);
  const [motionId, setMotionId] = useState(mId | 0);
  const { showAlert } = useAlert();
  const navigate = useNavigate();
  const [mounted, setMounted] = useState(false);
  const [searchKeyword, setSearchKeyword] = useState("");
  const { data: searchResult } = useSearchSketchbook(searchKeyword);

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
      content: content,
      target: target,
      motionId: motionId,
      characterMotionId: selectedMotion?.characterMotionId,
      setContent: setContent,
      isLoad: isLoad,
    });
  };

  const motionSeleted = async (motionId: number) => {
    setMotionId(motionId);
  };

  useEffect(() => {
    if (!isLoading) {
      setTarget(sketchbookList?.data[0]?.id);
    }
    if (sketchbookId.sketchbookId) {
      setTarget(Number(sketchbookId.sketchbookId));
    }
  }, [isLoading]);

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

  return (
    <div className={styles.writeContainer}>
      <LNB>
        <h1>{t("writeletter.title")}</h1>
        <DefaultButton onClick={() => onClickHandler()} custom={true}>
          {t("writeletter.send")}
        </DefaultButton>
      </LNB>
      <section className={styles.letterBox}>
        <article>
          {!chId && (
            <>
              <figure>
                <p>{t("writeletter.characterSelect")}</p>
                {characterList?.data && (
                  <CharacterList
                    characterList={characterList}
                    characterId={characterId}
                    setId={setCharacterId}
                  ></CharacterList>
                )}
                {!characterList?.data && !gif && (
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
              <figure>
                {characterId != 0 && (
                  <>
                    <p>{t("writeletter.motionSelect")}</p>
                    <MotionExample
                      isLoad={isFetching}
                      setMotionId={motionSeleted}
                    />
                  </>
                )}
              </figure>
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
                    }) => (
                      <DefaultButton
                        key={item.id}
                        onClick={() => {
                          setTarget(item.id);
                          setSearchKeyword("");
                          setTo(`${item.name} - ${item.tag}`);
                          handleToggleModal("findsketchbook");
                        }}
                        custom={true}
                      >
                        <div>
                          {item.name} - {item.tag}
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
            <div
              className={`${styles.sendList} ${styles.boxComponent} ${styles.cursor}`}
              onClick={() => handleToggleModal("findsketchbook")}
            >
              {to}
            </div>
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
