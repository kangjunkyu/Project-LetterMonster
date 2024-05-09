import { useEffect, useState } from "react";
import GNB from "../../components/molecules/common/GNB";
import styles from "./NoGNBLayout.module.scss";
import { Outlet } from "react-router";

function NoGNBLayout() {
  const [windowWidth, setWindowWidth] = useState(window.innerWidth);

  useEffect(() => {
    const handleResize = () => {
      setWindowWidth(window.innerWidth);
    };

    window.addEventListener("resize", handleResize);

    return () => {
      window.removeEventListener("resize", handleResize);
    };
  }, []);

  return (
    <main>
      {windowWidth >= 479 && <GNB />}
      <section className={styles.layoutSection}>
        <Outlet />
      </section>
    </main>
  );
}

export default NoGNBLayout;
