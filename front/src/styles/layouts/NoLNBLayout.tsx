import styles from "./NoLNBLayout.module.scss";
import GNB from "../../components/molecules/common/GNB";
import { Outlet } from "react-router";

function NoLNBLayout() {
  return (
    <main>
      <GNB />
      <section className={styles.layoutSection}>
        <Outlet />
      </section>
    </main>
  );
}

export default NoLNBLayout;
