import { useQuery, useQueryClient, useMutation } from "@tanstack/react-query";
import { getSketchbookList, postSketchbook } from "../../api/Api";

/** 스케치북 리스트 불러오기 */
export default function useSketchbookList() {
  return useQuery({
    queryKey: ["sketchbookList"],
    queryFn: () => getSketchbookList(),
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
