package run.fleek.domain.fruitman.deal;

import lombok.*;
import run.fleek.application.fruitman.order.dto.CartAddDto;
import run.fleek.common.jpa.CreatedAtListener;
import run.fleek.common.jpa.SystemMetadata;
import run.fleek.common.jpa.UpdatedAtListener;
import run.fleek.domain.fruitman.user.FruitManUser;
import run.fleek.enums.CartType;
import run.fleek.enums.PurchaseOption;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "cart", schema = "public")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EntityListeners({CreatedAtListener.class, UpdatedAtListener.class})
public class Cart implements SystemMetadata {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_seq")
  @SequenceGenerator(name = "cart_seq", sequenceName = "cart_seq", allocationSize = 1)
  @Column(name = "cart_id", nullable = false)
  private Long cartId;

  @Column(name = "cart_type")
  @Enumerated(EnumType.STRING)
  private CartType cartType;

  @Column(name = "purchase_option")
  @Enumerated(EnumType.STRING)
  private PurchaseOption purchaseOption;

  @JoinColumn(name = "fruit_man_user_id", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private FruitManUser fruitManUser;

  @Column(name = "order_id")
  private String orderId;

  @Column(name = "created_at", nullable = false)
  private Long createdAt;

  @Column(name = "updated_at", nullable = false)
  private Long updatedAt;
}
