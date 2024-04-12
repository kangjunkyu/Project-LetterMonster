import { Route, Routes } from "react-router-dom";
import { Page_Url } from "./Page_Url";
// 레이아웃

// 페이지
import MainPage from "../components/pages/main/MainPage";

function Router() {
  return (
    <Routes>
      <Route path={Page_Url.Main} element={<MainPage />}></Route>
    </Routes>
  );
}

export default Router;
