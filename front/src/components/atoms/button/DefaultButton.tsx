import { ReactNode } from "react";

import styles from "./DefaultButton.module.scss";

interface Props {
  children: ReactNode;
  onClick?: () => void;
  custom?: boolean;
  className?: string;
  onKeyDown?: (e: React.KeyboardEvent<HTMLButtonElement>) => void;
}

function DefaultButton({
  onClick,
  children,
  custom = false,
  className,
  onKeyDown,
}: Props) {
  return (
    <button
      className={custom ? className : styles.button_default}
      onClick={onClick}
      onKeyDown={onKeyDown}
    >
      {children}
    </button>
  );
}

export default DefaultButton;
