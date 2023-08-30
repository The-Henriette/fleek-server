package run.fleek.domain.fruitman.delivery;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeliveryAreaGroupService {
  private final DeliveryAreaGroupRepository deliveryAreaGroupRepository;
  private final DeliveryAreaRepository deliveryAreaRepository;

  @Transactional(readOnly = true)
  public DeliveryAreaGroup getDeliveryAreaGroup(Long deliveryGroupId) {
    return deliveryAreaGroupRepository.findById(deliveryGroupId)
      .orElseThrow(() -> new IllegalArgumentException("deliveryGroupId is invalid"));
  }

  @Transactional(readOnly = true)
  public boolean isAvailable(DeliveryAreaGroup deliveryAreaGroup, String postalCode) {
    return deliveryAreaRepository.existsByDeliveryAreaGroupAndPostalCode(deliveryAreaGroup, postalCode);
  }
}
