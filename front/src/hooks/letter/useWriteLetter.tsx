import { useNavigate } from "react-router-dom";

import { postLetter } from "../../api/Api";
import { useAlert } from "../notice/useAlert";
import { Page_Url } from "../../router/Page_Url";

interface Props {
  content: string;
  target: number;
  motionId: number;
  characterMotionId: number;
  setContent: (content: string) => void;
  isLoad: boolean;
}

function useWriteLetter() {
  const navigate = useNavigate();
  const { showAlert } = useAlert();
  return ({
    content,
    target,
    motionId,
    characterMotionId,
    setContent,
    isLoad,
  }: Props) => {
    if (
      content &&
      target &&
      motionId &&
      characterMotionId != 0 &&
      characterMotionId
    ) {
      // 값 유무 확인
      postLetter(content, target, characterMotionId)
        .then((res) => {
          if (res.statusCode === 201) {
            setContent("");
            showAlert("편지를 보냈어요!");
            navigate(Page_Url.SketchbookList);
          }
        })
        .catch(() => {
          showAlert("편지 전송에 실패했습니다. 다시 시도해주세요");
        });
    } else if (isLoad) {
      showAlert("캐릭터가 아직 동작을 연습중이에요");
    } else {
      showAlert("보낼 편지를 확인해주세요");
    }
  };
}

export default useWriteLetter;
