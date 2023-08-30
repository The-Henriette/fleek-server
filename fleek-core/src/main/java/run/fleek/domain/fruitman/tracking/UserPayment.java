package run.fleek.domain.fruitman.tracking;

import lombok.*;
import run.fleek.common.jpa.CreatedAtListener;
import run.fleek.common.jpa.SystemMetadata;
import run.fleek.common.jpa.UpdatedAtListener;
import run.fleek.enums.PaymentMethod;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "user_payment", schema = "public")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EntityListeners({CreatedAtListener.class, UpdatedAtListener.class})
public class UserPayment implements SystemMetadata {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_payment_seq")
  @SequenceGenerator(name = "user_payment_seq", sequenceName = "user_payment_seq", allocationSize = 1)
  @Column(name = "user_payment_id", nullable = false)
  private Long userPaymentId;

  @JoinColumn(name = "user_deal_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private UserDeal userDeal;

  @Column(name = "payment_method")
  @Enumerated(EnumType.STRING)
  private PaymentMethod paymentMethod;

  @Column(name = "amount")
  private Integer amount;

  @Column(name = "payment_due")
  private Long paymentDue;

  @Column(name = "refund_due")
  private Long refundDue;

  @Column(name = "refund_amount")
  private Integer refundAmount;

  @Column(name = "created_at", nullable = false)
  private Long createdAt;

  @Column(name = "updated_at", nullable = false)
  private Long updatedAt;

}
