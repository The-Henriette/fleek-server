package run.fleek.api.controller.fruitman.sku;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import run.fleek.domain.fruitman.sku.Sku;
import run.fleek.domain.fruitman.sku.SkuService;
import run.fleek.domain.fruitman.sku.dto.SkuAddDto;

@RestController
@RequiredArgsConstructor
public class FruitManSkuController {

  private final SkuService skuService;

  @PostMapping("/fruitman/sku")
  public void addSku(@RequestBody SkuAddDto skuAddDto) {
    skuService.addSku(Sku.from(skuAddDto));
  }

}
