import { useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { Page_Url } from "../../router/Page_Url";

function useLineCallback() {
  const navigate = useNavigate();
  const location = useLocation();

  useEffect(() => {
    const queryParams = new URLSearchParams(location.search);
    const accessToken = queryParams.get("accessToken");
    const refreshToken = queryParams.get("refreshToken");

    if (accessToken && refreshToken) {
      localStorage.setItem("accessToken", accessToken);
      localStorage.setItem("refreshToken", refreshToken);
      navigate(Page_Url.Main, { replace: true });
    }
  }, [location]);
}

export default useLineCallback;
