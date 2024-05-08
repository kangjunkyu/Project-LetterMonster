import { useQuery } from "@tanstack/react-query";
import { getSearchSketchbook } from "../../api/Api";

/** 스케치북 이름으로 검색 */
export default function useSearchSketchbook(sketchbookName: string) {
  return useQuery({
    queryKey: ["searchSketchbook", sketchbookName],
    queryFn: () => {
      // sketchbookName이 비어 있지 않을 때만 API 호출
      return sketchbookName
        ? getSearchSketchbook(sketchbookName)
        : Promise.resolve({});
    },
  });
}
