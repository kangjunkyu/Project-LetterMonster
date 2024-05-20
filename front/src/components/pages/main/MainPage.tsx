import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useTranslation } from "react-i18next";

import styles from "./MainPage.module.scss";
import Instagram from "../../../assets/snslogo/instagramLogo.svg?react";
import X from "../../../assets/snslogo/xLogo.svg?react";
import Tiktok from "../../../assets/snslogo/tiktokLogo.svg?react";
import Person from "../../../assets/commonIcon/person.svg?react";
import Friend from "../../../assets/commonIcon/friends.svg?react";
import Logout from "../../../assets/commonIcon/logout.svg?react";
import Character from "../../../assets/commonIcon/character.svg?react";
import Report from "../../../assets/commonIcon/report.svg?react";
import Developer from "../../../assets/commonIcon/developer.svg?react";
import lemon from "../../../assets/characterSample/test_dab.gif";

import Modal from "../../atoms/modal/Modal";
import LineLogin from "../../atoms/auth/LineLoginButton";
import KakaoLogin from "../../atoms/auth/KakaoLoginButton";
import MyPageUserInfo from "../../molecules/mypage/MyPageUserInfo";
import MyPageCharacter from "../../molecules/mypage/MyPageCharacter";
import LanguageSwitcher from "../../molecules/language/LanguageSwitcher";
import MyPageFriendList from "../../molecules/mypage/MyPageFriendList";

import { Page_Url } from "../../../router/Page_Url";
import { useLogout } from "../../../hooks/auth/useLogout";
import useSuggestion from "../../../hooks/common/useSuggestion";
import useCheckTokenExpiration from "../../../hooks/auth/useCheckTokenExpiration";
// import { useGetUserNickname } from "../../../hooks/user/useGetUserNickName";

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
  const navigate = useNavigate();
  const { t } = useTranslation();
  const goToSuggestion = useSuggestion();
  const checkToken = useCheckTokenExpiration();
  // const { data } = useGetUserNickname();

  const isLoginCheck = checkToken(localStorage.getItem("accessToken"))
    ? true
    : false;

  return (
    <article className={styles.mainContainer}>
      <LanguageSwitcher />
      <h1>{t("main.title")}</h1>
      <h2>{t("main.introduce")}</h2>
      <div className={styles.characterDiv}>
        <img className={styles.character} src={lemon} alt="lettermon" />
      </div>
      {/* {data?.nickname && localStorage.getItem("accessToken") && (
        <h2>{`${data.nickname}${t("main.proposal")}`}</h2>
      )} */}
      {isLoginCheck ? (
        <div className={styles.mainMenuContainer}>
          <button onClick={() => handleToggleModal("userInfo")}>
            <Person width={24} height={24} />
            <p>{t("main.userInfo")}</p>
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
            <Character width={24} height={24} />
            <p>{t("main.characterList")}</p>
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
            <Friend width={24} height={24} />
            <p>{t("main.friends")}</p>
          </button>
          <button
            onClick={() =>
              navigate("sketchbook/f4c687f5-1a6f-4bb8-b130-0e6891551b53")
            }
          >
            <Developer width={24} height={24} />
            <p>{t("main.developer")}</p>
          </button>

          <button onClick={() => goToSuggestion()}>
            <Report width={24} height={24} />
            <p>{t("main.suggestion")}</p>
          </button>
          {isModalOpen.friendList && (
            <Modal
              isOpen={isModalOpen.friendList}
              onClose={() => handleToggleModal("friendList")}
            >
              <MyPageFriendList />
            </Modal>
          )}
          <button
            onClick={() => {
              logout();
              navigate(Page_Url.Main);
            }}
          >
            <Logout width={25} height={25} />
            <p>{t("nav.logout")}</p>
          </button>
        </div>
      ) : (
        <div className={styles.mainLoginButton}>
          <KakaoLogin />
          <LineLogin />
        </div>
      )}
      <nav className={styles.socialMediaLinks}>
        <a
          href="https://www.instagram.com/lettermonster_official/"
          target="_blank"
          rel="noopener noreferrer"
        >
          <Instagram width={30} height={30} />
        </a>
        <a
          href="https://twitter.com/LetterMonster_"
          target="_blank"
          rel="noopener noreferrer"
        >
          <X width={40} height={40} />
        </a>
        <a
          href="https://www.tiktok.com/@lettermonster_official"
          target="_blank"
          rel="noopener noreferrer"
        >
          <Tiktok width={35.72} height={35.72} />
        </a>
      </nav>
    </article>
  );
}

export default MainPage;
