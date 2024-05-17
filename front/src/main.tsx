import ReactDOM from "react-dom/client";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query"; // 리액트 쿼리
import { ReactQueryDevtools } from "@tanstack/react-query-devtools"; // 리액트 쿼리 데브툴
import { BrowserRouter } from "react-router-dom"; // 라우터
import Router from "./router/Router"; // 라우터
import "./locales/i18n"; // 다국어 지원
import { AlertProvider } from "./hooks/notice/useAlert";
import GetToken from "./util/fcm/messaging_get_token";

declare global {
  interface Window {
    Kakao: any;
  }
}

const queryClient = new QueryClient();

function isIOS() {
  return (
    /iPad|iPhone|iPod/.test(navigator.userAgent) ||
    (navigator.userAgent.includes("Mac") && "ontouchend" in document)
  );
}

const App = () => {
  const { Kakao } = window;
  Kakao.cleanup();
  Kakao.init(import.meta.env.VITE_KAKAO_JAVASCRIPT_KEY);
  if ("serviceWorker" in navigator && "PushManager" in window && !isIOS()) {
    navigator.serviceWorker
      .register("/firebase-messaging-sw.js")
      .then(function (registration) {
        console.log("Service Worker 등록 성공:", registration);
      })
      .catch(function (error) {
        console.error("Service Worker 등록 실패:", error);
      });
  } else {
    console.log("이 환경은 Service Worker 또는 푸시 알림을 지원하지 않습니다.");
  }
  GetToken();

  return (
    <QueryClientProvider client={queryClient}>
      <BrowserRouter>
        <AlertProvider>
          <Router />
        </AlertProvider>
        <ReactQueryDevtools initialIsOpen={true} />
      </BrowserRouter>
    </QueryClientProvider>
  );
};

const root = ReactDOM.createRoot(
  document.getElementById("root") as HTMLElement
);
root.render(<App />);
