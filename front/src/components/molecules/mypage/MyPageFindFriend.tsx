import { useEffect, useState } from "react";
import styles from "./MyPageMolecules.module.scss";
import { useSearchUserNickname } from "../../../hooks/friendGroup/useSearchUserNickname";
import { usePostFriend } from "../../../hooks/friendGroup/useGroup";
import { useAlert } from "../../../hooks/notice/useAlert";

interface FriendProps {
  id: number;
  nickname: string;
  nicknameTag: number;
}

function MyPageFindFriend() {
  const [nickname, setNickname] = useState("");
  const [searchTerm, setSearchTerm] = useState("");
  const [friendId, setFriendId] = useState(0);
  const { showAlert } = useAlert();
  const addFriend = usePostFriend();

  const addFriendMutation = () => {
    console.log(friendId);
    addFriend.mutate(friendId, {
      onSuccess: () => {
        showAlert("친구를 추가했어요!");
      },
      onError: (err: any) => {
        if (err.response.data.status == 400) {
          showAlert("다시 추가해주세요.");
        } else {
          showAlert("다시 추가해주세요.");
        }
      },
    });
  };

  const {
    data: friendList,
    isLoading,
    error,
  } = useSearchUserNickname(searchTerm, {
    enabled: searchTerm.trim().length > 0,
  });

  useEffect(() => {
    const timeoutId = setTimeout(() => {
      setSearchTerm(nickname);
    }, 400);

    return () => clearTimeout(timeoutId);
  }, [nickname]);

  // console.log(friendList);

  return (
    <>
      <div className={styles.findFriendContainer}>
        <div>친구 찾기</div>
        <div className={styles.findFriendInput}>
          <input
            type="text"
            placeholder="닉네임을 입력해주세요."
            value={nickname}
            onChange={(e) => setNickname(e.target.value)}
          />
          <button onClick={() => useSearchUserNickname}>검색</button>
        </div>
        <div className={styles.findFriendDivider}></div>
        <div className={styles.findFriendColumn}>
          <p>닉네임</p>
          <p>태그</p>
        </div>
        <div className={styles.findFriendResult}>
          {isLoading ? (
            <p>Loading...</p>
          ) : error ? (
            <p>Error: {error.message}</p>
          ) : (
            friendList &&
            friendList.map((friend: FriendProps) => (
              <div className={styles.findFriendResultContent} key={friend.id}>
                <div>{friend.nickname}</div>
                <div>{friend.nicknameTag}</div>
                <button
                  onClick={() => {
                    setFriendId(friend.id);
                    addFriendMutation();
                  }}
                >
                  추가
                </button>
              </div>
            ))
          )}
        </div>
      </div>
    </>
  );
}

export default MyPageFindFriend;