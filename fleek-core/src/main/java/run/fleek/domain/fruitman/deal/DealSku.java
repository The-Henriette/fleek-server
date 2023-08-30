package run.fleek.domain.fruitman.deal;

import lombok.*;
import run.fleek.application.fruitman.deal.dto.DealSkuDto;
import run.fleek.common.jpa.CreatedAtListener;
import run.fleek.common.jpa.SystemMetadata;
import run.fleek.common.jpa.UpdatedAtListener;
import run.fleek.domain.fruitman.sku.Sku;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "deal_sku", schema = "public")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EntityListeners({CreatedAtListener.class, UpdatedAtListener.class})
public class DealSku implements SystemMetadata {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "deal_sku_seq")
  @SequenceGenerator(name = "deal_sku_seq", sequenceName = "deal_sku_seq", allocationSize = 1)
  @Column(name = "deal_sku_id", nullable = false)
  private Long dealSkuId;

  @JoinColumn(name = "deal_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private Deal deal;

  @JoinColumn(name = "sku_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private Sku sku;

  @Column(name = "produced_location")
  private String producedLocation;

  @Column(name = "produced_at")
  private Long producedAt;

  @Column(name = "produced_by")
  private String producedBy;

  @Column(name = "created_at", nullable = false)
  private Long createdAt;

  @Column(name = "updated_at", nullable = false)
  private Long updatedAt;

  public static DealSku from(DealSkuDto dealSkuDto, Deal deal, Sku sku) {
    return DealSku.builder()
      .deal(deal)
      .sku(sku)
      .producedLocation(dealSkuDto.getProducedLocation())
      .producedAt(dealSkuDto.getProducedAt().getTime())
      .producedBy(dealSkuDto.getProducedBy())
      .build();
  }

}
