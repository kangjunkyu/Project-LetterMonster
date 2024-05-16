import styles from "./SketchGuidePage.module.scss";

const GuidePage = ({ onClose }: any) => {
  return (
    <div className={styles.overlay} onClick={onClose}>
      <div className={styles.guideContainer}>
        <h1>그림판 사용 가이드</h1>
        <div className={styles.guideContent}>
          <h2>캐릭터 별명</h2>
          <p>위의 입력창에 나만의 캐릭터 이름을 꼭 지어주세요!</p>
          <h2>앨범에서 선택</h2>
          <p>배경이 없는 인물, 캐릭터 사진을 올릴 수 있어요!</p>
          <h2>그리기 도구</h2>
          <p>선의 굵기, 펜, 지우개,</p>
          <p>선 또는 그림 움직이기, 색깔 선택이 가능해요!</p>
          <br />
          <div className={styles.guide2}>
            <h3>별명을 짓고 캐릭터를 다 그리면,</h3>
            <h3>
              위의 <span className={styles.highlight}>완성</span> 버튼을
              눌러주세요!
            </h3>
          </div>
          <br />
          <br />
          <div className={styles.guide1}>
            <h3>
              <span>Tip!!</span> 캐릭터&nbsp;
              <span>팔 다리를 분명하게</span> 그리면
            </h3>
            <h3>훨씬 더 예쁘게 나옵니다!!</h3>
          </div>
        </div>
      </div>
    </div>
  );
};

export default GuidePage;
