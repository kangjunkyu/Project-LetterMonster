import { useQuery, useQueryClient, useMutation } from "@tanstack/react-query";
import {
  getSketchbookList,
  postSketchbook,
  getSketchbookListAll,
} from "../../api/Api";
import useCheckTokenExpiration from "../auth/useCheckTokenExpiration";

/** 스케치북 리스트 불러오기 */
export default function useSketchbookList() {
  const checkToken = useCheckTokenExpiration();
  return useQuery({
    queryKey: ["sketchbooklist"],
    queryFn: () => {
      return checkToken(localStorage.getItem("accessToken"))
        ? getSketchbookList()
        : Promise.resolve({});
    },
  });
}

/** 스케치북 리스트 전체 불러오기 */
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
      queryClient.invalidateQueries({ queryKey: ["sketchbooklist"] });
    },
    mutationKey: ["createSketchbook"],
  });
}
