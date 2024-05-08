import { ReactNode } from "react";
import styles from "./SearchList.module.scss";

interface Props {
  children: ReactNode;
}

function SearchList({ children }: Props) {
  return <ul className={styles.ListContainer}>{children}</ul>;
}

export default SearchList;
