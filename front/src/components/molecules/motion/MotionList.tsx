import styles from "./MotionList.module.scss";

function MotionList() {
  return (
    <>
      <div className={styles.motionListContainer}>
        <div>Motion 종류</div>
        <div>1번 : dav</div>
        <div>2번 : jesse_dance</div>
        <div>3번 : jumping_jacks</div>
        <div>4번 : jumping</div>
        <div>5번 : wave_hello</div>
        <div>6번 : zombie</div>
      </div>
    </>
  );
}

export default MotionList;
