import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";
import svgr from "vite-plugin-svgr";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react(), svgr()],
  build: {
    rollupOptions: {
      input: {
        main: "index.html",
        sw: "firebase-messaging-sw.js", // 서비스 워커 파일 추가
      },
    },
  },
});
