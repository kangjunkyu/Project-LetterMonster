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
import DefaultButton from "../../atoms/button/DefaultButton";
import { useTranslation } from "react-i18next";

interface MyFriendProps {
  id: number;
  nickname: string;
  nicknameTag: string;
  isFriend: boolean;
}

type ModalName = "findFriend" | "sketchbookList" | "deleteAlert";

function MyPageFriendList() {
  const { t } = useTranslation();
  const [isModalOpen, setModalOpen] = useState({
    findFriend: false,
    sketchbookList: false,
    deleteAlert: false,
  });
  const [userId, setUserId] = useState(-1);
  const [userName, setUserName] = useState("");
  const [friendToDelete, setFriendToDelete] = useState<MyFriendProps | null>(
    null
  );
  const { data: friendSketchbookList, isLoading } =
    useFriendSketchbookList(userId);
  const { showAlert } = useAlert();
  const deleteFriend = useDeleteFriend();
  const { data: myFriend } = useGetFriendGroupList();

  const handleToggleModal = (modalName: ModalName, friend?: MyFriendProps) => {
    setModalOpen((prev) => ({ ...prev, [modalName]: !prev[modalName] }));
    if (modalName === "deleteAlert" && friend) {
      setFriendToDelete(friend);
    }
  };

  const deleteFriendMutation = (friendId: number) => {
    deleteFriend.mutate(friendId, {
      onSuccess: () => {
        showAlert(t("friendList.completeDeleteFriend"));
      },
      onError: (err: any) => {
        if (err.response.data.status == 400) {
          showAlert(t("friendList.reDelete"));
        } else {
          showAlert(t("friendList.reDelete"));
        }
      },
    });
  };

  return (
    <>
      <div className={styles.friendListContainer}>
        <div className={styles.friendListUpper}>
          <div>{t("friendList.myFriendList")}</div>
          <button
            className={styles.friendFindButton}
            onClick={() => handleToggleModal("findFriend")}
          >
            {t("friendList.findFriend")}
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
                  // onClick={() => {
                  //   deleteFriendMutation(friend.id);
                  // }}
                  onClick={() => handleToggleModal("deleteAlert", friend)}
                >
                  {t("friendList.delete")}
                </button>
              </div>
            ))
          ) : (
            <div className={styles.myFriendListNull}>
              {t("friendList.noFriend")}
            </div>
          )}
        </div>
      </div>
      {isModalOpen.deleteAlert && friendToDelete && (
        <Modal
          isOpen={isModalOpen.deleteAlert}
          onClose={() => handleToggleModal("deleteAlert")}
        >
          <div className={styles.buttonBox}>
            {t("friendList.check")}
            <DefaultButton
              onClick={() => deleteFriendMutation(friendToDelete.id)}
            >
              {t("friendList.deleteFriend")}
            </DefaultButton>
            <DefaultButton
              onClick={() => {
                handleToggleModal("deleteAlert");
              }}
            >
              {t("sketchbook.cancel")}
            </DefaultButton>
          </div>
        </Modal>
      )}
    </>
  );
}

export default MyPageFriendList;
