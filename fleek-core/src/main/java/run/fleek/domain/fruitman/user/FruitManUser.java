package run.fleek.domain.fruitman.user;

import lombok.*;
import run.fleek.common.jpa.CreatedAtListener;
import run.fleek.common.jpa.SystemMetadata;
import run.fleek.common.jpa.UpdatedAtListener;
import run.fleek.enums.ProviderCode;
import run.fleek.enums.UserStatus;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "fruit_man_user", schema = "public")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EntityListeners({CreatedAtListener.class, UpdatedAtListener.class})
public class FruitManUser implements SystemMetadata {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fruit_man_user_seq")
  @SequenceGenerator(name = "fruit_man_user_seq", sequenceName = "fruit_man_user_seq", allocationSize = 1)
  @Column(name = "fruit_man_user_id", nullable = false)
  private Long fruitManUserId;

  @Column(name = "provider_code", nullable = false) // uk1
  @Enumerated(EnumType.STRING)
  private ProviderCode providerCode;

  @Column(name = "provider_id", nullable = false) // uk1
  private String providerId;

  @Column(name = "nickname")
  private String nickname;

  @Column(name = "email")
  private String email;

  @Column(name = "profile_url")
  private String profileUrl;

  @Column(name = "created_at", nullable = false)
  private Long createdAt;

  @Column(name = "updated_at", nullable = false)
  private Long updatedAt;

}
