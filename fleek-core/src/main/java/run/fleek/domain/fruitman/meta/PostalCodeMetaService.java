package run.fleek.domain.fruitman.meta;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostalCodeMetaService {

  private final PostalCodeMetaRepository postalCodeMetaRepository;

  public void addPostalCodeMetaList(List<PostalCodeMeta> postalCodeMetas) {
    postalCodeMetaRepository.saveAll(postalCodeMetas);
  }
}
