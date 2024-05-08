import { useQuery } from "@tanstack/react-query";
import { getMotionSelect } from "../../api/Api";

export default function useGetSelectedMotion(
  characterId: number,
  motionId: number
) {
  return useQuery({
    queryKey: ["motion", characterId, motionId],
    queryFn: () => {
      // sketchbookName이 비어 있지 않을 때만 API 호출
      return characterId != 0 && motionId != 0
        ? getMotionSelect(characterId, motionId)
        : Promise.resolve({});
    },
  });
}
