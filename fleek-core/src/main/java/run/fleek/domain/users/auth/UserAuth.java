package run.fleek.domain.users.auth;

import antlr.Token;
import lombok.*;
import run.fleek.common.jpa.CreatedAtListener;
import run.fleek.common.jpa.SystemMetadata;
import run.fleek.common.jpa.UpdatedAtListener;
import run.fleek.configuration.auth.dto.TokenDto;
import run.fleek.domain.users.FleekUser;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "user_auth", schema = "public")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EntityListeners({CreatedAtListener.class, UpdatedAtListener.class})
public class UserAuth implements SystemMetadata {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_auth_seq")
  @SequenceGenerator(name = "user_auth_seq", sequenceName = "user_auth_seq", allocationSize = 1)
  @Column(name = "user_auth_id", nullable = false)
  private Long userAuthId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fleek_user_id")
  private FleekUser fleekUser;

  @Column(name = "access_token")
  private String accessToken;

  @Column(name = "refresh_token")
  private String refreshToken;

  @Column(name = "expired_at")
  private Long expiredAt;

  @Column(name = "created_at", nullable = false)
  private Long createdAt;

  @Column(name = "updated_at", nullable = false)
  private Long updatedAt;

  @Transient
  private Long accessTokenExpiresAt;

  public UserAuth update(TokenDto tokenDto) {
    this.accessToken = tokenDto.getAccessToken();
//    this.refreshToken = tokenDto.getRefreshToken();
//    this.expiredAt = tokenDto.getRefreshTokenExpiresAt();
    return this;
  }

  public static UserAuth from(TokenDto tokenDto, FleekUser fleekUser) {
    return UserAuth.builder()
      .fleekUser(fleekUser)
      .accessToken(tokenDto.getAccessToken())
      .refreshToken(tokenDto.getRefreshToken())
      .expiredAt(tokenDto.getRefreshTokenExpiresAt())
      .build();
  }
}
