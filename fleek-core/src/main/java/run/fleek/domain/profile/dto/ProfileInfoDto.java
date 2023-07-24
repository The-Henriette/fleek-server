package run.fleek.domain.profile.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileInfoDto {
  private String typeCode;
  private String typeName;
  private List<ProfileInfoOptionDto> typeOptions;
  private String typeValue;
  private String inputType;
  private String emoji;
}
