import { useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { Page_Url } from "../../router/Page_Url";

function useKakaoCallback() {
  const navigate = useNavigate();
  const location = useLocation(); // 현재 위치 정보를 가져옴

  useEffect(() => {
    // URLSearchParams를 사용하여 쿼리 파라미터 접근
    const queryParams = new URLSearchParams(location.search);
    const accessToken = queryParams.get("accessToken");
    const refreshToken = queryParams.get("refreshToken");

    // localStorage에 토큰 저장
    if (accessToken && refreshToken) {
      localStorage.setItem("accessToken", accessToken);
      localStorage.setItem("refreshToken", refreshToken);
      navigate(Page_Url.Main);
    }
  }, [location]);

  return <div>로그인 처리 중입니다...</div>;
}

export default useKakaoCallback;
