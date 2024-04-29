import styles from "./WelcomePage.module.scss";

function WelcomePage() {
  return (
    <div className={styles.WelcomeContainer}>
      <div>{`레터 몬스터는 <24/04/28>일 1차 배포 목표로 준비중입니다.`}</div>
      <div>{`レターモンスターは <24/04/28> 臨時配布を目指して頑張ってます。ლ(•̀ _ •́ ლ)`}</div>
      <div>
        <img
          className={styles.character}
          src="/src/assets/characterSample/gom.gif"
          alt=""
        />
        <img
          className={styles.character}
          src="/src/assets/characterSample/hojin_character.gif"
          alt=""
        />
        <img
          className={styles.character}
          src="/src/assets/characterSample/egypt.gif"
          alt=""
        />
        <img
          className={styles.character}
          src="/src/assets/characterSample/rabbit.gif"
          alt=""
        />
        <img
          className={styles.character}
          src="/src/assets/characterSample/shinzzang.gif"
          alt=""
        />
        <img
          className={styles.character}
          src="/src/assets/characterSample/television.gif"
          alt=""
        />
        <img
          className={styles.character}
          src="/src/assets/characterSample/attamoma.gif"
          alt=""
        />
        <img
          className={styles.character}
          src="/src/assets/characterSample/juhyeon.gif"
          alt=""
        />
      </div>
    </div>
  );
}

export default WelcomePage;
