import { useState } from "react";
import {
  useDeleteFriend,
  useGetFriendGroupList,
} from "../../../hooks/friendGroup/useFriend";
import { useAlert } from "../../../hooks/notice/useAlert";
import styles from "./MyPageMolecules.module.scss";
import MyPageFindFriend from "./MyPageFindFriend";
import Modal from "../../atoms/modal/Modal";
import useFriendSketchbookList from "../../../hooks/sketchbook/useFriendSketchbookList";
import MyPageFriendSketchbook from "./MyPageFriendSketchbook";

interface MyFriendProps {
  id: number;
  nickname: string;
  nicknameTag: string;
  isFriend: boolean;
}

type ModalName = "findFriend" | "sketchbookList";

function MyPageFriendList() {
  const [isModalOpen, setModalOpen] = useState({
    findFriend: false,
    sketchbookList: false,
  });
  const [userId, setUserId] = useState(-1);
  const [userName, setUserName] = useState("");
  const { data: friendSketchbookList, isLoading } =
    useFriendSketchbookList(userId);
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
          <button className={styles.friendFindButton} onClick={() => handleToggleModal("findFriend")}>
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
        {isModalOpen.sketchbookList && (
          <Modal
            isOpen={isModalOpen.sketchbookList}
            onClose={() => handleToggleModal("sketchbookList")}
          >
            {!isLoading && friendSketchbookList && (
              <MyPageFriendSketchbook
                name={userName}
                list={friendSketchbookList?.data}
              />
            )}
          </Modal>
        )}
        <div className={styles.friendListAllContent}>
          {myFriend && myFriend[0]?.friendList?.length > 0 ? (
            myFriend[0]?.friendList?.map((friend: MyFriendProps) => (
              <div
                key={friend.id}
                className={styles.myFriendEachContent}
                onClick={() => {
                  setUserId(friend.id);
                  setUserName(friend.nickname);
                  handleToggleModal("sketchbookList");
                }}
              >
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
            ))
          ) : (
            <div className={styles.myFriendListNull}>
              현재 등록된 친구가 없습니다.
            </div>
          )}
        </div>
      </div>
    </>
  );
}

export default MyPageFriendList;
