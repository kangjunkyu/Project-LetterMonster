import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import {
  deleteFavoriteSketchbook,
  getFavoriteSketchbook,
  postFavoriteSketchbook,
} from "../../api/Api";
import useCheckTokenExpiration from "../auth/useCheckTokenExpiration";

export default function useFavoriteSketchbook() {
  const checkToken = useCheckTokenExpiration();
  return useQuery({
    queryKey: ["favorite"],
    queryFn: () => {
      if (checkToken(localStorage.getItem("accessToken")))
        return getFavoriteSketchbook();
      else {
        return Promise.resolve({});
      }
    },
  });
}

export function useFavoriteSketchbookOn() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (sketchbookId: number) => postFavoriteSketchbook(sketchbookId),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["favorite"] });
    },
    mutationKey: ["postfavorite"],
  });
}

export function useFavoriteDelete() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (sketchbookId: number) =>
      deleteFavoriteSketchbook(sketchbookId),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["favorite"] });
    },
    mutationKey: ["deletefavorite"],
  });
}
