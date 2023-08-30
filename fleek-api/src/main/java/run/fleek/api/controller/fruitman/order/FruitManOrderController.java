package run.fleek.api.controller.fruitman.order;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import run.fleek.application.fruitman.order.OrderApplication;
import run.fleek.application.fruitman.order.dto.OrderAddDto;
import run.fleek.application.fruitman.order.dto.OrderDetailDto;
import run.fleek.application.fruitman.order.dto.OrderDto;
import run.fleek.application.fruitman.order.dto.OrderPageDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FruitManOrderController {

  private final OrderApplication orderApplication;

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
}
