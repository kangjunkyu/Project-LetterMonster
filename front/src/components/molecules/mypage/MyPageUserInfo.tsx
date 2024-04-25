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
        <div>
          <button onClick={() => deleteUser()}>회원탈퇴</button>
        </div>
        <div>
          {userInfo && <div>현재 닉네임 : {userInfo.nickname}</div>}
          {userInfo && <div>현재 닉네임태그 : {userInfo.nicknameTag}</div>}
          <input
            type="text"
            onChange={(e) => {
              setNickname(e.target.value);
            }}
          />
          <button onClick={() => postNicknameMutation()}>닉네임변경</button>
        </div>
      </div>
    </>
  );
}

export default MyPageUserInfo;
