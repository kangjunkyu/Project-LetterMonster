import { Route, Routes } from "react-router-dom";
import { Page_Url } from "./Page_Url";
// 레이아웃
import DefaultLayouts from "../styles/layouts/DefaultLayouts";
import NoLNBLayout from "../styles/layouts/NoLNBLayout";

// 페이지
// import WelcomePage from "../components/pages/welcome/WelcomePage";
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
import MyPage from "../components/pages/mypage/MyPages";
import MotionResultPage from "../components/pages/motion/MotionResultPage";
import NotificationPage from "../components/pages/notification/NotificationPage";
import Cameras from "../components/pages/camera/CameraPage"

function Router() {
  return (
    <Routes>
      {/* <Route path={Page_Url.Welcome} element={<WelcomePage />}></Route> */}
      <Route element={<NoLNBLayout />}>
        <Route path={Page_Url.Main} element={<MainPage />}></Route>
      </Route>
      <Route element={<DefaultLayouts />}>
        <Route path={Page_Url.Login} element={<LoginPage />}></Route>
        <Route path={Page_Url.Noti} element={<NotificationPage />}></Route>
        <Route path={Page_Url.Sketch} element={<SketchPage />}></Route>
        <Route path={Page_Url.Motion} element={<MotionPage />}></Route>
        <Route
          path={Page_Url.MotionResult}
          element={<MotionResultPage />}
        ></Route>
        <Route path={Page_Url.MyPage} element={<MyPage />}></Route>
        <Route
          path={Page_Url.SketchbookList}
          element={<SketchbookListPage />}
        ></Route>
        <Route
          path={`${Page_Url.Sketchbook}:uuid`}
          element={<SketchbookPage />}
        ></Route>

        <Route path={Page_Url.Login} element={<LoginPage />}></Route>
        <Route
          path={Page_Url.WriteLetter}
          element={<LetterWritePage />}
        ></Route>
        <Route
          path={`${Page_Url.WriteLetterToSketchbook}:sketchbookId`}
          element={<LetterWritePage />}
        ></Route>
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
      <Route path={Page_Url.Camera} element={<Cameras />}></Route>
      <Route path={Page_Url.Error404} element={<ErrorPage />}></Route>
    </Routes>
  );
}

export default Router;
