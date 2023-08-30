package run.fleek.domain.fruitman.delivery;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.fleek.domain.fruitman.meta.PostalCodeMeta;

import java.util.List;
import java.util.stream.Collectors;

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

  @Transactional
  public void addDeliveryAreaGroup(DeliveryAreaGroup deliveryAreaGroup, List<PostalCodeMeta> postalCodeMetaList) {
    deliveryAreaGroupRepository.save(deliveryAreaGroup);
    deliveryAreaRepository.saveAll(postalCodeMetaList.stream()
      .map(postalCodeMeta -> DeliveryArea.builder()
        .deliveryAreaGroup(deliveryAreaGroup)
        .postalCode(postalCodeMeta.getPostalCode())
        .build())
      .collect(Collectors.toList()));
  }
}
