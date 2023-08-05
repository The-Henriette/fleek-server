package run.fleek.application.exchange.dto;

import lombok.*;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

import static run.fleek.common.constants.Constants.CDN_PREFIX;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeWatchDto {
  private Boolean success;
  private List<String> faceUrls;
  private String failureReason;

  public List<String> getFaceUrls() {
    if (CollectionUtils.isEmpty(faceUrls)) {
      return null;
    }

    return faceUrls.stream()
      .map(url -> CDN_PREFIX + url)
      .collect(Collectors.toList());
  }
}
