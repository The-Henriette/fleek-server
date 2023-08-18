package run.fleek.domain.users.wallet;

import lombok.*;
import run.fleek.common.jpa.CreatedAtListener;
import run.fleek.common.jpa.SystemMetadata;
import run.fleek.common.jpa.UpdatedAtListener;
import run.fleek.domain.users.FleekUser;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "user_wallet", schema = "public")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EntityListeners({CreatedAtListener.class, UpdatedAtListener.class})
public class UserWallet implements SystemMetadata {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_wallet_seq")
  @SequenceGenerator(name = "user_wallet_seq", sequenceName = "user_wallet_seq", allocationSize = 1)
  @Column(name = "user_wallet_id", nullable = false)
  private Long userWalletId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fleek_user_id")
  private FleekUser fleekUser;

  @Column(name = "amount")
  private Long amount;

  @Column(name = "created_at", nullable = false)
  private Long createdAt;

  @Column(name = "updated_at", nullable = false)
  private Long updatedAt;

  public void purchase(Long increment) {
    this.amount += increment;
  }
}
