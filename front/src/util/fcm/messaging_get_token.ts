import { getMessaging, getToken } from "firebase/messaging";
import { Firebase } from "./firebase";

function GetToken() {
  const firebaseMessaging = getMessaging(Firebase);

  getToken(firebaseMessaging, { vapidKey: import.meta.env.VITE_VAPID_KEY })
    .then((currentToken) => {
      if (currentToken) {
        localStorage.setItem("fcm_token", currentToken);
      } else {
        // Show permission request UI
        console.log(
          "No registration token available. Request permission to generate one."
        );
        // ...
      }
    })
    .catch((err) => {
      console.log("An error occurred while retrieving token. ", err);
      // ...
    });
}

export default GetToken;
