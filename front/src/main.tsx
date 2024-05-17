import ReactDOM from "react-dom/client";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { ReactQueryDevtools } from "@tanstack/react-query-devtools";
import { BrowserRouter } from "react-router-dom";
import Router from "./router/Router";
import "./locales/i18n";
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

function isWKWebView() {
  const isIOSDevice = isIOS();
  const wkwebview = !!(window?.webkit && window?.webkit.messageHandlers);
  return isIOSDevice && wkwebview;
}

function isInAppBrowser() {
  const userAgent = navigator.userAgent || navigator.vendor || window.opera;
  return /KAKAOTALK|NAVER|FB_IAB|Instagram|Line|WebView/i.test(userAgent);
}

const App = () => {
  const { Kakao } = window;
  Kakao.cleanup();
  Kakao.init(import.meta.env.VITE_KAKAO_JAVASCRIPT_KEY);

  const ios = isIOS();
  const wkwebview = isWKWebView();
  const inAppBrowser = isInAppBrowser();

  console.log("isIOS:", ios);
  console.log("isWKWebView:", wkwebview);
  console.log("isInAppBrowser:", inAppBrowser);

  if (
    "serviceWorker" in navigator &&
    "PushManager" in window &&
    !ios &&
    !wkwebview &&
    !inAppBrowser
  ) {
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
