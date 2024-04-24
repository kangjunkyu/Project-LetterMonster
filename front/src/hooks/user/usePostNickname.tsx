import { useMutation, useQueryClient } from "@tanstack/react-query";
import { postNickname } from "../../api/Api";

export function usePostNickname() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (nickname: string) => postNickname(nickname),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["Nickname"] });
    },
  });
}
