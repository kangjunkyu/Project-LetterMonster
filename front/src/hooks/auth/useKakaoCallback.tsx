import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Page_Url } from "../../router/Page_Url";
import { getKakaoLogin } from "../../api/Api";

function useKakaoCallback() {
  const navigate = useNavigate();
  useEffect(() => {
    const code = new URL(window.location.href).searchParams.get("code");
    getKakaoLogin(code)
      .then((res) => {
        //springboot에서 발급된 jwt 반환 localstorage에 저장
        localStorage.setItem("accessToken", res.headers["accessToken"]);
        localStorage.setItem("refreshToken", res.headers["refreshToken"]);
        localStorage.setItem("uid", res.data.data.userId);
        localStorage.setItem("oAuthProvider", res.data.data.oAuthProvider);

        //메인 페이지로 이동
        navigate(Page_Url.Main);
      })
      .catch((error: any) => {
        //에러 발생 시 로그인 페이지로 이동
        navigate(Page_Url.Login);
        console.log(error);
      });
  });
}

export default useKakaoCallback;
