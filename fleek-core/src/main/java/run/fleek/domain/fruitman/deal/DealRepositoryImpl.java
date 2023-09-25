package run.fleek.domain.fruitman.deal;

import com.querydsl.core.QueryResults;
import run.fleek.application.fruitman.deal.dto.DealDto;
import run.fleek.application.fruitman.deal.dto.DealPageDto;
import run.fleek.configuration.database.FleekQueryDslRepositorySupport;
import run.fleek.domain.fruitman.deal.vo.DealVo;
import run.fleek.domain.fruitman.delivery.QDeliveryAreaGroup;

import java.util.List;
import java.util.stream.Collectors;

import static run.fleek.domain.fruitman.deal.vo.DealVo.DEAL_VO_PROJECTION;

public class DealRepositoryImpl extends FleekQueryDslRepositorySupport implements DealRepositoryCustom {

  public DealRepositoryImpl() {
    super(Deal.class);
  }

  QDeal qDeal = QDeal.deal;
  QDealConstraint qDealConstraint = QDealConstraint.dealConstraint;
  QDeliveryAreaGroup qDeliveryAreaGroup = QDeliveryAreaGroup.deliveryAreaGroup;

  @Override
  public List<DealVo> listDealIn(Long effectedAt, Long expiredAt) {
    return from(qDeal)
      .innerJoin(qDeal.deliveryAreaGroup, qDeliveryAreaGroup)
      .innerJoin(qDealConstraint).on(qDeal.dealId.eq(qDealConstraint.deal.dealId))
      .where(qDeal.effectedAt.lt(expiredAt), qDeal.expiredAt.gt(effectedAt))
      .select(DEAL_VO_PROJECTION)
      .orderBy(qDeal.updatedAt.desc())
      .fetch();
  }

  @Override
  public DealPageDto pageDealsBefore(Long effectedAt, int size, int page) {
    QueryResults<DealVo> dealVoQueryResults =  from(qDeal)
      .innerJoin(qDeal.deliveryAreaGroup, qDeliveryAreaGroup)
      .innerJoin(qDealConstraint).on(qDeal.dealId.eq(qDealConstraint.deal.dealId))
      .where(qDeal.expiredAt.lt(effectedAt))
      .orderBy(qDeal.effectedAt.desc())
      .offset((long) page * size)
      .limit(size)
      .select(DEAL_VO_PROJECTION)
      .fetchResults();

    List<DealVo> results = dealVoQueryResults.getResults();

    return DealPageDto.builder()
      .page(page)
      .totalSize(dealVoQueryResults.getTotal())
      .deals(results.stream().map(DealDto::from).collect(Collectors.toList()))
      .build();

  }
}
