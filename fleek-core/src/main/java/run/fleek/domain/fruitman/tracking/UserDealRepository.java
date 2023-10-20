package run.fleek.domain.fruitman.tracking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import run.fleek.domain.fruitman.deal.Cart;
import run.fleek.domain.fruitman.deal.Deal;
import run.fleek.domain.fruitman.user.FruitManUser;
import run.fleek.enums.DealTrackingStatus;
import run.fleek.enums.PurchaseOption;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserDealRepository extends JpaRepository<UserDeal, Long>, UserDealRepositoryCustom {
  Optional<UserDeal> findFirstByDealAndTrackingStatusInAndPurchaseOption(Deal deal, List<DealTrackingStatus> statusList, PurchaseOption purchaseOption);

  List<UserDeal> findAllByFruitManUserAndDealAndTrackingStatusInAndPurchaseOption(FruitManUser fruitmanUser, Deal deal, ArrayList<DealTrackingStatus> dealTrackingStatuses, PurchaseOption purchaseOption);

  List<UserDeal> findAllByDeal(Deal deal);

  Optional<UserDeal> findByOrderId(String orderId);

  List<UserDeal> findAllByCart(Cart cart);

}
