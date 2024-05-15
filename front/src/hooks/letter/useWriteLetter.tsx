import { useNavigate } from "react-router-dom";

import { postLetter } from "../../api/Api";
import { useAlert } from "../notice/useAlert";
import { Page_Url } from "../../router/Page_Url";
import { useTranslation } from "react-i18next";

interface Props {
  content: string;
  target: number;
  motionId: number;
  characterMotionId: number;
  setContent: (content: string) => void;
  isLoad: boolean;
  characterId: number;
  uuid: string;
}

function useWriteLetter() {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const { showAlert } = useAlert();
  return ({
    content,
    target,
    motionId,
    characterMotionId,
    setContent,
    isLoad,
    characterId,
    uuid,
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
            showAlert(`${t("notification.letterSuccess")}`);
            navigate(`${Page_Url.Sketchbook}${uuid}`);
          }
        })
        .catch(() => {
          showAlert(`${t("notification.letterFail")}`);
        });
    } else if (characterId === 0) {
      showAlert(`${t("notification.selectCharacter")}`);
    } else if (isLoad || characterMotionId === 0 || !characterMotionId) {
      showAlert(`${t("notification.motionLoading")}`);
    } else if (!content) {
      showAlert(`${t("notification.noContent")}`);
    } else if (!target) {
      showAlert(`${t("notification.where")}`);
    } else if (!motionId) {
      showAlert(`${t("notification.selectMotion")}`);
    } else {
      showAlert(`${t("notification.error")}`);
    }
  };
}

export default useWriteLetter;
