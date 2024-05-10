import { useCallback, useState } from "react";
import { usePostNickname } from "../../../hooks/user/usePostNickname";
import styles from "./MyPageMolecules.module.scss";
import { useGetUserNickname } from "../../../hooks/user/useGetUserNickName";
import { useDeleteUser } from "../../../hooks/user/useDeleteUser";
import { useAlert } from "../../../hooks/notice/useAlert";

function MyPageUserInfo() {
  const deleteUser = useDeleteUser();
  const { showAlert } = useAlert();
  const changeNickname = usePostNickname();
  const [nickname, setNickname] = useState("");
  const { data: userInfo } = useGetUserNickname();

  const handleUserNicknameChange = useCallback(
    (event: React.ChangeEvent<HTMLInputElement>) => {
      const newNickname = event.target.value;
      if (newNickname.startsWith(" ")) {
        showAlert("첫 글자로 띄어쓰기를 사용할 수 없습니다.");
      } else if (
        /[^a-zA-Z0-9ㄱ-힣ㆍᆞᆢ\s]/.test(newNickname) ||
        newNickname.includes("　")
      ) {
        showAlert("닉네임은 영문, 숫자, 한글만 가능합니다.");
      } else if (newNickname.length > 10) {
        showAlert("닉네임은 10글자 이하만 가능합니다.");
      } else {
        setNickname(newNickname);
      }
    },
    []
  );

  const postNicknameMutation = () => {
    changeNickname.mutate(nickname, {
      onSuccess: () => {
        showAlert("닉네임 변경에 성공했어요!");
      },
      onError: (err: any) => {
        if (err.response.data.status == 400) {
          showAlert("욕설이 포함된 닉네임은 사용할 수 없어요.");
        } else {
          showAlert("다시 시도해주세요.");
        }
      },
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
          value={nickname}
          className={styles.userNicknameInput}
          type="text"
          onChange={handleUserNicknameChange}
          placeholder="새로운 닉네임을 입력하세요"
        />
        <button onClick={() => postNicknameMutation()}>닉네임변경</button>
        <button onClick={() => deleteUser()}>회원탈퇴</button>
      </div>
    </>
  );
}

export default MyPageUserInfo;
