package run.fleek.common.client.nhn.dto;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NhnMessageRequestDto {
  @Builder.Default
  private String senderKey = "7d42c14eac290071945a55f49d9ccbfccce26f49";
  private String templateCode;
  private List<NhnRecipientDto> recipientList;

  @Getter
  @Setter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class NhnRecipientDto {
    private String recipientNo;
    private Map<String, String> templateParameter;
  }
}

