import { useLogout } from "../../../hooks/auth/useLogout";
import MyPageUserInfo from "../../molecules/mypage/MyPageUserInfo";
import MyPageLangSelect from "../../molecules/mypage/MyPageLangSelect";
import Modal from "../../atoms/modal/Modal";
import { useState } from "react";

function MyPages() {
  const [isModalOpen, setModalOpen] = useState(false);
  const handleToggleModal = () => setModalOpen(!isModalOpen);
  const logout = useLogout();

  return (
    <>
      <div>
        <div>
          <button onClick={() => logout()}>로그아웃</button>
        </div>

        <button onClick={handleToggleModal}>개인 정보 & 언어 설정</button>
        {isModalOpen && (
          <Modal isOpen={isModalOpen} onClose={() => setModalOpen(false)}>
            <MyPageUserInfo />
            <MyPageLangSelect />
          </Modal>
        )}
      </div>
    </>
  );
}

export default MyPages;
