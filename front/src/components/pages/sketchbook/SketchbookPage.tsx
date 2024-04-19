import { useParams } from "react-router-dom";
import styles from "./SketchbookPage.module.scss";
import useSketchbook, {
  useDeleteSketchbook,
  usePutSketchbook,
} from "../../../hooks/sketchbook/useSketchbook";
import DefalutButton from "../../atoms/button/DefalutButton";
import { useState } from "react";

function SketchbookPage() {
  const sketchbookId = useParams() as { sketchbookId: string };
  const { data } = useSketchbook(Number(sketchbookId.sketchbookId));
  const putSketchbook = usePutSketchbook();
  const deleteSketchbook = useDeleteSketchbook();
  const [name, setName] = useState("임시수정");
  return (
    <article className={styles.SketchbookContainer}>
      <DefalutButton
        onClick={() =>
          putSketchbook.mutate({
            sketchbookId: Number(sketchbookId.sketchbookId),
            name: name,
          })
        }
      >
        수정
      </DefalutButton>
      <DefalutButton
        onClick={() =>
          deleteSketchbook.mutate(Number(sketchbookId.sketchbookId))
        }
      >
        삭제
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
