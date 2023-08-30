package run.fleek.api.controller.fruitman.meta;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import run.fleek.common.client.FleekWebClient;
import run.fleek.configuration.auth.FleekUserContext;
import run.fleek.domain.fruitman.delivery.DeliveryAreaGroup;
import run.fleek.domain.fruitman.delivery.DeliveryAreaGroupService;
import run.fleek.domain.fruitman.meta.PostalCodeMeta;
import run.fleek.domain.fruitman.meta.PostalCodeMetaService;
import run.fleek.domain.fruitman.meta.dto.PostalCodeMetaDto;
import run.fleek.domain.fruitman.user.FruitManUser;
import run.fleek.domain.fruitman.user.FruitManUserService;
import run.fleek.domain.fruitman.user.UserRefundInfoService;
import run.fleek.domain.fruitman.user.dto.UserRefundInfoDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class FruitManMetaController {

  private final PostalCodeMetaService postalCodeMetaService;
  private final FleekWebClient fleekWebClient;
  private final UserRefundInfoService userRefundInfoService;
  private final FleekUserContext fleekUserContext;
  private final FruitManUserService fruitManUserService;
  private final DeliveryAreaGroupService deliveryAreaGroupService;

  @PostMapping("/fruitman/postal-code/meta")
  public void addPostalCodeMetaList(@RequestBody List<PostalCodeMetaDto> postalCodeMetaDtoList) {
    postalCodeMetaService.addPostalCodeMetaList(postalCodeMetaDtoList.stream()
      .map(PostalCodeMetaDto::to)
      .collect(Collectors.toList()));
  }

  @PostMapping("/fruitman/refund-info")
  public void addRefundInfo(@RequestBody UserRefundInfoDto userRefundInfoDto) {
    Long userId = fleekUserContext.getUserId();
    FruitManUser fruitManUser = fruitManUserService.getFruitManUser(userId);

    userRefundInfoService.addRefundInfo(fruitManUser, userRefundInfoDto);
  }

  @GetMapping("/fruitman/refund-info")
  public UserRefundInfoDto getRefundInfo() {
    Long userId = fleekUserContext.getUserId();
    FruitManUser fruitManUser = fruitManUserService.getFruitManUser(userId);

    return userRefundInfoService.getUserRefundInfo(fruitManUser)
      .map(UserRefundInfoDto::from)
      .orElse(UserRefundInfoDto.builder().build());
  }

  @PostMapping("/fruitman/delivery/area/{unit}")
  public void addDeliveryAreaGroup(@PathVariable String unit, @RequestParam List<String> unitKeyList, @RequestParam String groupName) {
    List<PostalCodeMeta> postalCodeMetaList;
    if (unit.equals("SIDO")) {
      postalCodeMetaList = postalCodeMetaService.listPostalCodeMetaBySido(unitKeyList);
    } else if (unit.equals("SIGUNGU")) {
      postalCodeMetaList = postalCodeMetaService.listPostalCodeMetaBySigungu(unitKeyList);
    } else {
      throw new IllegalArgumentException("Invalid unit: " + unit);
    }

    DeliveryAreaGroup deliveryAreaGroup = DeliveryAreaGroup.builder()
      .deliveryAreaGroupName(groupName)
      .build();

    deliveryAreaGroupService.addDeliveryAreaGroup(deliveryAreaGroup, postalCodeMetaList);
  }

//  @GetMapping("/fruitman/postal-code/temp")
//  public int loadPostalCodes() {
//    List<PostalCodeMetaDto> postalCodeMetaDtoList = postalCodeMetaService.listPostalCodeMeta().stream()
//      .map(PostalCodeMetaDto::from)
//      .collect(Collectors.toList());
//
//    List<List<PostalCodeMetaDto>> partitionedList = Lists.partition(postalCodeMetaDtoList, 1000);
//
//    for (List<PostalCodeMetaDto> partition : partitionedList) {
//      fleekWebClient.post("https://api.fleek.run/fruitman/postal-code/meta", partition).bodyToMono(Void.class).block();
//    }
//
//    return postalCodeMetaDtoList.size();
//  }
}
