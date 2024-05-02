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
        showAlert(`${name} 스케치북이 생겼어요`);
        handleToggleModal("sketchbookCreate");
      } else {
        showAlert(`스케치북 이름을 정해주세요`);
      }
    }
  };

  const inputEnter = (e: React.KeyboardEvent<HTMLButtonElement>) => {
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
      />
    ));
  };

  return (
  <article className={styles.centerContainer}>
      {/* <LNB>
        <h1>스케치북 리스트</h1>
        <LNBButton onClick={() => handleToggleModal("sketchbookCreate")}>
          만들기
        </LNBButton>
      </LNB> */}
      <article className={styles.sketchbookListContainer}>
      <Modal
        isOpen={isModalOpen.sketchbookCreate}
        onClose={() => handleToggleModal("sketchbookCreate")}
      >
        <div className={styles.createSketchbookBox}>
          <div>스케치북 만들기</div>
          <input
            type="text"
            onChange={(e) => {
              setData2(e.target.value);
            }}
            placeholder="스케치북 이름"
          />
          <DefaultButton
            onClick={() => createHandler(data2)}
            onKeyDown={(e: React.KeyboardEvent<HTMLButtonElement>) =>
              inputEnter(e)
            }
          >
            만들기
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
