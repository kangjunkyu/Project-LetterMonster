import { Paint } from "./Paint";
import styles from "./SketchPage.module.scss";

function SketchPage() {
  return (
    <>
      <div className={styles.sketch_container}>
        <Paint />
      </div>
    </>
  );
}

export default SketchPage;
