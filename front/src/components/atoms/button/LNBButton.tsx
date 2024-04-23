import styles from "./LNBButton.module.scss";
import { ReactNode } from "react";

interface Props {
  children: ReactNode;
  onClick?: () => void;
  onKeyDown?: (e: React.KeyboardEvent<HTMLButtonElement>) => void;
}

function LNBButton({ onClick, onKeyDown, children }: Props) {
  return (
    <button
      className={styles.LNBButtonContainer}
      onClick={onClick}
      onKeyDown={(e) => onKeyDown?.(e)}
    >
      {children}
    </button>
  );
}

export default LNBButton;
