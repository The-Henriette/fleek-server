package run.fleek.domain.fruitman.tracking;

import lombok.*;
import run.fleek.common.jpa.CreatedAtListener;
import run.fleek.common.jpa.SystemMetadata;
import run.fleek.common.jpa.UpdatedAtListener;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "user_delivery_detail", schema = "public")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EntityListeners({CreatedAtListener.class, UpdatedAtListener.class})
public class UserDeliveryDetail implements SystemMetadata {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_delivery_detail_seq")
  @SequenceGenerator(name = "user_delivery_detail_seq", sequenceName = "user_delivery_detail_seq", allocationSize = 1)
  @Column(name = "user_delivery_detail_id", nullable = false)
  private Long userDeliveryDetailId;

  @JoinColumn(name = "user_deal_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private UserDeal userDeal;

  @Column(name = "recipient_name")
  private String recipientName;

  @Column(name = "recipient_phone_number")
  private String recipientPhoneNumber;

  @Column(name = "postal_code")
  private String postalCode;

  @Column(name = "main_address")
  private String mainAddress;

  @Column(name = "sub_address")
  private String subAddress;

  @Column(name = "delivery_memo")
  private String deliveryMemo;

  @Column(name = "created_at", nullable = false)
  private Long createdAt;

  @Column(name = "updated_at", nullable = false)
  private Long updatedAt;

}
