package run.fleek.domain.fruitman.user;

import lombok.*;
import run.fleek.common.jpa.CreatedAtListener;
import run.fleek.common.jpa.SystemMetadata;
import run.fleek.common.jpa.UpdatedAtListener;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "user_refund_info", schema = "public")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EntityListeners({CreatedAtListener.class, UpdatedAtListener.class})
public class UserRefundInfo implements SystemMetadata {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_refund_info_seq")
  @SequenceGenerator(name = "user_refund_info_seq", sequenceName = "user_refund_info_seq", allocationSize = 1)
  @Column(name = "user_refund_info_id", nullable = false)
  private Long userRefundInfoId;

  @JoinColumn(name = "fruit_man_user_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private FruitManUser fruitManUser;

  @Column(name = "refund_account_name")
  private String refundAccountName;

  @Column(name = "refund_bank_name")
  private String refundBankName;

  @Column(name = "refund_account_number")
  private String refundAccountNumber;

  @Column(name = "created_at", nullable = false)
  private Long createdAt;

  @Column(name = "updated_at", nullable = false)
  private Long updatedAt;

}
