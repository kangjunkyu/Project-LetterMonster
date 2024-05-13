import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import {
  getFavoriteSketchbook,
  postFavoriteSketchbook,
  getFavoriteSketchbookCheck,
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

export function useFavoriteSketchbookCheck(sketchbookId: number) {
  const checkToken = useCheckTokenExpiration();
  return useQuery({
    queryKey: ["favoriteBoolean", sketchbookId],
    queryFn: () => {
      if (checkToken(localStorage.getItem("accessToken")) && sketchbookId)
        return getFavoriteSketchbookCheck(sketchbookId);
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
      queryClient.invalidateQueries({ queryKey: ["favoriteBoolean"] });
    },
    mutationKey: ["postfavorite"],
  });
}
