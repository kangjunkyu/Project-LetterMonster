import { useQuery } from "@tanstack/react-query";
import { getMotionSelect } from "../../api/Api";

export default function useGetSelectedMotion(
  characterId: number,
  motionId: number
) {
  return useQuery({
    queryKey: ["motion", characterId, motionId],
    queryFn: () => {
      if (characterId && motionId) {
        return getMotionSelect(characterId, motionId);
      }
    },
  });
}
