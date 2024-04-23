import { useNavigate } from "react-router";
import { postLogout } from "../../api/Api";
import { Page_Url } from "../../router/Page_Url";

export function useLogout() {
  const navigate = useNavigate();

  return () => {
    postLogout().then(() => {
      localStorage.removeItem("refreshToken");
      localStorage.removeItem("accessToken");

      navigate(Page_Url.Main);
    });
  };
}
