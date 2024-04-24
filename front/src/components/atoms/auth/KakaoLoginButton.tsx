import Kakao from "../../../assets/login/kakaoLogin.png";
import styles from "./AuthButton.module.scss";

function KakaoLogin() {
  const BASE_URL = import.meta.env.VITE_BASE_URL;
  const REDIRECT_URI = import.meta.env.VITE_KAKAO_REDIRECT_URI;
  const KAKAO_AUTH_URL = `${BASE_URL}/oauth2/authorization/kakao?redirect_uri=${REDIRECT_URI}`;

  const Login = () => {
    window.location.href = KAKAO_AUTH_URL;
  };

  return (
    <button type="button" onClick={Login}>
      <img className={styles.loginImg} src={Kakao} alt="Kakao Login" />
    </button>
  );
}

export default KakaoLogin;
