import { useNavigate } from "react-router-dom";
import { Page_Url } from "../../../router/Page_Url";
import styles from "./MyPageMolecules.module.scss";
import { useTranslation } from "react-i18next";

interface Props {
  list: IItem[];
  name: string;
}

type IItem = {
  id: number;
  isPublic: boolean;
  shareLink: string;
  name: string;
  holder: {
    id: number;
    isLanguageSet: string;
    nickname: string;
    nicknameTag: number;
  };

  uuid: string;
  tag: number;
  isWritePossible: boolean;
};

function MyPageFriendSketchbook({ name, list }: Props) {
  const navigate = useNavigate();
  const { t } = useTranslation();

  return (
    <article className={styles.friendSketchbooksList}>
      <span>{name}</span>
      <p>{t("mypage.sketchbookList")}</p>
      {list?.length ? (
        list?.map(
          (item: IItem, i: number) =>
            item?.isPublic && (
              <figure
                key={i}
                className={styles.friendSketchbooksListItem}
                onClick={() => {
                  navigate(`${Page_Url.Sketchbook}${item.uuid}`);
                }}
              >
                <div>{item?.name}</div>
                <div>{item?.tag}</div>
              </figure>
            )
        )
      ) : (
        <div className={styles.noFriendSketchbook}>
          친구의 스케치북이 없어요
        </div>
      )}
    </article>
  );
}

export default MyPageFriendSketchbook;
