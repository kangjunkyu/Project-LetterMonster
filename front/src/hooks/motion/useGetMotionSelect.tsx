import { useQuery } from "@tanstack/react-query";
import { getMotionSelect } from "../../api/Api";

export function useGetMotionSelect(characterId: number, motionId: number) {
  return useQuery({
    queryKey: ["motionSelect"],
    queryFn: () => getMotionSelect(characterId, motionId),
  });
}
