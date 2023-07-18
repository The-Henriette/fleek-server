package run.fleek.domain.users;

import lombok.*;
import run.fleek.common.jpa.CreatedAtListener;
import run.fleek.common.jpa.SystemMetadata;
import run.fleek.common.jpa.UpdatedAtListener;
import run.fleek.domain.users.dto.SignUpDto;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "trace_user_detail", schema = "public")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EntityListeners({CreatedAtListener.class, UpdatedAtListener.class})
public class FleekUserDetail implements SystemMetadata {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trace_user_detail_seq")
  @SequenceGenerator(name = "trace_user_detail_seq", sequenceName = "trace_user_detail_seq", allocationSize = 1)
  @Column(name = "trace_user_detail_id", nullable = false)
  private Long traceUserDetailId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "trace_user_id")
  private FleekUser fleekUser;

  @Column(name = "phone_number")
  private String phoneNumber;

  @Column(name = "email")
  private String email;

  @Column(name = "created_at", nullable = false)
  private Long createdAt;

  @Column(name = "updated_at", nullable = false)
  private Long updatedAt;

  public static FleekUserDetail create(FleekUser fleekUser, SignUpDto signUpDto) {
    return FleekUserDetail.builder()
      .fleekUser(fleekUser)
      .phoneNumber(signUpDto.getPhoneNumber())
      .email(signUpDto.getEmail())
      .build();
  }
}
