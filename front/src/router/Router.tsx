import { Route, Routes } from "react-router-dom";
import { Page_Url } from "./Page_Url";
// 레이아웃
import DefaultLayouts from "../styles/layouts/DefaultLayouts";

// 페이지
import MainPage from "../components/pages/main/MainPage";
import ErrorPage from "../components/pages/error/ErrorPage";
import LoginPage from "../components/pages/login/LoginPage";
import KakaoCallback from "../components/pages/login/KakaoCallback";

function Router() {
  return (
    <Routes>
      <Route element={<DefaultLayouts />} errorElement={<ErrorPage />}>
        <Route path={Page_Url.Main} element={<MainPage />}></Route>

        <Route path={Page_Url.Login} element={<LoginPage />}></Route>
        <Route
          path={Page_Url.KakaoCallback}
          element={<KakaoCallback />}
        ></Route>
      </Route>
    </Routes>
  );
}

export default Router;
