import { postSketchCharacter } from "../../api/Api";
import { useMutation, useQueryClient } from "@tanstack/react-query";

/** 캐릭터 생성 */
export function usePostSketchCharacter() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: ({ nickname, file }: { nickname: string; file: File }) =>
      postSketchCharacter(nickname, file),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["SketchCharacter"] });
    },
    mutationKey: ["postSketchCharacter"],
  });
}
