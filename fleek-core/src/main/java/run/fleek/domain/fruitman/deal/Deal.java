package run.fleek.domain.fruitman.deal;

import lombok.*;
import org.springframework.util.CollectionUtils;
import run.fleek.application.fruitman.deal.dto.DealAddDto;
import run.fleek.common.jpa.CreatedAtListener;
import run.fleek.common.jpa.SystemMetadata;
import run.fleek.common.jpa.UpdatedAtListener;
import run.fleek.domain.fruitman.delivery.DeliveryAreaGroup;
import run.fleek.domain.fruitman.sku.Sku;
import run.fleek.enums.DealStatus;
import run.fleek.utils.JsonUtil;

import javax.persistence.*;
import java.util.Map;
import java.util.Optional;

@Data
@Builder
@Entity
@Table(name = "deal", schema = "public")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EntityListeners({CreatedAtListener.class, UpdatedAtListener.class})
public class Deal implements SystemMetadata {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "deal_seq")
  @SequenceGenerator(name = "deal_seq", sequenceName = "deal_seq", allocationSize = 1)
  @Column(name = "deal_id", nullable = false)
  private Long dealId;

  @Column(name = "deal_name")
  private String dealName;

  @Column(name = "market_price")
  private Integer marketPrice;

  @Column(name =  "sales_price")
  private Integer salesPrice;

  @Column(name = "delivery_price")
  private Integer deliveryPrice;

  @Column(name = "deal_thumbnail")
  private String dealThumbnail;

  @Column(name = "deal_images")
  private String dealImages;

  @Column(name = "delivery_methods")
  private String deliveryMethods;

  @Column(name = "deal_status")
  @Enumerated(EnumType.STRING)
  private DealStatus dealStatus;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "delivery_area_group_id")
  private DeliveryAreaGroup deliveryAreaGroup;

  @Column(name = "effected_at", nullable = false)
  private Long effectedAt;

  @Column(name = "expired_at", nullable = false)
  private Long expiredAt;

  @Column(name = "created_at", nullable = false)
  private Long createdAt;

  @Column(name = "updated_at", nullable = false)
  private Long updatedAt;

  public static Deal from(DealAddDto dealAddDto, Map<Long, Sku> skuMap, DeliveryAreaGroup deliveryAreaGroup) {
    return Deal.builder()
      .dealName(dealAddDto.getDealName())
      .marketPrice(dealAddDto.getMarketPrice())
      .salesPrice(dealAddDto.getSalesPrice())
      .deliveryPrice(dealAddDto.getDeliveryPrice())
      .dealStatus(DealStatus.PENDING)
      .dealThumbnail(Optional.ofNullable(dealAddDto.getDealThumbnail()).orElse(skuMap.get(dealAddDto.getDealSkus().get(0).getSkuId()).getSkuMainImage()))
      .dealImages(CollectionUtils.isEmpty(dealAddDto.getDealImages()) ?
        skuMap.get(dealAddDto.getDealSkus().get(0).getSkuId()).getSkuImages() : JsonUtil.write(dealAddDto.getDealImages()))
      .deliveryMethods(JsonUtil.write(dealAddDto.getDeliveryMethods()))
      .deliveryAreaGroup(deliveryAreaGroup)
      .build();
  }
}
