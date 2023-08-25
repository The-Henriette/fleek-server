package run.fleek.api.controller.fruitman.meta;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import run.fleek.domain.fruitman.meta.PostalCodeMetaService;
import run.fleek.domain.fruitman.meta.dto.PostalCodeMetaDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PostalCodeMetaController {

  private final PostalCodeMetaService postalCodeMetaService;

  @PostMapping("/fruitman/postal-code/meta")
  public void addPostalCodeMetaList(@RequestBody List<PostalCodeMetaDto> postalCodeMetaDtoList) {
    postalCodeMetaService.addPostalCodeMetaList(postalCodeMetaDtoList.stream()
      .map(PostalCodeMetaDto::to)
      .collect(Collectors.toList()));
  }
}
