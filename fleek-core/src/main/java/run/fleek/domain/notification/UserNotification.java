package run.fleek.domain.notification;

import lombok.*;
import run.fleek.common.jpa.CreatedAtListener;
import run.fleek.common.jpa.SystemMetadata;
import run.fleek.common.jpa.UpdatedAtListener;
import run.fleek.domain.users.FleekUser;
import run.fleek.enums.NotificationType;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "user_notification", schema = "public")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EntityListeners({CreatedAtListener.class, UpdatedAtListener.class})
public class UserNotification implements SystemMetadata {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_notification_seq")
  @SequenceGenerator(name = "user_notification_seq", sequenceName = "user_notification_seq", allocationSize = 1)
  @Column(name = "user_notification_id", nullable = false)
  private Long userNotificationId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fleek_user_id")
  private FleekUser fleekUser;

  @Column(name = "title")
  private String title;

  @Column(name = "body")
  private String body;

  @Column(name = "notification_type")
  @Enumerated(EnumType.STRING)
  private NotificationType notificationType;

  @Column(name = "notification_key")
  private String notificationKey;

  @Column(name = "created_at", nullable = false)
  private Long createdAt;

  @Column(name = "updated_at", nullable = false)
  private Long updatedAt;

}
