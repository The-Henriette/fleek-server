package run.fleek.application.fruitman.deal.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DealAddDto {
  private String dealName;
  private List<DealSkuDto> dealSkus;
  private Integer marketPrice;
  private Integer salesPrice;
  private Integer deliveryPrice;
  private String dealThumbnail;
  private List<String> dealImages;
  private List<String> deliveryMethods;
  private Long deliveryGroupId;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
  private Date targetDate;
  private Integer requiredQuantity;
  private Integer quantityPerUser;
  private List<PurchaseOptionDto> purchaseOptions;
}
