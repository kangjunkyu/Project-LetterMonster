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
import { useTranslation } from "react-i18next";

interface MyFriendProps {
  id: number;
  nickname: string;
  nicknameTag: string;
  isFriend: boolean;
}

type ModalName = "findFriend" | "sketchbookList";

function MyPageFriendList() {
  const { t } = useTranslation();
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

  return (
    <>
      <div className={styles.friendListContainer}>
        <div className={styles.friendListUpper}>
          <div>{t("mypage.friendList")}</div>
          <button onClick={() => handleToggleModal("findFriend")}>
            {t("mypage.findfriend")}
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
                  {t("mypage.characterDelete")}
                </button>
              </div>
            ))
          ) : (
            <div className={styles.myFriendListNull}>
              {t("mypage.nofriend")}
            </div>
          )}
        </div>
      </div>
    </>
  );
}

export default MyPageFriendList;
