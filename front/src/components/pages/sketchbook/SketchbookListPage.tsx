import styles from "./SketchbookListPage.module.scss";
import SketchbookList from "../../molecules/sketchbook/SketchbookList";
import SketchbookListItem from "../../atoms/sketchbook/SketchbookListItem";
import useSketchbookList, {
  useCreateSketchbook,
} from "../../../hooks/sketchbook/useSketchbookList";
import { useState } from "react";
import DefaultButton from "../../atoms/button/DefaultButton";
import { Page_Url } from "../../../router/Page_Url";
import LNB from "../../molecules/common/LNB";
import LNBButton from "../../atoms/button/LNBButton";
import { useAlert } from "../../../hooks/notice/useAlert";
import Modal from "../../atoms/modal/Modal";
import AddButton from "../../atoms/button/AddButton";
import { useTranslation } from "react-i18next";
import useFavoriteSketchbook from "../../../hooks/sketchbook/useFavorite";
import useSearchSketchbook from "../../../hooks/sketchbook/useSearchSketchbook";
import useFriendSketchbookList from "../../../hooks/sketchbook/useFriendSketchbookList";
import { useGetFriendGroupList } from "../../../hooks/friendGroup/useFriend";
import MyPageFriendSketchbook from "../../molecules/mypage/MyPageFriendSketchbook";
import useCheckTokenExpiration from "../../../hooks/auth/useCheckTokenExpiration";
import LoginPage from "../login/LoginPage";

interface IItem {
  id: string;
  isPublic: boolean;
  shareLink: string;
  name: string;
  holder: {
    nickname: string;
    nicknameTag: number;
  };
  uuid: string;
  tag: number;
  isWritePossible: boolean;
}

type ModalName = "sketchbookCreate";

