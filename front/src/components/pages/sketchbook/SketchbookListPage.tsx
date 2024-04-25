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

function SketchbookListPage() {
  const { data, isLoading } = useSketchbookList();
  const [data2, setData2] = useState("");
  const createSketchbook = useCreateSketchbook();

  const createHandler = (name: string) => {
    {
      name && createSketchbook.mutate(name);
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
    <>
      <article className={styles.sketchbookListContainer}>
        <LNB>
          <h1>스케치북 리스트</h1>
          <LNBButton
            onClick={() => createHandler(data2)}
            onKeyDown={(e: React.KeyboardEvent<HTMLButtonElement>) =>
              inputEnter(e)
            }
          >
            만들기
          </LNBButton>
        </LNB>
        <input
          type="text"
          onChange={(e) => {
            setData2(e.target.value);
          }}
        />
        <DefaultButton onClick={() => createHandler(data2)}>
          만들기
        </DefaultButton>
        <SketchbookList>
          {!isLoading && data?.data && data?.data?.length > 0 ? (
            renderListItems(data.data)
          ) : (
            <div>비었어요.</div>
          )}
        </SketchbookList>
      </article>
    </>
  );
}

export default SketchbookListPage;
