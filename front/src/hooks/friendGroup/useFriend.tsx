import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import {
  deleteFriend,
  getFriendGroupList,
  postFriendGroupList,
} from "../../api/Api";

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

export function useDeleteFriend() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (friendId: number) => deleteFriend(friendId),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["friend"] });
    },
  });
}
