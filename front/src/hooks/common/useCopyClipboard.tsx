import { useTranslation } from "react-i18next";
import { useAlert } from "../notice/useAlert";

function useCopyClipboard() {
  const { showAlert } = useAlert();
  const { t } = useTranslation();
  return (textToCopy: string) =>
    navigator?.clipboard
      ?.writeText(textToCopy)
      .then(() => {
        // 복사가 성공적으로 이루어졌을 때의 로직
        showAlert(`${t("notification.copy")}`);
      })
      .catch(() => {
        // 에러 처리
        showAlert(`${t("notification.retry")}`);
      });
}

export default useCopyClipboard;
