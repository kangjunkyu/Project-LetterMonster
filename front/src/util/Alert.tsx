import AlertNotice from "../components/atoms/notice/AlertNotice";

interface Props {
  content: string;
}

function Alert({ content }: Props) {
  console.log("되는거아냐?");
  return () => <AlertNotice content={content} />;
}

export default Alert;
