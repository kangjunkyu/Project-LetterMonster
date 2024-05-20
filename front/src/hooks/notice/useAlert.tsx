import { useContext, createContext, useState } from "react";
import AlertNotice from "../../components/atoms/notice/AlertNotice";

// Alert context와 provider를 생성합니다.
const AlertContext = createContext({
  content: "",
  showAlert: (message: string) => {
    message;
  },
  hideAlert: () => {},
});

export const useAlert = () => useContext(AlertContext);

export const AlertProvider = ({ children }: any) => {
  const [alertContent, setAlertContent] = useState("");
  const [showing, setShowing] = useState(false);

  const showAlert = (message: any) => {
    setAlertContent(message);
    setShowing(true);

    // 추가적인 로직이 필요하다면 여기에 작성...
    setTimeout(() => {
      hideAlert();
    }, 3000);
  };

  const hideAlert = () => {
    setAlertContent("");
    setShowing(false);
  };
  return (
    <AlertContext.Provider
      value={{ content: alertContent, showAlert, hideAlert }}
    >
      {children}
      {showing && <AlertNotice content={alertContent} />}
    </AlertContext.Provider>
  );
};
