import { useLocation, useNavigate } from "react-router-dom";
import styles from "./MotionPreview.module.scss";
import DefaultButton from "../../atoms/button/DefaultButton";
import { Page_Url } from "../../../router/Page_Url";

function MotionPreview() {
  const navigate = useNavigate();
  const location = useLocation();
  const { image } = location.state || {};

  return (
    <>
      <div className={styles.motionPreviewContainer}>
        <div className={styles.motionImage}>
          {image && (
            <img className={styles.motionImage} src={image} alt="Uploaded" />
          )}
        </div>
        <div className={styles.motionPreviewButtons}>
          <DefaultButton onClick={() => navigate(Page_Url.WriteLetter)}>
            편지쓰러가기
          </DefaultButton>
          <DefaultButton onClick={() => navigate(Page_Url.SketchbookList)}>
            취소
          </DefaultButton>
        </div>
      </div>
    </>
  );
}

export default MotionPreview;
