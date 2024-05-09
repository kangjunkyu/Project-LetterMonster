import styles from "./MainPage.module.scss";
import LanguageSwitcher from "../../molecules/language/LanguageSwitcher";
import lemon from "../../../assets/characterSample/test_dab.gif";
import { useState } from "react";
import { useLogout } from "../../../hooks/auth/useLogout";
import Modal from "../../atoms/modal/Modal";
import MyPageCharacter from "../../molecules/mypage/MyPageCharacter";
import MyPageUserInfo from "../../molecules/mypage/MyPageUserInfo";
import KakaoLogin from "../../atoms/auth/KakaoLoginButton";
import LineLogin from "../../atoms/auth/LineLoginButton";
import MyPageFriendList from "../../molecules/mypage/MyPageFriendList";
import { useNavigate } from "react-router-dom";
import { Page_Url } from "../../../router/Page_Url";
import instagramLogo from "../../../assets/snslogo/instagramLogo.svg";
import twitterLogo from "../../../assets/snslogo/twitterLogo.svg";

type ModalName =
  | "userInfo"
  | "langSelect"
  | "findFriend"
  | "characterList"
  | "friendList";

function MainPage() {
  const [isModalOpen, setModalOpen] = useState({
    userInfo: false,
    langSelect: false,
    findFriend: false,
    characterList: false,
    friendList: false,
  });
  const handleToggleModal = (modalName: ModalName) =>
    setModalOpen((prev) => ({ ...prev, [modalName]: !prev[modalName] }));
  const logout = useLogout();
  const isLoginCheck = localStorage.getItem("accessToken") ? true : false;
  const navigate = useNavigate();

  return (
    <div className={styles.mainContainer}>
      <LanguageSwitcher />
      <h1>Letter Monster</h1>
      <h2>내 캐릭터로 편지보내기!</h2>
      <div className={styles.characterDiv}>
        <img className={styles.character} src={lemon} alt="lettermon" />
      </div>
      {isLoginCheck ? (
        <>
          <div className={styles.mainMenuContainer}>
            <button onClick={() => handleToggleModal("userInfo")}>
              개인 정보
            </button>
            {isModalOpen.userInfo && (
              <Modal
                isOpen={isModalOpen.userInfo}
                onClose={() => handleToggleModal("userInfo")}
              >
                <MyPageUserInfo />
              </Modal>
            )}
            <button onClick={() => handleToggleModal("characterList")}>
              캐릭터 목록
            </button>
            {isModalOpen.characterList && (
              <Modal
                isOpen={isModalOpen.characterList}
                onClose={() => handleToggleModal("characterList")}
              >
                <MyPageCharacter />
              </Modal>
            )}
            <button onClick={() => handleToggleModal("friendList")}>
              내 친구 목록
            </button>
            {isModalOpen.friendList && (
              <Modal
                isOpen={isModalOpen.friendList}
                onClose={() => handleToggleModal("friendList")}
              >
                <MyPageFriendList />
              </Modal>
            )}
            {localStorage.getItem("refreshToken") ? (
              <button
                onClick={() => {
                  logout();
                  navigate(Page_Url.Main);
                }}
              >
                로그아웃
              </button>
            ) : (
              <button
                onClick={() => {
                  logout();
                  navigate(Page_Url.Login);
                }}
              >
                로그인
              </button>
            )}
          </div>
        </>
      ) : (
        <>
          <div className={styles.mainLoginButton}>
            <KakaoLogin />
            <LineLogin />
          </div>
        </>
      )}
      <div className={styles.socialMediaLinks}>
        <a
          href="https://www.instagram.com/lettermonster_official/"
          target="_blank"
          rel="noopener noreferrer"
        >
          <img
            className={styles.snsInstagram}
            src={instagramLogo}
            alt="Instagram"
          />
        </a>
        <a
          href="https://twitter.com/LetterMonster_"
          target="_blank"
          rel="noopener noreferrer"
        >
          <img className={styles.snsTwitter} src={twitterLogo} alt="Twitter" />
        </a>
      </div>
    </div>
  );
}

export default MainPage;
