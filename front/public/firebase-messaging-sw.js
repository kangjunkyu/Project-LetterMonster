// 푸시 알림 관련 기능을 지원하는지 확인
if ("serviceWorker" in navigator && "PushManager" in window) {
  // Service Worker 등록
  navigator?.serviceWorker
    .register("/sw.js")
    .then(function (registration) {
      // 푸시 이벤트 리스너 추가
      self.addEventListener("push", function (event) {
        const payload = event.data.json();

        const title = payload.notification.title;
        const options = {
          body: payload.notification.body,
          icon: payload.notification.icon,
          data: payload.notification.click_action,
        };

        event.waitUntil(self.registration.showNotification(title, options));
      });

      // 알림 클릭 이벤트 리스너 추가
      self.addEventListener("notificationclick", function (event) {
        event.notification.close();
        event.waitUntil(clients.openWindow(event.notification.data));
      });
    })
    .catch(function (error) {
      console.error("Service Worker 등록 실패:", error);
    });
} else {
  // Service Worker 또는 푸시 알림을 지원하지 않는 환경 (예: iOS WKWebView)
  console.log("이 환경은 Service Worker 또는 푸시 알림을 지원하지 않습니다.");
}
