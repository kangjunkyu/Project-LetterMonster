import { useAlert } from "../notice/useAlert";

function useCopyClipboard() {
  const { showAlert } = useAlert();
  return (textToCopy: string) =>
    navigator.clipboard
      ?.writeText(textToCopy)
      .then(() => {
        // 복사가 성공적으로 이루어졌을 때의 로직
        showAlert("복사 완료");
      })
      .catch(() => {
        // 에러 처리
        showAlert("다시 시도해주세요");
      });
}

export default useCopyClipboard;
