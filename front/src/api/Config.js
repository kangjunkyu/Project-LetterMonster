import axios from "axios";
import { Page_Url } from "../router/Page_Url";

export function createCustomAxios(baseURL, contentType) {
  const instance = axios.create({
    baseURL,
  });

  instance.interceptors.request.use((config) => ({
    ...config,
    headers: {
      Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
      "Content-Type": contentType,
    },
  }));

  return instance;
}

export const API = createCustomAxios(
  import.meta.env.VITE_BASE_URL,
  "application/json"
);
export const ImgAPI = createCustomAxios(
  import.meta.env.VITE_BASE_URL,
  "multipart/form-data"
);

// 응답 인터셉터 처리
API.interceptors.response.use(
  (response) => {
    return response;
  },
  async (error) => {
    if (!error.response) {
      // 네트워크 오류 또는 서버가 응답하지 않는 경우
      return Promise.reject(error);
    }

    const {
      config,
      response: { status },
    } = error;

    // accessToken과 refreshToken이 모두 없으면 로그인 페이지로 이동
    const accessToken = localStorage.getItem("accessToken");
    const refreshToken = localStorage.getItem("refreshToken");

    if (!accessToken && !refreshToken) {
      window.location.href = Page_Url.Login;
      return;
    }

    // 토큰 만료시
    if (status === 401) {
      if (!refreshToken) {
        window.location.href = Page_Url.Login;
        return;
      }

      try {
        const baseURL = import.meta.env.VITE_BASE_URL;
        const response = await axios
          .create({
            baseURL,
            headers: {
              Authorization: `Bearer ${refreshToken}`,
            },
          })
          .post(import.meta.env.VITE_REFRESH_TOKEN);

        if (response.status === 200) {
          localStorage.setItem(
            "accessToken",
            response.data.data["accessToken"]
          );
          localStorage.setItem(
            "refreshToken",
            response.data.data["refreshToken"]
          );

          axios.defaults.headers.common[
            "Authorization"
          ] = `Bearer ${response.data.data["accessToken"]}`;
          config.headers[
            "Authorization"
          ] = `Bearer ${response.data.data["accessToken"]}`;
          console.log("엥여기?")
          return axios(config);
        } else {
          window.location.href = Page_Url.Login;
        }
      } catch (refreshError) {
        window.location.href = Page_Url.Login;
      }
    }

    return Promise.reject(error);
  }
);

export default API;
