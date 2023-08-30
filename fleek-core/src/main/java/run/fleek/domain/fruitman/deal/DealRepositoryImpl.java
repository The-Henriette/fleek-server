package run.fleek.domain.fruitman.deal;

import run.fleek.configuration.database.FleekQueryDslRepositorySupport;
import run.fleek.domain.fruitman.deal.vo.DealVo;
import run.fleek.domain.fruitman.delivery.QDeliveryAreaGroup;

import java.util.List;

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
      .where(qDeal.effectedAt.lt(expiredAt), qDeal.expiredAt.goe(effectedAt))
      .select(DEAL_VO_PROJECTION)
      .orderBy(qDeal.updatedAt.desc())
      .fetch();
  }
}
