import { useNavigate } from "react-router";
import { deleteUser } from "../../api/Api";
import { Page_Url } from "../../router/Page_Url";

export function useDeleteUser() {
  const navigate = useNavigate();

  return () => {
    deleteUser().then(() => {
      localStorage.removeItem("refreshToken");
      localStorage.removeItem("accessToken");
      localStorage.removeItem("fcm_token");

      navigate(Page_Url.Main);
    });
  };
}
