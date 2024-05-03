import styles from "./shareIcon.module.scss";

interface Props {
  link: string;
  nickname: string;
}

function KakaoShareIcon({ link, nickname }: Props) {
  const { Kakao } = window;
  const icon =
    "https://developers.kakao.com/assets/img/about/logos/kakaotalksharing/kakaotalk_sharing_btn_medium.png";
  const handleKakaoButton = () => {
    Kakao.Share.createCustomButton({
      container: "#kakaotalk-sharing-btn",
      templateId: 107259,
      templateArgs: {
        link: link,
        NICKNAME: nickname,
      },
    });
  };
  return (
    <button
      id="kakaotalk-sharing-btn"
      className={styles.kakaotalk}
      onClick={(e) => {
        e.stopPropagation();
        handleKakaoButton();
      }}
    >
      <img src={icon} alt="공유하기 버튼" />
    </button>
  );
}

export default KakaoShareIcon;
