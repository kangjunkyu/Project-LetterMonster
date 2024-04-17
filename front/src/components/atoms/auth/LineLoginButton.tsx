import Line from "../../../assets/login/lineLogin.png";
import styles from "./AuthButton.module.scss";

function LineLogin() {
  const LineClientId = import.meta.env.VITE_LINE_CLIENT_ID;
  const LineRedirectURI = import.meta.env.VITE_LINE_REDIRECT_URI;
  const LineState = import.meta.env.VITE_LINE_STATE;
  const LINE_AUTH_URL = `https://access.line.me/oauth2/v2.1/authorize?response_type=code&client_id=${LineClientId}&redirect_uri=${LineRedirectURI}&state=${LineState}&scope=profile`;

  const Login = () => {
    window.location.href = LINE_AUTH_URL;
  };

  return (
    <button type="button" onClick={Login}>
      <img className={styles.loginImg} src={Line} alt="라인 로그인" />
    </button>
  );
}

export default LineLogin;
