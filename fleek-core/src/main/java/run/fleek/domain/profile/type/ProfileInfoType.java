package run.fleek.domain.profile.type;

import lombok.*;
import run.fleek.common.jpa.CreatedAtListener;
import run.fleek.common.jpa.SystemMetadata;
import run.fleek.common.jpa.UpdatedAtListener;
import run.fleek.enums.ProfileInfoCategory;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "profile_info_type", schema = "public")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EntityListeners({CreatedAtListener.class, UpdatedAtListener.class})
public class ProfileInfoType implements SystemMetadata {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_info_type_seq")
  @SequenceGenerator(name = "profile_info_type_seq", sequenceName = "profile_info_type_seq", allocationSize = 1)
  @Column(name = "profile_info_type_id", nullable = false)
  private Long profileInfoTypeId;

  @Column(name = "profile_info_type_code") // uk1
  private String profileInfoTypeCode;

  @Column(name = "profile_info_type_name")
  private String profileInfoTypeName;

  @Column(name = "description")
  private String description;

//  @Column(name = "inputType")

  @Column(name = "profile_info_category")
  @Enumerated(EnumType.STRING)
  private ProfileInfoCategory profileInfoCategory; // uk2

  @Column(name = "order_number")
  private Integer orderNumber; // uk2

  @Column(name = "created_at", nullable = false)
  private Long createdAt;

  @Column(name = "updated_at", nullable = false)
  private Long updatedAt;

}
