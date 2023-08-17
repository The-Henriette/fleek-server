package run.fleek.domain.notification;

import com.google.common.collect.Maps;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class FirebaseNotificationService {

  private final FirebaseMessaging firebaseMessaging;

  public void sendNotification(String token, String title, String message, Map<String, String> data) {
    Notification notification = Notification.builder()
      .setTitle(title)
      .setBody(message)
      .build();

    Message fcmMessage = Message.builder()
      .setToken(token)
      .setNotification(notification)
      .putAllData(Objects.isNull(data) ? Maps.newHashMap() : data)
      .build();

    try {
      firebaseMessaging.send(fcmMessage);
    } catch (FirebaseMessagingException e) {
      throw new RuntimeException(e);
    }
  }
}
