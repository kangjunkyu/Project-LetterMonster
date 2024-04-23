import ReactDOM from "react-dom/client";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query"; // 리액트 쿼리
import { ReactQueryDevtools } from "@tanstack/react-query-devtools"; // 리액트 쿼리 데브툴
import { BrowserRouter } from "react-router-dom"; // 라우터
import Router from "./router/Router"; // 라우터
import "./locales/i18n"; // 다국어 지원
import ReactGA from "react-ga"; // 구글 애널리틱스
import RouterChangeTracker from "./util/ga/RouterChangeTracker"; // Ga - 트래커

const queryClient = new QueryClient();

// const gaTrackingId = import.meta.env.VITE_APP_GA_TRACKING_ID;
// ReactGA.initialize(gaTrackingId, { debug: true }); // react-ga 초기화 및 debug 사용

// RouterChangeTracker();

ReactDOM.createRoot(document.getElementById("root") as HTMLElement).render(
  <QueryClientProvider client={queryClient}>
    <BrowserRouter>
      <Router />
      <ReactQueryDevtools initialIsOpen={true} />
    </BrowserRouter>
  </QueryClientProvider>
);
