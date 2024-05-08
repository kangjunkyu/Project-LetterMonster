import { initializeApp } from "firebase/app"; // fcm

const firebaseConfig = {
  apiKey: import.meta.env.VITE_FCM_API_KEY,
  authDomain: import.meta.env.VITE_FCM_AUTHDOMAIN,
  projectId: import.meta.env.VITE_FCM_PROJECTID,
  storageBucket: import.meta.env.VITE_FCM_STORAGEBUCKET,
  messagingSenderId: import.meta.env.VITE_FCM_MESSAGINGSENDERID,
  appId: import.meta.env.VITE_FCM_APPID,
  measurementId: import.meta.env.VITE_FCM_MEASUREMENTID,
};

// Initialize Firebase
export const Firebase = initializeApp(firebaseConfig);
