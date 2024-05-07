import { useState } from "react";
import {
  useDeleteFriend,
  useGetFriendGroupList,
} from "../../../hooks/friendGroup/useFriend";
import { useAlert } from "../../../hooks/notice/useAlert";
import styles from "./MyPageMolecules.module.scss";
import MyPageFindFriend from "./MyPageFindFriend";
import Modal from "../../atoms/modal/Modal";

interface MyFriendProps {
  id: number;
  nickname: string;
  nicknameTag: string;
  isFriend: boolean;
}

type ModalName = "findFriend";

function MyPageFriendList() {
  const [isModalOpen, setModalOpen] = useState({
    findFriend: false,
  });

  const { showAlert } = useAlert();
  const deleteFriend = useDeleteFriend();
  const { data: myFriend } = useGetFriendGroupList();

  const handleToggleModal = (modalName: ModalName) =>
    setModalOpen((prev) => ({ ...prev, [modalName]: !prev[modalName] }));

  const deleteFriendMutation = (friendId: number) => {
    deleteFriend.mutate(friendId, {
      onSuccess: () => {
        showAlert("친구를 삭제했어요!");
      },
      onError: (err: any) => {
        if (err.response.data.status == 400) {
          showAlert("다시 삭제해주세요.");
        } else {
          showAlert("다시 삭제해주세요.");
        }
      },
    });
  };

  return (
    <>
      <div className={styles.friendListContainer}>
        <div className={styles.friendListUpper}>
          <div>내 친구 목록</div>
          <button onClick={() => handleToggleModal("findFriend")}>
            친구 찾기
          </button>
          {isModalOpen.findFriend && (
            <Modal
              isOpen={isModalOpen.findFriend}
              onClose={() => handleToggleModal("findFriend")}
            >
              <MyPageFindFriend />
            </Modal>
          )}
        </div>
        <div className={styles.friendListAllContent}>
          {myFriend &&
            myFriend[0].friendList.map((friend: MyFriendProps) => (
              <div key={friend.id} className={styles.myFriendEachContent}>
                <div>{friend.nickname}</div>
                <div>{friend.nicknameTag}</div>
                <button
                  className={styles.deleteFriendButton}
                  onClick={() => {
                    deleteFriendMutation(friend.id);
                  }}
                >
                  삭제
                </button>
              </div>
            ))}
        </div>
      </div>
    </>
  );
}

export default MyPageFriendList;
