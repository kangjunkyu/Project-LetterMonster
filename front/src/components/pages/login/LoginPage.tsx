import KakaoLogin from "../../atoms/auth/KakaoLoginButton";
import LineLogin from "../../atoms/auth/LineLoginButton";
import styles from "./LoginPage.module.scss";

const LoginPage: React.FC = () => {
  return (
    <>
      <div className={styles.loginContainer}>
        <KakaoLogin />
        <LineLogin />
      </div>
    </>
  );
};

export default LoginPage;
