import { useGetFriendGroupList } from "../../../hooks/friendGroup/useGroup";
import styles from "./MyPageMolecules.module.scss";

function MyPageFriendList() {
  const { data: myFriend } = useGetFriendGroupList();

  console.log(myFriend);

  return (
    <>
      <div className={styles.friendListContainer}>내 친구 목록</div>
    </>
  );
}

export default MyPageFriendList;
