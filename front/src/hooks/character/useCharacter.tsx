import { useMutation, useQueryClient } from "@tanstack/react-query";
import {
  cancelCharacter,
  deleteCharacter,
  patchCharacterNickname,
  patchMainCharacter,
} from "../../api/Api";
import { useNavigate } from "react-router";
import { Page_Url } from "../../router/Page_Url";

/** 캐릭터 닉네임 설정 */
export function usePatchCharacterNickname() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: ({
      characterId,
      nickname,
    }: {
      characterId: number;
      nickname: string;
    }) => patchCharacterNickname(characterId, nickname),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["Character"] });
    },
  });
}

/** 대표 캐릭터 선정 */
export function useSelectMainCharacter() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (characterId: number) => patchMainCharacter(characterId),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["Character"] });
    },
  });
}

/** 캐릭터 생성 취소 */
export function useCancelCharacter() {
  const navigate = useNavigate();
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (characterId: number) => cancelCharacter(characterId),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["Character"] });
      navigate(Page_Url.Sketchbook);
    },
  });
}

/** 캐릭터 삭제 */
export function useDeleteCharacter() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (characterId: number) => deleteCharacter(characterId),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["Character"] });
    },
  });
}
