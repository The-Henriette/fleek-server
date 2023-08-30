package run.fleek.domain.fruitman.deal;

import lombok.*;
import run.fleek.application.fruitman.deal.dto.PurchaseOptionDto;
import run.fleek.common.jpa.CreatedAtListener;
import run.fleek.common.jpa.SystemMetadata;
import run.fleek.common.jpa.UpdatedAtListener;
import run.fleek.enums.PurchaseOption;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "deal_purchase_option", schema = "public")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EntityListeners({CreatedAtListener.class, UpdatedAtListener.class})
public class DealPurchaseOption implements SystemMetadata {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "deal_purchase_option_seq")
  @SequenceGenerator(name = "deal_purchase_option_seq", sequenceName = "deal_purchase_option_seq", allocationSize = 1)
  @Column(name = "deal_purchase_option_id", nullable = false)
  private Long dealPurchaseOptionId;

  @JoinColumn(name = "deal_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private Deal deal;

  @Column(name = "purchase_option")
  @Enumerated(EnumType.STRING)
  private PurchaseOption purchaseOption;

  @Column(name = "price")
  private Integer price;

  @Column(name = "created_at", nullable = false)
  private Long createdAt;

  @Column(name = "updated_at", nullable = false)
  private Long updatedAt;

  public static DealPurchaseOption from(Deal deal, PurchaseOptionDto optionDto) {
    return DealPurchaseOption.builder()
        .deal(deal)
        .purchaseOption(PurchaseOption.valueOf(optionDto.getPurchaseTypeCode()))
        .price(optionDto.getPrice())
        .build();
  }

}
