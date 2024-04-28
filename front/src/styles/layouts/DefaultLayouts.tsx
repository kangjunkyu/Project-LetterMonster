import styles from "./DefaultLayouts.module.scss";
import GNB from "../../components/molecules/common/GNB";
import { Outlet } from "react-router";

function DefaultLayouts() {
  return (
    <main>
      <GNB />
      <section className={styles.layoutSection}>
        <Outlet />
      </section>
    </main>
  );
}

export default DefaultLayouts;
