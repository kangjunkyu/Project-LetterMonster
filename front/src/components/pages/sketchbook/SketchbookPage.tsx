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
    <article className={styles.sketchbookContainer}>
      <div className={styles.buttonBox}>
        <DefaultButton
          onClick={() =>
            putSketchbook.mutate({
              sketchbookId: Number(data?.data?.id),
              name: name,
            })
          }
        >
          수정
        </DefaultButton>
        <DefaultButton
          onClick={() => deleteSketchbook.mutate(Number(data?.data?.id))}
        >
          삭제
        </DefaultButton>
        <DefaultButton
          onClick={() => {
            navigate(`${Page_Url.WriteLetterToSketchbook}${data?.data?.id}`);
          }}
        >
          편지쓰기
        </DefaultButton>
      </div>
      {data && (
        <figure className={styles.sketchbook}>
          <div>{data?.data?.name} 스케치북</div>
          <div>{data?.data?.sketchbookCharacterMotionList[1]?.id} 스케치북</div>
          <div>
            {
              data?.data?.sketchbookCharacterMotionList[1]?.letterList[0]
                ?.sender.nickname
            }
            님의 편지
          </div>
          <div>
            {
              data?.data?.sketchbookCharacterMotionList[1]?.letterList[0]
                ?.content
            }
          </div>
          <div>
            {
              data?.data?.sketchbookCharacterMotionList[1]?.letterList[0]
                ?.write_time
            }
          </div>
          <img
            src={
              data?.data?.sketchbookCharacterMotionList[1]?.characterMotion
                ?.imageUrl
            }
            alt=""
          />
          <div>
            {
              data?.data?.sketchbookCharacterMotionList[1]?.characterMotion
                ?.nickname
            }
          </div>
        </figure>
      )}
    </article>
  );
}

export default SketchbookPage;
