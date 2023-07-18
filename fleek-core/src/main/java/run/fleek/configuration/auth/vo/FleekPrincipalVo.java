package run.fleek.configuration.auth.vo;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FleekPrincipalVo {
    private Long fleekUserId;
    private String userName;
}