function SketchbookListPage() {
  const { t } = useTranslation();
  const { data, isLoading } = useSketchbookList();
  const { data: favorite, isLoading: favoriteLodaing } =
    useFavoriteSketchbook();
  const [data2, setData2] = useState("");
  const [toggle, setToggle] = useState(true);
  const [searchKeyword, setSearchKeyword] = useState("");
  const createSketchbook = useCreateSketchbook();
  const { showAlert } = useAlert();
  const [userId, setUserId] = useState(-1);
  const [userName, setUserName] = useState("");
  const [isModalOpen, setModalOpen] = useState({
    sketchbookCreate: false,
  });
  const { data: searchResult, isLoading: searchResultLoding } =
    useSearchSketchbook(searchKeyword);
  const { data: myFriend } = useGetFriendGroupList();
  const handleToggleModal = (modalName: ModalName) =>
    setModalOpen((prev) => ({ ...prev, [modalName]: !prev[modalName] }));
  const { data: friendSketchbookList } = useFriendSketchbookList(userId);
  const checkToken = useCheckTokenExpiration();
  const createHandler = (name: string) => {
    {
      if (name.startsWith(" ")) {
        showAlert("첫 글자로 띄어쓰기를 사용할 수 없습니다.");
      }
      // else if (/[^a-zA-Z0-9ㄱ-힣ㆍᆞᆢ\s]/.test(name) || name.includes("　")) {
      //   showAlert("스케치북 이름은 영문, 숫자, 한글만 가능합니다.");
      // }
      else if (name.length > 10) {
        showAlert("스케치북 이름은 10글자 이하만 가능합니다.");
      } else if (name) {
        createSketchbook.mutate(name);
        showAlert(`${name} ${t("sketchbookList.create")}`);
        handleToggleModal("sketchbookCreate");
      } else {
        showAlert(`${t("sketchbookList.namePlease")}`);
      }
    }
  };

  const inputEnter = (
    e:
      | React.KeyboardEvent<HTMLButtonElement>
      | React.KeyboardEvent<HTMLInputElement>
  ) => {
    if (e.key === "Enter") {
      createHandler(data2);
    }
  };

  const renderListItems = (items: [IItem]) => {
    return items.map((item: IItem) => (
      <SketchbookListItem
        key={item.id}
        item={item}
        url={`${Page_Url.Sketchbook}${item.uuid}`}
        index={Number(item.id)}
      />
    ));
  };

  return (
    <article className={styles.centerContainer}>
      <LNB>
        <h1>{t("sketchbookList.title")}</h1>
        {checkToken(localStorage.getItem("accessToken")) ? (
          <LNBButton onClick={() => handleToggleModal("sketchbookCreate")}>
            {t("sketchbookList.generate")}
          </LNBButton>
        ) : (
          <div></div>
        )}
      </LNB>
      <article className={styles.sketchbookListContainer}>
        <Modal
          isOpen={isModalOpen.sketchbookCreate}
          onClose={() => handleToggleModal("sketchbookCreate")}
        >
          <div className={styles.createSketchbookBox}>
            <div>{t("sketchbookList.generateModal")}</div>
            <input
              type="text"
              autoFocus
              onChange={(e) => {
                setData2(e.target.value);
              }}
              onKeyDown={(e: React.KeyboardEvent<HTMLInputElement>) =>
                inputEnter(e)
              }
              placeholder={t("sketchbookList.name")}
            />
            <DefaultButton
              onClick={() => createHandler(data2)}
              onKeyDown={(e: React.KeyboardEvent<HTMLButtonElement>) =>
                inputEnter(e)
              }
            >
              {t("sketchbookList.generate")}
            </DefaultButton>
          </div>
        </Modal>
        <div className={styles.buttonBox}>
          <button
            className={toggle ? styles.select : ""}
            onClick={() => setToggle(true)}
          >
            {t("sketchbookList.Explore")}
          </button>
          <button
            className={!toggle ? styles.select : ""}
            onClick={() => setToggle(false)}
          >
            {t("sketchbookList.My")}
          </button>
        </div>
        <AddButton onClick={() => handleToggleModal("sketchbookCreate")} />
        {toggle ? (
          <>
            <input
              type="text"
              className={`${styles.searchBox} `}
              placeholder={t("writeletter.sketchbookSearch")}
              value={searchKeyword}
              onChange={(e) => {
                setSearchKeyword(e.target.value);
              }}
            />
            {searchResult?.data && (
              <SketchbookList title={t("writeletter.searchResult")}>
                {!searchResultLoding &&
                searchResult?.data &&
                searchResult?.data?.length > 0
                  ? renderListItems(searchResult.data)
                  : ""}
              </SketchbookList>
            )}
            {myFriend?.[0] && (
              <nav className={styles.friendNav}>
                {myFriend[0]?.friendList?.map(
                  (item: {
                    id: number;
                    nickname: string;
                    nicknameTag: number;
                  }) => (
                    <button
                      key={item.id}
                      onClick={() => {
                        setUserName(item.nickname);
                        setUserId(item?.id);
                      }}
                    >
                      {item?.nickname}
                    </button>
                  )
                )}
              </nav>
            )}
            <MyPageFriendSketchbook
              name={userName}
              list={friendSketchbookList?.data}
            />
          </>
        ) : checkToken(localStorage.getItem("accessToken")) ? (
          <>
            <SketchbookList title={t("sketchbookList.favoriteList")}>
              {!favoriteLodaing &&
              favorite?.data &&
              favorite?.data?.length > 0 ? (
                renderListItems(favorite.data)
              ) : (
                <div>{t("sketchbookList.empty")}</div>
              )}
            </SketchbookList>
            <SketchbookList title={t("sketchbookList.My")}>
              {!isLoading && data?.data && data?.data?.length > 0 ? (
                renderListItems(data.data)
              ) : (
                <div>{t("sketchbookList.empty")}</div>
              )}
            </SketchbookList>
          </>
        ) : (
          <>
            <h1>{t("sketchbookList.")}</h1>
            <LoginPage />
          </>
        )}
      </article>
    </article>
  );
}

export default SketchbookListPage;
