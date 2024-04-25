import KakaoLogin from "../../atoms/auth/KakaoLoginButton";
import LineLogin from "../../atoms/auth/LineLoginButton";
import styles from "./LoginPage.module.scss";

function LoginPage() {
  return (
    <>
      <div className={styles.loginContainer}>
        <KakaoLogin />
        <LineLogin />
      </div>
    </>
  );
}

export default LoginPage;
