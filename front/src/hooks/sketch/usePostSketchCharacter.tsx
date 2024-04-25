import { postSketchCharacter } from "../../api/Api";
import { useMutation, useQueryClient } from "@tanstack/react-query";

export function usePostSketchCharacter(
  onComplete: (data: any, uri: string, nickname: string) => void
) {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: ({
      nickname,
      file,
    }: {
      nickname: string;
      file: File;
      uri: string;
    }) => postSketchCharacter(nickname, file),

    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["SketchCharacter"] });
    },
    onSettled: (data, error, variables) => {
      if (!error && variables) {
        onComplete(data, variables.uri, variables.nickname);
      }
    },
    mutationKey: ["postSketchCharacter"],
  });
}
