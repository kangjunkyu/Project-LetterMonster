import { useState } from "react";
import styles from "./WriteLetterPage.module.scss";
import DefaultButton from "../../atoms/button/DefaultButton";
import CrayonBox20 from "../../atoms/crayonBox/CrayonBox20";
import Letter from "../../atoms/letter/Letter";
import { useLocation } from "react-router";
import { useParams } from "react-router-dom";
import useSketchbookList from "../../../hooks/sketchbook/useSketchbookList";
// import { useGetCharacterList } from "../../../hooks/character/useCharacterList";
import { postLetter } from "../../../api/Api";

function LetterWritePage() {
  const sketchbookId = useParams() as { sketchbookId: string }; // 스케치북 아이디
  const [content, setContent] = useState(""); // 편지내용
  const [target, setTarget] = useState(0); // 편지보낼스케치북
  const location = useLocation();
  const { characterId, gif, characterNickname, motionId } =
    location.state || {};
  const { data: sketchbookList } = useSketchbookList();

  const onClickHandler = () => {
    if (content && (target || sketchbookId)) {
      // 값 유무 확인
      postLetter(content, Number(target), 1).then((res) => {
        if (res.statusCode === 201) {
          setContent("");
        }
      });
    } else {
    }
  };
  return (
    <div className={styles.container}>
      <nav className={styles.localMenu}>
        <DefaultButton onClick={() => onClickHandler()}>임시저장</DefaultButton>
        <DefaultButton onClick={() => onClickHandler()} custom={true}>
          <CrayonBox20>편지 쓰기</CrayonBox20>
        </DefaultButton>
      </nav>
      <section className={styles.letterBox}>
        <article>
          <figure>
            <p>캐릭터선택</p>
            <ul className={`${styles.characterList} ${styles.boxComponent}`}>
              <li
                className={`${styles.characterListItem} ${styles.boxComponent}`}
              >
                <img
                  src={gif}
                  className={`${styles.characterImg}`}
                  alt="캐릭터 사진"
                ></img>
                <div>{characterNickname}</div>
              </li>
            </ul>
          </figure>
          <figure>
            <p>받을 사람? </p>
            <select
              name="sendTo"
              id="sendTo"
              className={`${styles.sendList} ${styles.boxComponent}`}
              onChange={(e) => {
                setTarget(Number(e.target.value));
              }}
            >
              {sketchbookList &&
                sketchbookList.data?.map(
                  (
                    item: { id: number; name: string; tag: number },
                    i: number
                  ) => (
                    <option value={item.id} key={i}>
                      {item.name} - {item.tag}
                    </option>
                  )
                )}
            </select>
          </figure>
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
        <div className={styles.letterSize}>
          미리보기
          <Letter sender="sk" content={content}></Letter>
        </div>
      </section>
    </div>
  );
}

export default LetterWritePage;
