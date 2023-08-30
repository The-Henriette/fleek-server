package run.fleek.domain.fruitman.delivery;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryAreaService {

  private final DeliveryAreaRepository deliveryAreaRepository;
}
