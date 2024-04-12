import axios from "axios";

export function createCustomAxios(baseURL, contentType) {
  const instance = axios.create({
    baseURL,
  });

  instance.interceptors.request.use((config) => ({
    ...config,
    headers: {
      Authorization: localStorage.getItem("accessToken"),
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
// API.interceptors.response.use(
//   (response) => {
//     return response;
//   },
//   async (error) => {
//     if (error.response) {
//       const { config } = error;
//       // 토큰 만료시
//       if (error.response.status === 401) {
//         const originRequest = config;
//         try {
//           // 리프레시 토큰 api
//           const response = await axios
//             .create({
//               baseURL: import.meta.env.VITE_BASE_URL,
//               headers: { refreshToken: localStorage.getItem("refreshToken") },
//             })
//             .post("/auth/renew");
//           // 리프레시 토큰 요청이 성공할 때
//           if (response.status === 200) {
//             const newAccessToken = response.headers["authorization"];
//             localStorage.setItem("accessToken", newAccessToken);
//             localStorage.setItem(
//               "refreshToken",
//               response.headers["refreshtoken"]
//             );
//             axios.defaults.headers.common.Authorization = `${newAccessToken}`;
//             // 진행중이던 요청 이어서하기
//             originRequest.headers.Authorization = `${newAccessToken}`;
//             return axios.create(originRequest);
//             // 리프레시 토큰 요청이 실패할때(리프레시 토큰도 만료되었을때 = 재로그인 안내)
//           }
//           if (response.status === 401 || response.status === 404) {
//             window.location.href = "/login";
//           }
//         } catch (error) {
//           window.location.href = "/login";
//         }
//         window.location.href = "/login";
//       }
//     }
//     return Promise.reject(error);
//   }
// );

export default API;
