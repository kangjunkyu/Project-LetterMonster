import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Page_Url } from "../../router/Page_Url";
import { postLineLogin } from "../../api/Api";

function useLineCallback() {
  const navigate = useNavigate();
  useEffect(() => {
    const code = new URL(window.location.href).searchParams.get("code");
    const state = new URL(window.location.href).searchParams.get("state");
    {
      code &&
        state === import.meta.env.VITE_LINE_STATE &&
        postLineLogin(code)
          .then((res) => {
            //springboot에서 발급된 jwt 반환 localstorage에 저장
            localStorage.setItem("accessToken", res.data.token["accessToken"]);
            localStorage.setItem(
              "refreshToken",
              res.data.token["refreshToken"]
            );

            //메인 페이지로 이동
            navigate(Page_Url.Main);
          })
          .catch((error: any) => {
            //에러 발생 시 로그인 페이지로 이동
            navigate(Page_Url.Login);
            console.log(error);
          });
    }
  });
}

export default useLineCallback;
