package run.fleek.application.fruitman.order.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderPageDto {

  private Integer page;
  private Long totalSize;
  private List<OrderDto> orders;
}
