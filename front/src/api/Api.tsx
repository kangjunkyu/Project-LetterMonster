import API from "./Config";

export const baseAPI = () => API.get("/");

// 카카오 소셜 로그인
export const getKakaoLogin = (code: string | null)=>{
    return API.get(`/users/kakao`, {params: {code:code}});
};