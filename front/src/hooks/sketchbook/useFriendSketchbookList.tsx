import { useQuery } from "@tanstack/react-query";
import { getFriendSketchbookList } from "../../api/Api";

/** 스케치북 리스트 불러오기 */
export default function useFriendSketchbookList(userId: number) {
  return useQuery({
    queryKey: ["friendsSketchbooks", userId],
    queryFn: () => {
      return userId !== -1
        ? getFriendSketchbookList(userId)
        : Promise.resolve({});
    },
  });
}
