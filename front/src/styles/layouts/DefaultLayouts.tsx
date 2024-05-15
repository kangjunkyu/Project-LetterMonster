import styles from "./DefaultLayouts.module.scss";
import GNB from "../../components/molecules/common/GNB";
import { Outlet } from "react-router";

function DefaultLayouts() {
  return (
    <main>
      <GNB />
      <article className={styles.layoutSection}>
        <Outlet />
      </article>
    </main>
  );
}

export default DefaultLayouts;
