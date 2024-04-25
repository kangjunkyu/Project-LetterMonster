import { useEffect, useState } from "react";

function useTrigger() {
  const [trigger, setTrigger] = useState(false);
  useEffect(() => {
    setTrigger(true);
    setTimeout(() => {
      setTrigger(false);
    }, 3000);
  }, []);
  return { trigger };
}

export default useTrigger;
