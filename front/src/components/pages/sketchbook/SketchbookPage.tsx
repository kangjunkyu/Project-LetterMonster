import { useNavigate, useParams } from "react-router-dom";
import styles from "./SketchbookPage.module.scss";
import useSketchbook, {
  useDeleteSketchbook,
  usePutSketchbook,
} from "../../../hooks/sketchbook/useSketchbook";
import DefaultButton from "../../atoms/button/DefaultButton";
import { useState } from "react";
import { Page_Url } from "../../../router/Page_Url";

function SketchbookPage() {
  const params = useParams() as { uuid: string };
  const { data } = useSketchbook(params.uuid);
  const putSketchbook = usePutSketchbook();
  const deleteSketchbook = useDeleteSketchbook();
  const [
    name,
    // setName
  ] = useState("임시수정");
  const navigate = useNavigate();

  return (
    <article className={styles.SketchbookContainer}>
      <DefaultButton
        onClick={() =>
          putSketchbook.mutate({
            sketchbookId: Number(data.data.id),
            name: name,
          })
        }
      >
        수정
      </DefalutButton>
      <DefalutButton
        onClick={() => deleteSketchbook.mutate(Number(data.data.id))}
      >
        삭제
      </DefalutButton>
      <DefalutButton
        onClick={() => {
          navigate(`${Page_Url.WriteLetterToSketchbook}${data.data.id}`);
        }}
      >
        편지쓰기
      </DefalutButton>
      {data && (
        <>
          <div>{data.data.name} 스케치북</div>
        </>
      )}
    </article>
  );
}

export default SketchbookPage;
