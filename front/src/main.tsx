import ReactDOM from "react-dom/client";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query"; // 리액트 쿼리
import { ReactQueryDevtools } from "@tanstack/react-query-devtools"; // 리액트 쿼리 데브툴
import { BrowserRouter } from "react-router-dom"; // 라우터
import Router from "./router/Router"; // 라우터
import "./locales/i18n"; // 다국어 지원
import { useScript } from "./hooks/share/useShareToKakao";
import { useEffect } from "react";
import { AlertProvider } from "./hooks/notice/useAlert";
import ReactGA from "react-ga4"; // 구글 애널리틱스
import RouterChangeTracker from "./util/ga/RouterChangeTracker"; // Ga - 트래커

declare global {
  interface Window {
    Kakao: any;
  }
}

if (import.meta.env.REACT_APP_GOOGLE_ANALYTICS) {
  ReactGA.initialize(import.meta.env.REACT_APP_GOOGLE_ANALYTICS);
}

const queryClient = new QueryClient();

const App = () => {
  const status = useScript("https://developers.kakao.com/sdk/js/kakao.js");
  useEffect(() => {
    if (status === "ready" && window.Kakao && !window.Kakao.isInitialized()) {
      window.Kakao.init(import.meta.env.VITE_KAKAO_JAVASCRIPT_KEY);
    }
  }, [status]);
  return (
    <QueryClientProvider client={queryClient}>
      <BrowserRouter>
        <AlertProvider>
          <Router />
        </AlertProvider>
        <RouterChangeTracker />;
        <ReactQueryDevtools initialIsOpen={true} />
      </BrowserRouter>
    </QueryClientProvider>
  );
};

const root = ReactDOM.createRoot(
  document.getElementById("root") as HTMLElement
);
root.render(<App />);
