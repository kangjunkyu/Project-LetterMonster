import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { getFriendGroupList, postFriendGroupList } from "../../api/Api";

export function useGetFriendGroupList() {
  return useQuery({
    queryKey: ["friend"],
    queryFn: () => getFriendGroupList(),
  });
}

export function usePostFriend() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (friendId: number) => postFriendGroupList(friendId),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["friend"] });
    },
  });
}
