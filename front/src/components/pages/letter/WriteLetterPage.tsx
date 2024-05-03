import { useEffect, useState } from "react";
import { useNavigate } from "react-router";
import { useParams } from "react-router-dom";

import styles from "./WriteLetterPage.module.scss";
import DefaultButton from "../../atoms/button/DefaultButton";
import LNB from "../../molecules/common/LNB";
import CharacterList from "../../molecules/character/CharacterList";
import MotionExample from "../../molecules/motion/MotionExample";

import { useSketchbookListAll } from "../../../hooks/sketchbook/useSketchbookList";
import { postLetter } from "../../../api/Api";
import { useGetCharacterList } from "../../../hooks/character/useCharacterList";
import { useAlert } from "../../../hooks/notice/useAlert";
import { Page_Url } from "../../../router/Page_Url";
import useGetSelectedMotion from "../../../hooks/motion/useGetSelectedMotion";
import LoadingSpinner from "../../atoms/loadingSpinner/LoadingSpinner";

function LetterWritePage() {
  const sketchbookId = useParams() as { sketchbookId: string }; // 스케치북 아이디
  const [content, setContent] = useState(""); // 편지내용
  const [target, setTarget] = useState(0); // 편지보낼스케치북
  const { data: sketchbookList, isLoading } = useSketchbookListAll();
  const { data: characterList } = useGetCharacterList();
  const [characterId, setCharacterId] = useState(0);
  const [motionId, setMotionId] = useState(0);
  const { showAlert } = useAlert();
  const navigate = useNavigate();
  const [mounted, setMounted] = useState(false);

  const {
    data: selectedMotion,
    isLoading: isLoad,
    isFetching,
    isRefetching,
    isError,
  } = useGetSelectedMotion(characterId, motionId);

  const onClickHandler = () => {
    if (
      content &&
      (target || sketchbookId) &&
      motionId &&
      selectedMotion?.data?.characterMotionId != 0
    ) {
      // 값 유무 확인
      postLetter(
        content,
        Number(target),
        selectedMotion?.data?.characterMotionId
      ).then((res) => {
        if (res.statusCode === 201) {
          setContent("");
          showAlert("편지를 보냈어요!");
          navigate(Page_Url.SketchbookList);
        }
      });
    } else if (isLoad) {
      showAlert("캐릭터가 아직 동작을 연습중이에요");
    } else {
      showAlert("보낼 편지를 확인해주세요");
    }
  };
  const motionSeleted = async (motionId: any) => {
    setMotionId(motionId);
  };

  useEffect(() => {
    if (!isLoading) {
      setTarget(sketchbookList?.data[0]?.id);
    }
  }, [isLoading]);

  useEffect(() => {
    if (mounted && isError && motionId != 0)
      showAlert("이 동작은 못하겠대요 ㅜ");
  }, [isError]);

  useEffect(() => {
    if (sketchbookId && sketchbookId.sketchbookId && !target) {
      // URL 파라미터에서 받은 sketchbookId가 숫자 타입이라고 가정할 때
      setTarget(Number(sketchbookId.sketchbookId));
    }
  }, [sketchbookId, target]);

  useEffect(() => {
    setMounted(true);
  }, []);

  return (
    <div className={styles.writeContainer}>
      <LNB>
        <h1>편지 쓰기</h1>
        <DefaultButton onClick={() => onClickHandler()} custom={true}>
          보내기
        </DefaultButton>
      </LNB>
      <section className={styles.letterBox}>
        <article>
          <figure>
            <p>캐릭터 고르기</p>
            {characterList && (
              <CharacterList
                characterList={characterList}
                characterId={characterId}
                setId={setCharacterId}
              ></CharacterList>
            )}
            {!characterList?.data?.data && (
              <div className={styles.characterList}>
                <button
                  onClick={() => navigate(Page_Url.Sketch)}
                  className={styles.buttonItem}
                >
                  내 캐릭터 그리러 가기
                </button>
              </div>
            )}
          </figure>
          <figure>
            {characterId != 0 && (
              <>
                <p>동작 고르기</p>
                <MotionExample
                  isLoad={isFetching}
                  setMotionId={motionSeleted}
                />
              </>
            )}
          </figure>
          <figure>
            <p>받을 사람</p>
            <select
              name="sendTo"
              id="sendTo"
              className={`${styles.sendList} ${styles.boxComponent}`}
              onChange={(e) => {
                setTarget(Number(e.target.value));
              }}
              value={target} // useState를 사용하여 관리되는 상태를 value로 연결합니다.
              disabled={sketchbookId ? true : false} // sketchbookList가 로드되지 않았다면 select를 비활성화합니다.
            >
              {sketchbookList?.data?.map(
                (
                  item: {
                    id: number;
                    name: string;
                    tag: number;
                    holder: { nickname: string };
                  },
                  i: number
                ) => (
                  <option value={item?.id} key={i}>
                    {item?.name} - {item?.tag} - {item?.holder?.nickname}
                  </option>
                )
              )}
            </select>
          </figure>
        </article>
        <article>
          {isFetching && <LoadingSpinner />}
          {!isRefetching && <img src={selectedMotion?.imageUrl} />}
          <figure>
            <div>편지 내용</div>
            <textarea
              name="letterContent"
              id="letterContent"
              className={styles.letterContent}
              cols={30}
              rows={10}
              onChange={(e) => setContent(e.target.value)}
            ></textarea>
          </figure>
        </article>
      </section>
    </div>
  );
}

export default LetterWritePage;
