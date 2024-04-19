import { Route, Routes } from "react-router-dom";
import { Page_Url } from "./Page_Url";
// 레이아웃
import DefaultLayouts from "../styles/layouts/DefaultLayouts";

// 페이지
import MainPage from "../components/pages/main/MainPage";
import ErrorPage from "../components/pages/error/ErrorPage";
import LoginPage from "../components/pages/login/LoginPage";
import KakaoCallback from "../components/pages/login/KakaoCallback";
import LineCallback from "../components/pages/login/LineCallback";
import SketchPage from "../components/pages/sketch/SketchPage";
import MotionPage from "../components/pages/motion/MotionPage";
import LetterWritePage from "../components/pages/letter/WriteLetterPage";
import SketchbookListPage from "../components/pages/sketchbook/SketchbookListPage";
import SketchbookPage from "../components/pages/sketchbook/SketchbookPage";

function Router() {
  return (
    <Routes>
      <Route element={<DefaultLayouts />} errorElement={<ErrorPage />}>
        <Route path={Page_Url.Main} element={<MainPage />}></Route>
        <Route path={Page_Url.Login} element={<LoginPage />}></Route>
        <Route path={Page_Url.Sketch} element={<SketchPage />}></Route>
        <Route path={Page_Url.Motion} element={<MotionPage />}></Route>
        <Route
          path={Page_Url.SketchbookList}
          element={<SketchbookListPage />}
        ></Route>
        <Route
          path={Page_Url.Sketchbook + ":sketchbookId"}
          element={<SketchbookPage />}
        ></Route>

        <Route path={Page_Url.Login} element={<LoginPage />}></Route>
        <Route
          path={Page_Url.WriteLetter}
          element={<LetterWritePage />}
        ></Route>
        <Route
          path={Page_Url.KakaoCallback}
          element={<KakaoCallback />}
        ></Route>
        <Route path={Page_Url.LineCallback} element={<LineCallback />}></Route>
      </Route>
    </Routes>
  );
}

export default Router;
