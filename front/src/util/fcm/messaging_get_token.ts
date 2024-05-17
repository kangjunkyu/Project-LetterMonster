import { getMessaging, getToken } from "firebase/messaging";
import { Firebase } from "./firebase";
import { FirebaseMessaging } from "@capacitor-firebase/messaging";
import {
  AppTrackingTransparency,
  AppTrackingStatusResponse,
} from "capacitor-plugin-app-tracking-transparency";

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

export async function GetTokenIOS() {
  const { token } = await FirebaseMessaging.getToken();
  localStorage.setItem("fcm_token", token);
  if ((await getStatus()).status === "notDetermined") {
    requestPermission();
  }
}

export async function getStatus(): Promise<AppTrackingStatusResponse> {
  const response = await AppTrackingTransparency.getStatus();

  return response;
}

export async function requestPermission(): Promise<AppTrackingStatusResponse> {
  const response = await AppTrackingTransparency.requestPermission();

  return response;
}

export default GetToken;
