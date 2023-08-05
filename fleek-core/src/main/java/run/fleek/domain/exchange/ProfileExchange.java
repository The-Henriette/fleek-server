package run.fleek.domain.exchange;

import lombok.*;
import run.fleek.common.jpa.CreatedAtListener;
import run.fleek.common.jpa.SystemMetadata;
import run.fleek.common.jpa.UpdatedAtListener;
import run.fleek.domain.profile.Profile;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "profile_exchange", schema = "public")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EntityListeners({CreatedAtListener.class, UpdatedAtListener.class})
public class ProfileExchange implements SystemMetadata {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_exchange_seq")
  @SequenceGenerator(name = "profile_exchange_seq", sequenceName = "profile_exchange_seq", allocationSize = 1)
  @Column(name = "profile_exchange_id", nullable = false)
  private Long profileExchangeId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "profile_id")
  private Profile profile;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "exchange_id")
  private Exchange exchange;

  @Column(name = "watched")
  private Boolean watched;

  @Column(name = "accepted")
  private Boolean accepted;

  @Column(name = "rejected")
  private Boolean rejected;

  @Column(name = "created_at", nullable = false)
  private Long createdAt;

  @Column(name = "updated_at", nullable = false)
  private Long updatedAt;

  public static ProfileExchange from(Profile profile, Exchange exchange, Boolean accepted) {
    return ProfileExchange.builder()
        .profile(profile)
        .exchange(exchange)
        .watched(false)
        .accepted(accepted)
        .rejected(false)
        .build();
  }
}
