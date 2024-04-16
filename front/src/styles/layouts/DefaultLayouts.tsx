import "./DefaultLayouts.scss";
import GNB from "../../components/molecules/common/GNB";
import SNB from "../../components/molecules/common/SNB";
import { Outlet } from "react-router";

function DefaultLayouts() {
  return (
    <main>
      <GNB />
      <SNB />
      <section>
        <Outlet />
      </section>
    </main>
  );
}

export default DefaultLayouts;
