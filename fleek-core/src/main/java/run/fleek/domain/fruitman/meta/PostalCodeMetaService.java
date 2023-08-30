package run.fleek.domain.fruitman.meta;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import run.fleek.domain.fruitman.user.dto.UserRefundInfoDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostalCodeMetaService {

  private final PostalCodeMetaRepository postalCodeMetaRepository;

  public void addPostalCodeMetaList(List<PostalCodeMeta> postalCodeMetas) {
    postalCodeMetaRepository.saveAll(postalCodeMetas);
  }

  public List<PostalCodeMeta> listPostalCodeMeta() {
    return postalCodeMetaRepository.findAll();
  }

  public List<PostalCodeMeta> listPostalCodeMetaBySido(List<String> sido) {
    return postalCodeMetaRepository.findAllBySidoIn(sido);
  }

  public List<PostalCodeMeta> listPostalCodeMetaBySigungu(List<String> sigungu) {
    return postalCodeMetaRepository.findAllBySigunguIn(sigungu);
  }
}
