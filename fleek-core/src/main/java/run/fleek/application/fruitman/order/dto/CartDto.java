package run.fleek.application.fruitman.order.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {

  private Long cartId;
  private String purchaseOption;
  private String cartType;
  private String orderId;
  private List<Long> dealIds;
}
