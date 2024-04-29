import { ReactNode } from "react";

import styles from "./DefaultButton.module.scss";

interface Props {
  children: ReactNode;
  onClick?: () => void;
  custom?: boolean;
  className?: string;
}

function DefaultButton({
  onClick,
  children,
  custom = false,
  className,
}: Props) {
  return (
    <button
      className={custom ? className : styles.button_default}
      onClick={onClick}
    >
      {children}
    </button>
  );
}

export default DefaultButton;
