import { ReactNode } from "react";

import styles from "./DefaultButton.module.scss";

interface Props {
  children: ReactNode;
  onClick?: () => void;
  custom?: boolean;
}

function DefaultButton({ onClick, children, custom = false }: Props) {
  return (
    <button className={custom ? "" : styles.button_default} onClick={onClick}>
      {children}
    </button>
  );
}

export default DefaultButton;
