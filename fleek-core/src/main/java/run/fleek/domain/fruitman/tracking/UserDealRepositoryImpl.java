package run.fleek.domain.fruitman.tracking;

import com.querydsl.core.QueryResults;
import run.fleek.application.fruitman.order.dto.OrderDto;
import run.fleek.application.fruitman.order.dto.OrderPageDto;
import run.fleek.application.fruitman.order.vo.OrderVo;
import run.fleek.configuration.database.FleekQueryDslRepositorySupport;
import run.fleek.domain.fruitman.deal.Deal;
import run.fleek.domain.fruitman.deal.QDeal;
import run.fleek.domain.fruitman.user.FruitManUser;

import java.util.List;
import java.util.stream.Collectors;

import static run.fleek.enums.DealTrackingStatus.VISIBLE_STATES;

public class UserDealRepositoryImpl extends FleekQueryDslRepositorySupport implements UserDealRepositoryCustom {

  private final QUserDeal qUserDeal = QUserDeal.userDeal;
  private final QUserPayment qUserPayment = QUserPayment.userPayment;
  private final QDeal qDeal = QDeal.deal;

  public UserDealRepositoryImpl() {
    super(UserDeal.class);
  }

  @Override
  public OrderPageDto pageOrders(FruitManUser fruitManUser, Integer size, Integer page) {
    QueryResults<OrderVo> vos = from(qUserDeal)
      .innerJoin(qUserDeal.deal, qDeal)
      .innerJoin(qUserPayment).on(qUserDeal.userDealId.eq(qUserPayment.userDeal.userDealId))
      .where(qUserDeal.fruitManUser.eq(fruitManUser).and(qUserDeal.trackingStatus.in(VISIBLE_STATES)))
      .orderBy(qUserDeal.orderedAt.desc())
      .offset((long) page * size)
      .limit(size)
      .select(OrderVo.ORDER_VO_PROJECTION)
      .fetchResults();

    List<OrderVo> results = vos.getResults();

    return OrderPageDto.builder()
      .page(page)
      .totalSize(vos.getTotal())
      .orders(results.stream().map(OrderDto::from).collect(Collectors.toList()))
      .build();
  }
}
