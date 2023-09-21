package run.fleek.domain.fruitman.tracking;

import run.fleek.application.fruitman.order.dto.OrderPageDto;
import run.fleek.application.fruitman.order.vo.OrderVo;
import run.fleek.domain.fruitman.deal.Deal;
import run.fleek.domain.fruitman.user.FruitManUser;

import java.util.List;

public interface UserDealRepositoryCustom {
  OrderPageDto pageOrders(FruitManUser fruitManUser, Integer size, Integer page);
}
