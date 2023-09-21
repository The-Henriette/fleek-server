package run.fleek.api.controller.fruitman.deal;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import run.fleek.application.fruitman.deal.DealApplication;
import run.fleek.application.fruitman.deal.dto.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FruitManDealController {

  private final DealApplication dealApplication;

  @PostMapping("/fruitman/deal")
  public void addDeal(@RequestBody DealAddDto dealAddDto) {
    dealApplication.addDeal(dealAddDto);
  }

  @GetMapping("/fruitman/deal/today")
  public List<DealDto> listTodayDeal() {
    return dealApplication.listTodayDeal();
  }

  @GetMapping("/fruitman/deal/previous")
  public DealPageDto listPreviousDeals(@RequestParam(defaultValue = "10") int size,
                                       @RequestParam(defaultValue = "0") int page) {
    return dealApplication.listPreviousDeals(size, page);
  }

  @GetMapping("/fruitman/deal/{dealId}")
  public DealDetailDto getDealDetail(@PathVariable Long dealId) {
    return dealApplication.getDealDetail(dealId);
  }

  @GetMapping("/fruitman/deal/{dealId}/participants")
  public DealParticipantsDto getDealParticipants(@PathVariable Long dealId) {
    return dealApplication.getDealParticipants(dealId);
  }

  @GetMapping("/fruitman/deal/{dealId}/postal-code/{postalCode}/available")
  public DealAvailabilityDto getDealAvailability(@PathVariable Long dealId, @PathVariable String postalCode) {
    return dealApplication.getDealAvailability(dealId, postalCode);
  }
}
