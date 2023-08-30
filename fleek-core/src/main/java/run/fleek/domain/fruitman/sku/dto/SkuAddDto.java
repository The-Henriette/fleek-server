package run.fleek.domain.fruitman.sku.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkuAddDto {
  private String skuName;
  private String skuMainImage;
  private List<String> skuImages;
}
