package run.fleek.common.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FleekValidationDto {
  private Boolean isValid;
  private String invalidMessage;
}
