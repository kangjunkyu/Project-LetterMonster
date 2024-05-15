import { jwtDecode } from "jwt-decode";

export default function useCheckTokenExpiration() {
  return (token: string | null) => {
    if (!token) {
      return false;
    }
    try {
      const decoded = jwtDecode<{ exp: number }>(token);
      const currentTime = Date.now() / 1000; // 현재 시간을 초 단위로 변환
      if (decoded.exp < currentTime) {
        return false;
      }
      return true;
    } catch (error) {
      return false;
    }
  };
}
