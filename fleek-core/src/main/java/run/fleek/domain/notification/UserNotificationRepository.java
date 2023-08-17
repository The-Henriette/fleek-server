package run.fleek.domain.notification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import run.fleek.enums.NotificationType;

import java.util.Optional;

@Repository
public interface UserNotificationRepository extends JpaRepository<UserNotification, Long>, UserNotificationRepositoryCustom {
  Optional<UserNotification> findByNotificationTypeAndNotificationKey(NotificationType notificationType, String notificationKey);
}
