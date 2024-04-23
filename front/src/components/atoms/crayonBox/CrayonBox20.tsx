import { useState, useEffect } from "react";
import "./CrayonBox20.scss";

interface Props {
  children: React.ReactNode;
}

function CrayonBox20({ children }: Props) {
  const [currentClass, setCurrentClass] = useState("BoxContainer");
  const classes = [
    "BoxContainer",
    "BoxContainer2",
    "BoxContainer3",
    "BoxContainer4",
  ];

  useEffect(() => {
    const intervalId = setInterval(() => {
      setCurrentClass((current) => {
        // 현재 클래스 인덱스 계산
        const currentIndex = classes.indexOf(current);
        // 다음 클래스 인덱스 계산
        const nextIndex = (currentIndex + 1) % classes.length;
        // 다음 클래스 반환
        return classes[nextIndex];
      });
    }, 1000);

    // 컴포넌트 언마운트 시 인터벌 정리
    return () => clearInterval(intervalId);
  }, []); // 빈 의존성 배열을 사용하여 마운트 시 한 번만 설정

  return <section className={currentClass}>{children}</section>;
}

export default CrayonBox20;
