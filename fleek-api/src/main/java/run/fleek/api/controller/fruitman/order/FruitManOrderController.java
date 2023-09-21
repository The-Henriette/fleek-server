package run.fleek.api.controller.fruitman.order;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import run.fleek.application.fruitman.notification.FruitManNotificationApplication;
import run.fleek.application.fruitman.order.OrderApplication;
import run.fleek.application.fruitman.order.dto.*;
import run.fleek.common.client.toss.dto.TossPaymentResponseDto;
import run.fleek.domain.fruitman.tracking.UserDeal;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FruitManOrderController {

  private final OrderApplication orderApplication;
  private final FruitManNotificationApplication fruitManNotificationApplication;

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
     UserDeal userDeal = orderApplication.cancelOrder(orderId);
      fruitManNotificationApplication.sendNotificationOnCancel(userDeal);
  }

  @PostMapping("/fruitman/order/{orderId}/payment/{status}")
  public PaymentResponseDto updatePaymentStatus(@PathVariable String orderId,
                                                    @PathVariable String status,
                                                    @RequestBody PaymentRequestDto dto) {
    if (status.equals("success")) {
      PaymentResponseDto response = orderApplication.updatePaymentStatus(orderId, dto);
      fruitManNotificationApplication.sendNotificationOnTeamPurchasePending(response.getOrderId());

      if (response.getSuccess() && response.getRemainingCount() <= 0) {
        fruitManNotificationApplication.sendNotificationOnTeamPurchaseSuccess(response.getOrderId());
      }

      return response;
    } else {
      orderApplication.cancelOrder(orderId);
      return null;
    }
  }
}
