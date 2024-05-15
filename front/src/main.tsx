import { useEffect } from "react";
import ReactDOM from "react-dom/client";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { ReactQueryDevtools } from "@tanstack/react-query-devtools";
import { BrowserRouter } from "react-router-dom";
import Router from "./router/Router";
import "./locales/i18n";
import { AlertProvider } from "./hooks/notice/useAlert";
import { GetTokenIOS } from "./util/fcm/messaging_get_token";
import { App as CapacitorApp } from "@capacitor/app";
import styles from "./main.module.scss";

declare global {
  interface Window {
    Kakao: any;
  }
}

const queryClient = new QueryClient();

const App = () => {
  useEffect(() => {
    const initializeFCM = async () => {
      try {
        await GetTokenIOS();
      } catch (error) {
        console.error("Failed to get FCM token:", error);
      }
    };

    const initializeKakao = () => {
      const { Kakao } = window;
      if (Kakao) {
        Kakao.cleanup();
        Kakao.init(import.meta.env.VITE_KAKAO_JAVASCRIPT_KEY);
      }
    };

    const handleAppUrlOpen = (data: any) => {
      const url = new URL(data.url);
      const accessToken = url.searchParams.get("accessToken");
      const refreshToken = url.searchParams.get("refreshToken");

      if (accessToken && refreshToken) {
        localStorage.setItem("accessToken", accessToken);
        localStorage.setItem("refreshToken", refreshToken);
        window.location.reload();
      }
    };

    initializeFCM();
    initializeKakao();

    // URL 스키마 처리 리스너 추가
    CapacitorApp.addListener("appUrlOpen", handleAppUrlOpen);

    // useEffect 클린업 함수에서 리스너 제거
    return () => {
      CapacitorApp.removeAllListeners();
    };
  }, []);

  return (
    <QueryClientProvider client={queryClient}>
      <BrowserRouter>
        <AlertProvider>
          <div className={styles.safearea}></div>
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
