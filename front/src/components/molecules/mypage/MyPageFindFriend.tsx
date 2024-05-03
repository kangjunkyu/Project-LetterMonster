import { useEffect, useState } from "react";
import styles from "./MyPageMolecules.module.scss";
import { useSearchUserNickname } from "../../../hooks/friend/useSearchUserNickname";

interface FriendProps {
  id: number;
  nickname: string;
  nicknameTag: number;
}

function MyPageFindFriend() {
  const [nickname, setNickname] = useState("");
  const [searchTerm, setSearchTerm] = useState("");

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
          <p>닉네임 태그</p>
        </div>
        <div className={styles.findFriendResult}>
          <div>
            {isLoading ? (
              <p>Loading...</p>
            ) : error ? (
              <p>Error: {error.message}</p>
            ) : (
              friendList &&
              friendList.map((friend: FriendProps) => (
                <div key={friend.id}>{friend.nickname}</div>
              ))
            )}
          </div>
          <div>
            {friendList &&
              friendList.map((friend: FriendProps) => (
                <div key={friend.id}>{friend.nicknameTag}</div>
              ))}
          </div>
        </div>
      </div>
    </>
  );
}

export default MyPageFindFriend;
