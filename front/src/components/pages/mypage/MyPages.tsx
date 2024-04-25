import styles from "./MyPages.module.scss";
import { useLogout } from "../../../hooks/auth/useLogout";
import MyPageUserInfo from "../../molecules/mypage/MyPageUserInfo";
import MyPageLangSelect from "../../molecules/mypage/MyPageLangSelect";
import Modal from "../../atoms/modal/Modal";
import { useState } from "react";

type ModalName = 'userInfo' | 'langSelect';

function MyPages() {
  const [isModalOpen, setModalOpen] = useState({
    userInfo: false,
    langSelect: false,
  });
  const handleToggleModal = (modalName: ModalName) =>
    setModalOpen((prev) => ({ ...prev, [modalName]: !prev[modalName] }));
  const logout = useLogout();

  return (
    <>
      <div className={styles.myPageContainer}>
        <div>
          <button onClick={() => logout()}>로그아웃</button>
        </div>

        <button onClick={() => handleToggleModal("userInfo")}>개인 정보</button>
        {isModalOpen.userInfo && (
          <Modal
            isOpen={isModalOpen.userInfo}
            onClose={() => handleToggleModal("userInfo")}
          >
            <MyPageUserInfo />
          </Modal>
        )}
        <button onClick={() => handleToggleModal("langSelect")}>
          언어 설정
        </button>
        {isModalOpen.langSelect && (
          <Modal
            isOpen={isModalOpen.langSelect}
            onClose={() => handleToggleModal("langSelect")}
          >
            <MyPageLangSelect />
          </Modal>
        )}
      </div>
    </>
  );
}

export default MyPages;
