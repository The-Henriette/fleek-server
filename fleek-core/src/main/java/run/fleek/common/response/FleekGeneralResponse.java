package run.fleek.common.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FleekGeneralResponse {
  private Boolean success;
  private String errorMessage;
}
