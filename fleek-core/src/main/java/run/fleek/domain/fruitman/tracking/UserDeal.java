package run.fleek.domain.fruitman.tracking;

import lombok.*;
import run.fleek.common.jpa.CreatedAtListener;
import run.fleek.common.jpa.SystemMetadata;
import run.fleek.common.jpa.UpdatedAtListener;
import run.fleek.domain.fruitman.deal.Cart;
import run.fleek.domain.fruitman.deal.Deal;
import run.fleek.domain.fruitman.user.FruitManUser;
import run.fleek.enums.DealTrackingStatus;
import run.fleek.enums.PurchaseOption;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "user_deal", schema = "public")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EntityListeners({CreatedAtListener.class, UpdatedAtListener.class})
public class UserDeal implements SystemMetadata {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_deal_seq")
  @SequenceGenerator(name = "user_deal_seq", sequenceName = "user_deal_seq", allocationSize = 1)
  @Column(name = "user_deal_id", nullable = false)
  private Long userDealId;

  @JoinColumn(name = "fruit_man_user_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private FruitManUser fruitManUser;

  @JoinColumn(name = "deal_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private Deal deal;

  @JoinColumn(name = "cart_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private Cart cart;

  @Column(name = "order_id")
  private String orderId;

  @Column(name = "ordered_at")
  private Long orderedAt;

  @Column(name = "paid_at")
  private Long paidAt;

  @Column(name = "pdd")
  private Long pdd;

  @Column(name = "tracking_status")
  @Enumerated(EnumType.STRING)
  private DealTrackingStatus trackingStatus;

  @Column(name = "purchase_option")
  @Enumerated(EnumType.STRING)
  private PurchaseOption purchaseOption;

  @Column(name = "created_at", nullable = false)
  private Long createdAt;

  @Column(name = "updated_at", nullable = false)
  private Long updatedAt;

}
