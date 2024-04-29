import { Application } from "@splinetool/runtime";
import { useEffect, useRef } from "react";
import KakaoLogin from "../../atoms/auth/KakaoLoginButton";
import LineLogin from "../../atoms/auth/LineLoginButton";
import styles from "./LoginPage.module.scss";

const LoginPage: React.FC = () => {
  const canvasRef = useRef<HTMLCanvasElement>(null); // HTMLCanvasElement 타입으로 ref 생성

  useEffect(() => {
    if (canvasRef.current) {
      const app = new Application(canvasRef.current); // Application 인스턴스 생성
      app.load("https://prod.spline.design/z8BXHzvYbC8OWd2h/scene.splinecode"); // Spline 모델 로드
    }
  }, []);

  return (
    <>
      <div className={styles.loginContainer}>
        <canvas ref={canvasRef} className={styles.canvas3d}></canvas>
        <KakaoLogin />
        <LineLogin />
      </div>
    </>
  );
};

export default LoginPage;
