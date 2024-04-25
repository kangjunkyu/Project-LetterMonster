import { useState } from "react";
import { usePostNickname } from "../../../hooks/user/usePostNickname";
import styles from "./MyPageMolecules.module.scss";
import { useGetUserNickname } from "../../../hooks/user/useGetUserNickName";
import { useDeleteUser } from "../../../hooks/user/useDeleteUser";

function MyPageUserInfo() {
  const deleteUser = useDeleteUser();
  const changeNickname = usePostNickname();
  const [nickname, setNickname] = useState("");
  const { data: userInfo } = useGetUserNickname();

  const postNicknameMutation = () => changeNickname.mutate(nickname);
  return (
    <>
      <div className={styles.userInfoContainer}>
        <div className={styles.userInfoDetail}>
          <div>
            <div>닉네임</div>
            <div>닉네임 태그</div>
          </div>
          <div>
            {userInfo && <div>{userInfo.nickname}</div>}
            {userInfo && <div>{userInfo.nicknameTag}</div>}
          </div>
        </div>
        <input
          className={styles.userNicknameInput}
          type="text"
          onChange={(e) => {
            setNickname(e.target.value);
          }}
          placeholder="새로운 닉네임을 입력하세요"
        />
        <button onClick={() => postNicknameMutation()}>닉네임변경</button>
        <button onClick={() => deleteUser()}>회원탈퇴</button>
      </div>
    </>
  );
}

export default MyPageUserInfo;
