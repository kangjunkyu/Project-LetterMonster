import { useNavigate } from "react-router-dom";
import { Page_Url } from "../../../router/Page_Url";
import "./MainPage.scss";

function MainPage() {
  const navigate = useNavigate();

  return (
    <main className="container">
      <div>Letter Monster / 레터몬스터</div>
      <div className="characterDiv">
        <img src="/src/assets/characterSample/gom.gif" alt="" />
        <img src="/src/assets/characterSample/hojin_character.gif" alt="" />
        <img src="/src/assets/characterSample/egypt.gif" alt="" />
        <img src="/src/assets/characterSample/rabbit.gif" alt="" />
        <img src="/src/assets/characterSample/shinzzang.gif" alt="" />
        <img src="/src/assets/characterSample/television.gif" alt="" />
        <img src="/src/assets/characterSample/attamoma.gif" alt="" />
        <img src="/src/assets/characterSample/juhyeon.gif" alt="" />
      </div>
      <button onClick={() => navigate(Page_Url.Login)}>카카오로그인</button>
    </main>
  );
}

export default MainPage;
