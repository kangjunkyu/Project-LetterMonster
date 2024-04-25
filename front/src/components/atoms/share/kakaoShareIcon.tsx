import icon from "../../../assets/commonIcon/shareLink.svg";

interface Props {
  link: string;
  nickname: string;
}

function KakaoShareIcon({ link, nickname }: Props) {
  const { Kakao } = window;
  const handleKakaoButton = () => {
    Kakao.Share.createCustomButton({
      container: "#kakaotalk-sharing-btn",
      templateId: 107259,
      templateArgs: {
        path: link,
        NICKNAME: nickname,
      },
    });
  };
  return (
    <button
      id="kakaotalk-sharing-btn"
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
