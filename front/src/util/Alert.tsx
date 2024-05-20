import AlertNotice from "../components/atoms/notice/AlertNotice";

interface Props {
  content: string;
}

function Alert({ content }: Props) {
  return () => <AlertNotice content={content} />;
}

export default Alert;
