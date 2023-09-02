package run.fleek.domain.fruitman.deal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartService {
  private final CartRepository cartRepository;

  @Transactional
  public Cart addCart(Cart cart) {
    return cartRepository.save(cart);
  }

  @Transactional(readOnly = true)
  public Cart getCart(Long cartId) {
    return cartRepository.findById(cartId)
      .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 장바구니입니다."));
  }
}
