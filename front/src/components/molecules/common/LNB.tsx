import { ReactNode } from "react";
import styles from "./LNB.module.scss";
import { useNavigate } from "react-router";
import DefalutButton from "../../atoms/button/DefalutButton";
import back from "../../../assets/commonIcon/back.svg";

interface Props {
  children: ReactNode;
}

function LNB({ children }: Props) {
  const navigate = useNavigate();
  return (
    <nav className={styles.localnavContainer}>
      <DefalutButton onClick={() => navigate(-1)} custom={true}>
        <img src={back} alt="뒤로 가기" />
      </DefalutButton>
      {children}
    </nav>
  );
}

export default LNB;
