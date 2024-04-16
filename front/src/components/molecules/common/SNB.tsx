import { Link } from "react-router-dom";
import { Page_Url } from "../../../router/Page_Url";
import "./SNB.scss";

function SNB() {
  return (
    <nav>
      <Link className="SNBButton" to={Page_Url.Test}>
        <img src="/red.png" alt="그리기" />
        그리기
      </Link>
      <Link className="SNBButton" to={Page_Url.Test}>
        <img src="/yellow.png" alt="스케치북" />
        스케치북
      </Link>
      <Link className="SNBButton" to={Page_Url.Test}>
        <img src="/green.png" alt="더보기" />
        더보기
      </Link>
    </nav>
  );
}

export default SNB;
