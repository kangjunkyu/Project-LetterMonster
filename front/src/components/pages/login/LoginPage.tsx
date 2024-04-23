import KakaoLogin from "../../atoms/auth/KakaoLoginButton";
import LineLogin from "../../atoms/auth/LineLoginButton";

function LoginPage() {
  return (
    <>
      <KakaoLogin />
      <LineLogin />
    </>
  );
}

export default LoginPage;
