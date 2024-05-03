import { useQuery } from "@tanstack/react-query";
import { searchUserNickname } from "../../api/Api";

export function useSearchUserNickname(nickname: string, options={}) {
  return useQuery({
    queryKey: ["friend", nickname],
    queryFn: () => searchUserNickname(nickname),
    enabled: !!nickname,
    ...options,
    
  });
}