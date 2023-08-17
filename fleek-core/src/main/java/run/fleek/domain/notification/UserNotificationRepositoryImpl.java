package run.fleek.domain.notification;

import com.querydsl.core.QueryResults;
import run.fleek.application.notification.dto.NotificationDataDto;
import run.fleek.application.notification.dto.NotificationPageDto;
import run.fleek.configuration.database.FleekQueryDslRepositorySupport;
import run.fleek.domain.users.QFleekUser;

import java.util.List;
import java.util.stream.Collectors;

public class UserNotificationRepositoryImpl extends FleekQueryDslRepositorySupport implements UserNotificationRepositoryCustom {
  public UserNotificationRepositoryImpl() {
    super(UserNotification.class);
  }

  QUserNotification qUserNotification = QUserNotification.userNotification;
  QFleekUser qFleekUser = QFleekUser.fleekUser;

  @Override
  public NotificationPageDto pageNotifications(Long userId, Integer page, Integer size) {

    QueryResults<UserNotification> queryResults =
      from(qUserNotification)
        .innerJoin(qUserNotification.fleekUser, qFleekUser)
        .where(qFleekUser.fleekUserId.eq(userId))
        .select(qUserNotification)
        .orderBy(qUserNotification.createdAt.desc())
        .offset((long) page * size)
        .limit(size)
        .fetchResults();

    List<UserNotification> results = queryResults.getResults();
    return NotificationPageDto.builder()
      .page(page)
      .totalSize(queryResults.getTotal())
      .notifications(results.stream()
        .map(result -> NotificationDataDto.builder()
          .type(result.getNotificationType().name())
          .title(result.getTitle())
          .body(result.getBody())
          .notificationKey(result.getNotificationKey())
          .build())
        .collect(Collectors.toList()))
      .build();
  }
}
