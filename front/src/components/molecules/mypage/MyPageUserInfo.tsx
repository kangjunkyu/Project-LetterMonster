import { useState } from "react";
import { usePostNickname } from "../../../hooks/user/usePostNickname";
import styles from "./MyPageMolecules.module.scss";
import { useGetUserNickname } from "../../../hooks/user/useGetUserNickName";
import { useDeleteUser } from "../../../hooks/user/useDeleteUser";
import {useAlert} from "../../../hooks/notice/useAlert";

function MyPageUserInfo() {
  const deleteUser = useDeleteUser();
  const { showAlert } = useAlert();
  const changeNickname = usePostNickname();
  const [nickname, setNickname] = useState("");
  const { data: userInfo } = useGetUserNickname();

  const postNicknameMutation = () => {
    changeNickname.mutate(nickname, {
      onSuccess: () => {
        showAlert("닉네임 변경에 성공했어요!");
      },
      onError: (error) => {
        if (error.response && error.response.status === 400) {
          showAlert("욕설이 포함된 닉네임은 사용할 수 없어요.");
        } else {
          showAlert("닉네임 변경에 실패했어요. 다시 시도해주세요.");
        }
      }
    });
  };
  return (
    <>
      <div className={styles.userInfoContainer}>
        <div className={styles.userInfoDetail}>
          <div>
            <div>닉네임</div>
            <div>닉네임 태그</div>
          </div>
          <div className={styles.userInfoReal}>
            {userInfo && <div>{userInfo?.nickname}</div>}
            {userInfo && <div>{userInfo?.nicknameTag}</div>}
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
