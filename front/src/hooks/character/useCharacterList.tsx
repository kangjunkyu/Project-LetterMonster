import { useQuery } from "@tanstack/react-query";
import { getCharacterList } from "../../api/Api";

/** 캐릭터 리스트 조회 */
export function useGetCharacterList() {
  return useQuery({
    queryKey: ["Character"],
    queryFn: () => getCharacterList(),
  });
}
