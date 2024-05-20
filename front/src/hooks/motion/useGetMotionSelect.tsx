import { useCallback } from "react";
import { getMotionSelect } from "../../api/Api";

export function useGetMotionSelect() {
  const callGetMotionSelect = useCallback(
    (characterId: number, motionId: number): any => {
      return getMotionSelect(characterId, motionId);
    },
    []
  );

  return callGetMotionSelect;
}
