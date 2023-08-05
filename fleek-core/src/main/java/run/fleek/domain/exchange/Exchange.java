package run.fleek.domain.exchange;

import lombok.*;
import run.fleek.common.jpa.CreatedAtListener;
import run.fleek.common.jpa.SystemMetadata;
import run.fleek.common.jpa.UpdatedAtListener;
import run.fleek.domain.chat.Chat;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "exchange", schema = "public")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EntityListeners({CreatedAtListener.class, UpdatedAtListener.class})
public class Exchange implements SystemMetadata {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exchange_seq")
  @SequenceGenerator(name = "exchange_seq", sequenceName = "exchange_seq", allocationSize = 1)
  @Column(name = "exchange_id", nullable = false)
  private Long exchangeId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "chat_id")
  private Chat chat;

  @Column(name = "request_message_id")
  private String requestMessageId;

  @Column(name = "created_at", nullable = false)
  private Long createdAt;

  @Column(name = "updated_at", nullable = false)
  private Long updatedAt;
}
