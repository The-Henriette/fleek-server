package run.fleek.domain.fruitman.deal;

import run.fleek.domain.fruitman.deal.vo.DealVo;

import java.util.List;

public interface DealRepositoryCustom {
  List<DealVo> listDealIn(Long effectedAt, Long expiredAt);
}
