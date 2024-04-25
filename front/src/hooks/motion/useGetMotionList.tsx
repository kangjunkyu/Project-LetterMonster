import { useQuery } from "@tanstack/react-query";
import { getMotionList } from "../../api/Api";

export function useGetMotionList(){
    return useQuery({
        queryKey: ["motionList"],
        queryFn: () => getMotionList(),
    })
}