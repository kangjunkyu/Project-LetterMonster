import styles from "./shareIcon.module.scss";

interface Props {
  link: string;
  nickname: string;
  index: number;
}

function KakaoShareIcon({ link, nickname, index }: Props) {
  const { Kakao } = window;
  const icon =
    "https://developers.kakao.com/assets/img/about/logos/kakaotalksharing/kakaotalk_sharing_btn_medium.png";
  const buttonId = `kakaotalk-sharing-btn-${index}`;
  const handleKakaoButton = () => {
    Kakao.Share.createCustomButton({
      container: `#${buttonId}`,
      templateId: 107259,
      templateArgs: {
        link: link,
        NICKNAME: nickname,
      },
    });
  };
  return (
    <button
      id={buttonId}
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
