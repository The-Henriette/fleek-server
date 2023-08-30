package run.fleek.domain.fruitman.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.fleek.domain.fruitman.user.dto.UserRefundInfoDto;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRefundInfoService {

  private final UserRefundInfoRepository userRefundInfoRepository;

  @Transactional(readOnly = true)
  public Optional<UserRefundInfo> getUserRefundInfo(FruitManUser fruitManUser) {
    return userRefundInfoRepository.findByFruitManUser(fruitManUser);
  }

  @Transactional
  public void addRefundInfo(FruitManUser fruitManUser, UserRefundInfoDto userRefundInfoDto) {
    userRefundInfoRepository.save(UserRefundInfo.builder()
        .fruitManUser(fruitManUser)
        .refundBankName(userRefundInfoDto.getRefundBankName())
        .refundAccountName(userRefundInfoDto.getRefundAccountName())
        .refundAccountNumber(userRefundInfoDto.getRefundAccountNumber())
      .build());
  }
}
