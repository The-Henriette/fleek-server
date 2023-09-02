package run.fleek.application.fruitman.order.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartAddDto {

  private List<Long> dealIds;
  private String purchaseOption;
  private String cartType;

}
