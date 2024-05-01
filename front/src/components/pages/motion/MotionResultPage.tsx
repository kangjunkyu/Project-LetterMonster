import { useLocation, useNavigate } from "react-router-dom";
import DefaultButton from "../../atoms/button/DefaultButton";
import { Page_Url } from "../../../router/Page_Url";
import styles from "./MotionPage.module.scss";

function MotionResultPage() {
  const location = useLocation();
  const navigate = useNavigate();
  const { gif } = location.state || {};
  return (
    <>
      <div className={styles.motionResultContainer}>
        <div>모션 결과 페이지</div>
        <img
          className={styles.motionResultImage}
          src={gif}
          alt="Gif Result Preview"
        />
        <DefaultButton
          onClick={() => {
            navigate(Page_Url.WriteLetter);
          }}
        >
          편지 쓰기
        </DefaultButton>
      </div>
    </>
  );
}

export default MotionResultPage;
