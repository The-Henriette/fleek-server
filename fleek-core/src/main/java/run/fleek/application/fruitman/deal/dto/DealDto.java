package run.fleek.application.fruitman.deal.dto;

import lombok.*;
import run.fleek.domain.fruitman.deal.vo.DealVo;
import run.fleek.enums.DeliveryMethod;
import run.fleek.utils.JsonUtil;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DealDto {
  private Long dealId;
  private String dealName;
  private Integer originalPrice;
  private Integer salesPrice;
  private Integer discountRate;
  private List<String> deliveryMethods;
  private String deliveryAreas;
  private Integer requiredQuantity;
  private Integer currentQuantity;
  private Integer remainingQuantity;
  private String dealThumbnail;
  private Long effectedAt;
  private Long expiredAt;

  public static DealDto from(DealVo vo) {
    return DealDto.builder()
      .dealId(vo.getDealId())
      .dealName(vo.getDealName())
      .originalPrice(vo.getOriginalPrice())
      .salesPrice(vo.getSalesPrice())
      .discountRate((int) ((1 - (double) vo.getSalesPrice() / vo.getOriginalPrice()) * 100))
      .deliveryMethods(JsonUtil.readList(vo.getDeliveryMethodString(), String.class).stream().map(s -> DeliveryMethod.valueOf(s).getLabel()).collect(Collectors.toList()))
      .deliveryAreas(vo.getDeliveryAreaGroupName())
      .requiredQuantity(vo.getRequiredQuantity())
      .currentQuantity(vo.getCurrentQuantity())
      .remainingQuantity(vo.getRequiredQuantity() - vo.getCurrentQuantity())
      .dealThumbnail(vo.getDealThumbnail())
      .effectedAt(vo.getEffectedAt())
      .expiredAt(vo.getExpiredAt())
      .build();
  }
}
