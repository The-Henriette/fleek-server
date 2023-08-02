package run.fleek.application.auth.dto;

import lombok.*;
import run.fleek.domain.certification.dto.CertificationRegisterDto;
import run.fleek.domain.certification.dto.CertificationResourceDto;
import run.fleek.domain.users.term.dto.UserTermDto;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDto {
  private String verificationCode;
  private String name;
  private String dateOfBirth;
  private String gender;
  private String phoneNumber;
  private String profileName;
  private String profileChatCode;
  private List<String> profileImageUrls;
  private List<UserTermDto> userTerms;
  private CertificationRegisterDto certification;

}
