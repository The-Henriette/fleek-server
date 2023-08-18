package run.fleek.application.exchange.dto;

import com.google.api.client.util.Lists;
import lombok.*;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendbirdExchangeDataDto {
  private String state;
  private String exchangeId;
  private List<String> readProfileNames;

  public List<String> getReadProfileNames() {
    if (CollectionUtils.isEmpty(readProfileNames)) {
      return Lists.newArrayList();
    }

    return readProfileNames;
  }
}
