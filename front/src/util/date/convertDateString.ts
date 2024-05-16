import { useTranslation } from "react-i18next";

export default function convertDateString(dateString: string) {
  const { t } = useTranslation();
  const date = new Date(dateString);
  const now = new Date();
  const diffTime = Math.abs(now.getTime() - date.getTime()); // 밀리초 단위 차이
  const diffMinutes = Math.floor(diffTime / (1000 * 60)); // 분 단위 차이
  const diffHours = Math.floor(diffTime / (1000 * 60 * 60)); // 시간 단위 차이
  const diffDays = Math.floor(diffTime / (1000 * 60 * 60 * 24)); // 일 단위 차이
  if (dateString === null) {
    return "No date";
  }
  if (diffMinutes < 60) {
    return `${diffMinutes}${t("date.minute")}`;
  } else if (diffHours < 24) {
    return `${diffHours}${t("date.hour")}`;
  } else if (diffDays <= 7) {
    return `${diffDays}${t("date.day")}`;
  } else {
    const year = date.getFullYear();
    const month = date.getMonth() + 1; // 월은 0부터 시작하므로 1을 더해줍니다.
    const day = date.getDate();

    return `${year}${t("date.year")}${month}${t("date.month")}${day}${t(
      "date.days"
    )}`;
  }
}
