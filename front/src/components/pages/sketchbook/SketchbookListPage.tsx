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
  const [data2, setData2] = useState("");
  const createSketchbook = useCreateSketchbook();
  const { showAlert } = useAlert();

  const [isModalOpen, setModalOpen] = useState({
    sketchbookCreate: false,
  });

  const handleToggleModal = (modalName: ModalName) =>
    setModalOpen((prev) => ({ ...prev, [modalName]: !prev[modalName] }));

  const createHandler = (name: string) => {
    {
      if (name) {
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
        <LNBButton onClick={() => handleToggleModal("sketchbookCreate")}>
          {t("sketchbookList.generate")}
        </LNBButton>
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
        <AddButton onClick={() => handleToggleModal("sketchbookCreate")} />
        <SketchbookList>
          {!isLoading && data?.data && data?.data?.length > 0 ? (
            renderListItems(data.data)
          ) : (
            <div>비었어요.</div>
          )}
        </SketchbookList>
      </article>
    </article>
  );
}

export default SketchbookListPage;
