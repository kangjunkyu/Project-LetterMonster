import Line from "../../../assets/login/lineLogin.png";
import DefalutButton from "../button/DefalutButton";
import styles from "./AuthButton.module.scss";

function LineLogin() {
  const LineRedirectURI = import.meta.env.VITE_LINE_REDIRECT_URI;
  const BaseUrl = import.meta.env.VITE_BASE_URL;
  const LINE_AUTH_URL = `${BaseUrl}/oauth2/authorization/line?redirect_uri=${LineRedirectURI}`;

  const Login = () => {
    window.location.href = LINE_AUTH_URL;
  };

  return (
    <DefalutButton onClick={Login} custom={true}>
      <img className={styles.loginImg} src={Line} alt="라인 로그인" />
    </DefalutButton>
  );
}

export default LineLogin;
