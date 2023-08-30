package run.fleek.domain.fruitman.tracking;

import lombok.*;
import run.fleek.common.jpa.CreatedAtListener;
import run.fleek.common.jpa.SystemMetadata;
import run.fleek.common.jpa.UpdatedAtListener;
import run.fleek.enums.ReceiptPurpose;
import run.fleek.enums.ReceiptTargetType;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "user_payment_receipt", schema = "public")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EntityListeners({CreatedAtListener.class, UpdatedAtListener.class})
public class UserPaymentReceipt implements SystemMetadata {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_payment_receipt_seq")
  @SequenceGenerator(name = "user_payment_receipt_seq", sequenceName = "user_payment_receipt_seq", allocationSize = 1)
  @Column(name = "user_payment_receipt_id", nullable = false)
  private Long userPaymentReceiptId;

  @JoinColumn(name = "user_payment_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private UserPayment userPayment;

  @Column(name = "receipt_purpose")
  @Enumerated(EnumType.STRING)
  private ReceiptPurpose receiptPurpose;

  @Column(name = "receipt_target_type")
  @Enumerated(EnumType.STRING)
  private ReceiptTargetType receiptTargetType;

  @Column(name = "receipt_target")
  private String receiptTarget;

  @Column(name = "created_at", nullable = false)
  private Long createdAt;

  @Column(name = "updated_at", nullable = false)
  private Long updatedAt;

}
