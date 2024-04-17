import useLineCallback from "../../../hooks/auth/useLineCallback";
import LoadingSpinner from "../../atoms/loadingSpinner/LoadingSpinner";

function LineCallback() {
  useLineCallback();
  return <LoadingSpinner />;
}

export default LineCallback;
