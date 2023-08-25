package run.fleek.api.controller.fruitman;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FruitManHelloController {

  @GetMapping("/fruitman/hello")
  public String hello() {
    return "Fuck yeah, FruitMan!";
  }
}
