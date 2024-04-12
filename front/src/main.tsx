import ReactDOM from "react-dom/client";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query"; // 리액트 쿼리
import { ReactQueryDevtools } from "@tanstack/react-query-devtools"; // 리액트 쿼리 데브툴
import { BrowserRouter } from "react-router-dom"; // 라우터
import Router from "./router/Router"; // 라우터
import "./locales/i18n"; // 다국어 지원

const queryClient = new QueryClient();

import { worker } from "./mocks/worker";

if (process.env.NODE_ENV === "development") {
  worker.start();
}

ReactDOM.createRoot(document.getElementById("root") as HTMLElement).render(
  <QueryClientProvider client={queryClient}>
    <BrowserRouter>
      <Router />
      <ReactQueryDevtools initialIsOpen={true} />
    </BrowserRouter>
  </QueryClientProvider>
);
