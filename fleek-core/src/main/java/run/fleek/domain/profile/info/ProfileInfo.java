package run.fleek.domain.profile.info;

import lombok.*;
import run.fleek.common.jpa.CreatedAtListener;
import run.fleek.common.jpa.SystemMetadata;
import run.fleek.common.jpa.UpdatedAtListener;
import run.fleek.domain.profile.Profile;
import run.fleek.enums.ProfileInfoCategory;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "profile_info", schema = "public")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EntityListeners({CreatedAtListener.class, UpdatedAtListener.class})
public class ProfileInfo implements SystemMetadata {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_info_seq")
  @SequenceGenerator(name = "profile_info_seq", sequenceName = "profile_info_seq", allocationSize = 1)
  @Column(name = "profile_info_id", nullable = false)
  private Long profileInfoId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "profile_id")
  private Profile profile;

  @Column(name = "profile_info_category")
  @Enumerated(EnumType.STRING)
  private ProfileInfoCategory profileInfoCategory;

  @Column(name = "type_name")
  private String typeName;

  @Column(name = "type_option")
  private String typeOption;

  @Column(name = "type_value")
  private String typeValue;

  @Column(name = "created_at", nullable = false)
  private Long createdAt;

  @Column(name = "updated_at", nullable = false)
  private Long updatedAt;
}
