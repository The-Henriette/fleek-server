package run.fleek.api.controller.fruitman.order;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import run.fleek.application.fruitman.order.OrderApplication;
import run.fleek.application.fruitman.order.dto.*;
import run.fleek.common.client.toss.dto.TossPaymentResponseDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FruitManOrderController {

  private final OrderApplication orderApplication;

  @PostMapping("/fruitman/cart")
  public CartDto addCart(@RequestBody CartAddDto cartAddDto) {
    return orderApplication.addCart(cartAddDto);
  }

  @GetMapping("/fruitman/cart/{cartId}")
  public CartDto getCart(@PathVariable Long cartId) {
    return orderApplication.getCart(cartId);
  }

  @PostMapping("/fruitman/order")
  public OrderDto addOrder(@RequestBody OrderAddDto orderAddDto) {
    return orderApplication.addOrder(orderAddDto);
  }

  @GetMapping("/fruitman/order")
  public OrderPageDto listOrders(@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "0") int page) {
    return orderApplication.listOrders(size, page);
  }

  @GetMapping("/fruitman/order/{orderId}")
  public OrderDetailDto getOrder(@PathVariable String orderId) {
    return orderApplication.getOrder(orderId);
  }

  @PostMapping("/fruitman/order/{orderId}/cancel")
  public void cancelOrder(@PathVariable String orderId) {
    orderApplication.cancelOrder(orderId);
  }

  @PostMapping("/fruitman/order/{orderId}/payment/{status}")
  public TossPaymentResponseDto updatePaymentStatus(@PathVariable String orderId,
                                                    @PathVariable String status,
                                                    @RequestBody PaymentRequestDto dto) {
    if (status.equals("success")) {
      return orderApplication.updatePaymentStatus(orderId, dto);
    } else {
      orderApplication.cancelOrder(orderId);
      return null;
    }
  }
}
