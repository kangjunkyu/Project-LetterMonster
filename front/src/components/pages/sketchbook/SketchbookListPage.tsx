import styles from "./SketchbookListPage.module.scss";
import SketchbookList from "../../molecules/sketchbook/SketchbookList";
import SketchbookListItem from "../../atoms/sketchbook/SketchbookListItem";
import useSketchbookList, {
  useCreateSketchbook,
} from "../../../hooks/sketchbook/useSketchbookList";
import { useState } from "react";
import DefalutButton from "../../atoms/button/DefalutButton";
import { Link } from "react-router-dom";
import { Page_Url } from "../../../router/Page_Url";

interface IItem {
  id: string;
  isPublic: boolean;
  shareLink: string;
  name: string;
}

function SketchbookListPage() {
  const { data, isLoading, isError } = useSketchbookList();
  const [data2, setData2] = useState("");
  const createSketchbook = useCreateSketchbook();

  const createHandler = (name: string) => {
    {
      name && createSketchbook.mutate(name);
    }
  };

  const renderListItems = (items: [IItem]) => {
    return items.map((item: IItem) => (
      <>
        <Link to={`${Page_Url.Sketchbook}${item.id}`}>
          <SketchbookListItem key={item.id} item={item} />
        </Link>
      </>
    ));
  };

  return (
    <article className={styles.sketchbookListContainer}>
      <h1>스케치북 리스트</h1>
      <input
        type="text"
        onChange={(e) => {
          setData2(e.target.value);
        }}
      />
      <button onClick={() => createHandler(data2)}>생성</button>
      <SketchbookList>
        {!isLoading && data?.data && data?.data?.length > 0 ? (
          renderListItems(data.data)
        ) : (
          <div>비었어요.</div>
        )}
      </SketchbookList>
    </article>
  );
}

export default SketchbookListPage;
