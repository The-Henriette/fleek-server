package run.fleek.domain.users.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDto {
    private String userName;
    private String password;
    private String email;
    private String phoneNumber;
}
