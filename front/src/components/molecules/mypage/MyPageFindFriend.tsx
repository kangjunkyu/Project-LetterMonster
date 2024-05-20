import { useEffect, useState } from "react";
import styles from "./MyPageMolecules.module.scss";
import { useSearchUserNickname } from "../../../hooks/friendGroup/useSearchUserNickname";
import {
  useDeleteFriend,
  usePostFriend,
} from "../../../hooks/friendGroup/useFriend";
import { useAlert } from "../../../hooks/notice/useAlert";
import { useTranslation } from "react-i18next";

interface FriendProps {
  userId: number;
  nickname: string;
  nicknameTag: number;
  isFriend: boolean;
}

function MyPageFindFriend() {
  const { t } = useTranslation();
  const [nickname, setNickname] = useState("");
  const [searchTerm, setSearchTerm] = useState("");
  const { showAlert } = useAlert();
  const addFriend = usePostFriend();
  const deleteFriend = useDeleteFriend();

  const deleteFriendMutation = (friendId: number) => {
    deleteFriend.mutate(friendId, {
      onSuccess: () => {
        showAlert(t("notification.deleteFriend"));
      },
      onError: (err: any) => {
        if (err.response.data.status == 400) {
          showAlert(t("notification.retry"));
        } else {
          showAlert(t("notification.retry"));
        }
      },
    });
  };

  const addFriendMutation = (friendId: number) => {
    addFriend.mutate(friendId, {
      onSuccess: () => {
        showAlert(t("notification.weareFriend"));
      },
      onError: (err: any) => {
        if (err.response.data.status == 400) {
          showAlert(t("notification.retry"));
        } else {
          showAlert(t("notification.retry"));
        }
      },
    });
  };

  const {
    data: friendList,
    isLoading,
    error,
  } = useSearchUserNickname(searchTerm, {
    enabled: searchTerm.trim()?.length > 0,
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
        <div>{t("mypage.findfriend")}</div>
        <div className={styles.findFriendInput}>
          <input
            type="text"
            placeholder={t("mypage.friendSearch")}
            value={nickname}
            onChange={(e) => setNickname(e.target.value)}
          />
          <button className={styles.searchUserButton} onClick={() => useSearchUserNickname}>검색</button>
        </div>
        <div className={styles.findFriendDivider}></div>
        <div className={styles.findFriendColumn}>
          <p>{t("mypage.nickname")}</p>
          <p>{t("mypage.nicknameTag")}</p>
        </div>
        <div className={styles.findFriendResult}>
          {isLoading ? (
            <p>Loading...</p>
          ) : error ? (
            <p>Error: {error.message}</p>
          ) : (
            friendList &&
            friendList.map((friend: FriendProps) => (
              <div
                className={styles.findFriendResultContent}
                key={friend.userId}
              >
                <div>{friend.nickname}</div>
                <div>{friend.nicknameTag}</div>
                {friend.isFriend ? (
                  <button
                    className={styles.deleteFriendButton}
                    onClick={() => {
                      deleteFriendMutation(friend.userId);
                    }}
                  >
                    {t("mypage.characterDelete")}
                  </button>
                ) : (
                  <button
                    className={styles.addFriendButton}
                    onClick={() => {
                      addFriendMutation(friend.userId);
                    }}
                  >
                    {t("mypage.wearefriend")}
                  </button>
                )}
              </div>
            ))
          )}
        </div>
      </div>
    </>
  );
}

export default MyPageFindFriend;
