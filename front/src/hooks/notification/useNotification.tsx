import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import {
  getNotification,
  getUncheckedNotification,
  putNotification,
} from "../../api/Api";

export default function useNotification() {
  return useQuery({
    queryKey: ["notificationList"],
    queryFn: () => getNotification(),
  });
}

export function useUncheckedNotification() {
  return useQuery({
    queryKey: ["uncheckedNotificationList"],
    queryFn: () => getUncheckedNotification(),
  });
}

export function useCheckNotification() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: () => putNotification(),
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ["uncheckedNotificationList"],
      });
    },
  });
}
