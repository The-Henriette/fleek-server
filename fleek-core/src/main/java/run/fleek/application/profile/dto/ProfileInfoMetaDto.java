package run.fleek.application.profile.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileInfoMetaDto {
    private String typeCode;
    private String typeName;

    private List<ProfileOptionMetaDto> options;
}
