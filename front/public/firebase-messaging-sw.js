if (
  "serviceWorker" in navigator &&
  "PushManager" in window &&
  !(
    /iPad|iPhone|iPod/.test(navigator.userAgent) ||
    (navigator.userAgent.includes("Mac") && "ontouchend" in document) ||
    (window.webkit && window.webkit.messageHandlers) ||
    /KAKAOTALK|NAVER|FB_IAB|Instagram|Line|WebView/i.test(
      navigator.userAgent || window.opera
    )
  )
) {
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

  self.addEventListener("notificationclick", function (event) {
    event.notification.close();
    event.waitUntil(clients.openWindow(event.notification.data));
  });
} else {
  console.log("Service Worker 등록을 생략합니다.");
}
