import { useQuery, useQueryClient, useMutation } from "@tanstack/react-query";
import {
  getSketchbookList,
  postSketchbook,
  getSketchbookListAll,
} from "../../api/Api";

/** 스케치북 리스트 불러오기 */
export default function useSketchbookList() {
  return useQuery({
    queryKey: ["sketchbooklist"],
    queryFn: () => getSketchbookList(),
  });
}

/** 스케치북 리스트전체 불러오기 */
export function useSketchbookListAll() {
  return useQuery({
    queryKey: ["sketchbooklistall"],
    queryFn: () => getSketchbookListAll(),
  });
}

/** 스케치북 생성  */
export function useCreateSketchbook() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (name: string) => postSketchbook(name),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["sketchbookList"] });
    },
    mutationKey: ["createSketchbook"],
  });
}
