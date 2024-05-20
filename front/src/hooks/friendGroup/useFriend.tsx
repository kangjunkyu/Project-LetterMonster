import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import {
  deleteFriend,
  getFriendGroupList,
  postFriendGroupList,
} from "../../api/Api";
import useCheckTokenExpiration from "../auth/useCheckTokenExpiration";

export function useGetFriendGroupList() {
  const checkToken = useCheckTokenExpiration();
  return useQuery({
    queryKey: ["friend"],
    queryFn: () => {
      if (checkToken(localStorage.getItem("accessToken"))) {
        return getFriendGroupList();
      } else {
        return Promise.resolve({});
      }
    },
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
