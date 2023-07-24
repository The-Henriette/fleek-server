package run.fleek.domain.profile.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileInfoOptionDto {
  private String optionCode;
  private String optionName;
  private String optionEmoji;
}
