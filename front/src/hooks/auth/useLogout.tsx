import { useNavigate } from "react-router";
import { postLogout } from "../../api/Api";
import { Page_Url } from "../../router/Page_Url";

export function useLogout() {
  const navigate = useNavigate();
  return () => {
    postLogout().then(() => {
      localStorage.removeItem("refreshToken");
      localStorage.removeItem("accessToken");
      localStorage.removeItem("fcm_token");
      navigate(Page_Url.Main);
    });
    useDeleteLog();
  };
}

export function useDeleteLog() {
  return () => {
    localStorage.removeItem("refreshToken");
    localStorage.removeItem("accessToken");
    localStorage.removeItem("fcm_token");
  };
}
