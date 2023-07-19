package run.fleek.domain.users;

import lombok.*;
import run.fleek.common.jpa.CreatedAtListener;
import run.fleek.common.jpa.SystemMetadata;
import run.fleek.common.jpa.UpdatedAtListener;
import run.fleek.enums.Gender;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "fleek_user_detail", schema = "public")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EntityListeners({CreatedAtListener.class, UpdatedAtListener.class})
public class FleekUserDetail implements SystemMetadata {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fleek_user_detail_seq")
  @SequenceGenerator(name = "fleek_user_detail_seq", sequenceName = "fleek_user_detail_seq", allocationSize = 1)
  @Column(name = "fleek_user_detail_id", nullable = false)
  private Long fleekUserDetailId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fleek_user_id")
  private FleekUser fleekUser;

  @Column(name = "name")
  private String name;

  @Column(name = "gender")
  @Enumerated(EnumType.STRING)
  private Gender gender;

  @Column(name = "orientation")
  @Enumerated(EnumType.STRING)
  private Gender orientation;

  @Column(name = "birth_date", nullable = false)
  private Long birthDate;

  @Column(name = "created_at", nullable = false)
  private Long createdAt;

  @Column(name = "updated_at", nullable = false)
  private Long updatedAt;
}
