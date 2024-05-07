import Line from "../../../assets/login/lineLogin.png";
import DefaultButton from "../button/DefaultButton";
import styles from "./AuthButton.module.scss";

function LineLogin() {
  const LineRedirectURI = import.meta.env.VITE_LINE_REDIRECT_URI;
  const BaseUrl = import.meta.env.VITE_BASE_URL;
  const LINE_AUTH_URL = `${BaseUrl}/oauth2/authorization/line?redirect_uri=${LineRedirectURI}&firebase_token=${localStorage.getItem(
    "fcm_token"
  )}`;

  const Login = () => {
    window.location.href = LINE_AUTH_URL;
  };

  return (
    <DefaultButton onClick={Login} custom={true}>
      <img className={styles.loginImg} src={Line} alt="라인 로그인" />
    </DefaultButton>
  );
}

export default LineLogin;
