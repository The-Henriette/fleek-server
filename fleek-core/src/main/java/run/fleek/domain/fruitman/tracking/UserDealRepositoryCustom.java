package run.fleek.domain.fruitman.tracking;

import run.fleek.application.fruitman.order.dto.OrderPageDto;
import run.fleek.domain.fruitman.user.FruitManUser;

public interface UserDealRepositoryCustom {
  OrderPageDto pageOrders(FruitManUser fruitManUser, Integer size, Integer page);
}
