import { useState } from "react";
import styles from "./WriteLetterPage.module.scss";
import DefaultButton from "../../atoms/button/DefaultButton";
import CrayonBox20 from "../../atoms/crayonBox/CrayonBox20";
import Letter from "../../atoms/letter/Letter";

function LetterWritePage() {
  const [content, setContent] = useState("");

  const onClickHandler = () => {};
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
                  src="/image.png"
                  className={`${styles.characterImg}`}
                  alt="캐릭터 사진"
                ></img>
                <div>캐릭터이름</div>
              </li>
            </ul>
          </figure>
          <figure>
            <p>받을 사람? </p>
            <select
              name="sendTo"
              id="sendTo"
              className={`${styles.sendList} ${styles.boxComponent}`}
            >
              <option value="dd">본민</option>
              <option value="dd">주현</option>
              <option value="dd">준규</option>
              <option value="dd">담현</option>
              <option value="dd">연주</option>
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
