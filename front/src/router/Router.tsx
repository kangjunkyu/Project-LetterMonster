import { Route, Routes } from "react-router-dom";
import { Page_Url } from "./Page_Url";
// 레이아웃

// 페이지
import MainPage from "../components/pages/main/MainPage";
import LoginPage from "../components/pages/login/LoginPage";
import KakaoCallback from "../components/pages/login/KakaoCallback";

function Router() {
  return (
    <Routes>
      <Route path={Page_Url.Main} element={<MainPage />}></Route>
      <Route path={Page_Url.Login} element={<LoginPage />}></Route>

      <Route path={Page_Url.KakaoCallback} element={<KakaoCallback />}></Route>
    </Routes>
  );
}

export default Router;
