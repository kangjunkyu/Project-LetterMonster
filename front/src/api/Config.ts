import axios from "axios";
import { Page_Url } from "../router/Page_Url";

export function createCustomAxios(baseURL: any, contentType: any) {
  const instance = axios.create({
    baseURL,
  });

  instance.interceptors.request.use((config: any) => ({
    ...config,
    headers: {
      Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
      "Content-Type": contentType,
    },
  }));

  return instance;
}

// 응답 인터셉터 처리
function setupResponseInterceptor(instance: any) {
  instance.interceptors.response.use(
    (response: any) => {
      return response;
    },
    async (error: any) => {
      if (!error.response) {
        // 네트워크 오류 또는 서버가 응답하지 않는 경우
        return Promise.reject(error);
      }

      const {
        config,
        response: { status },
      } = error;

      const refreshToken = localStorage.getItem("refreshToken");

      // 토큰 만료시
      if (status === 401) {
        if (!refreshToken) {
          localStorage.removeItem("accessToken");
          window.location.href = Page_Url.Main;
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
            return axios(config);
          } else {
            window.location.href = Page_Url.Main;
          }
        } catch (refreshError) {
          window.location.href = Page_Url.Main;
        }
      }

      return Promise.reject(error);
    }
  );
}

// 인스턴스 생성 및 인터셉터 설정
export const API = createCustomAxios(
  import.meta.env.VITE_BASE_URL,
  "application/json"
);
setupResponseInterceptor(API); // API에 인터셉터 적용

export const ImgAPI = createCustomAxios(
  import.meta.env.VITE_BASE_URL,
  "multipart/form-data"
);
setupResponseInterceptor(ImgAPI); // ImgAPI에도 동일하게 인터셉터 적용

export default API;
