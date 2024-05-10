import { useQuery } from "@tanstack/react-query";
import { getUserNickname } from "../../api/Api";

export function useGetUserNickname() {
  return useQuery({
    queryKey: ["usernickname"],
    queryFn: () =>
      localStorage.getItem("accessToken")
        ? getUserNickname()
        : Promise.resolve({}),
  });
}
