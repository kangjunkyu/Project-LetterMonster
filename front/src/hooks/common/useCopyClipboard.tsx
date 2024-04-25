function useCopyClipboard() {
  return (textToCopy: string) =>
    navigator.clipboard
      .writeText(textToCopy)
      .then(() => {
        // 복사가 성공적으로 이루어졌을 때의 로직
        console.log("Text successfully copied to clipboard");
      })
      .catch((err) => {
        // 에러 처리
        console.error("Failed to copy text to clipboard", err);
      });
}

export default useCopyClipboard;
