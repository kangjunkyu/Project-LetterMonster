import { useQuery, useQueryClient, useMutation } from "@tanstack/react-query";
import { useNavigate } from "react-router-dom";
import {
  getSketchbookSelected,
  putSketchbookName,
  deleteSketchbook,
  putSketchbookOpen,
} from "../../api/Api";
import { Page_Url } from "../../router/Page_Url";
import { useAlert } from "../notice/useAlert";

/** 스케치북 불러오기 */
export default function useSketchbook(uuid: string) {
  return useQuery({
    queryKey: ["sketchbook"],
    queryFn: () => getSketchbookSelected(uuid),
  });
}

/** 스케치북 수정  */
export function usePutSketchbook() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: ({
      sketchbookId,
      name,
    }: {
      sketchbookId: number;
      name: string;
    }) => putSketchbookName(sketchbookId, name),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["sketchbook"] });
    },
  });
}
/** 스케치북 공개 여부 수정  */
export function usePutSketchbookOpen() {
  const { showAlert } = useAlert();
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (sketchbookId: number) => putSketchbookOpen(sketchbookId),
    onSuccess: () => {
      showAlert("Success");
      queryClient.invalidateQueries({ queryKey: ["sketchbook"] });
    },
    onError: () => {
      showAlert("Error");
    },
  });
}

/** 스케치북 삭제  */
export function useDeleteSketchbook() {
  const navigate = useNavigate();
  return useMutation({
    mutationFn: (sketchbookId: number) => deleteSketchbook(sketchbookId),
    onSuccess: () => {
      navigate(Page_Url.SketchbookList);
    },
  });
}
