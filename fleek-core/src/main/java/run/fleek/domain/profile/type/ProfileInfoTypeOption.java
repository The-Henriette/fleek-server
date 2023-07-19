package run.fleek.domain.profile.type;

import lombok.*;
import run.fleek.common.jpa.CreatedAtListener;
import run.fleek.common.jpa.SystemMetadata;
import run.fleek.common.jpa.UpdatedAtListener;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "profile_info_type_option", schema = "public")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EntityListeners({CreatedAtListener.class, UpdatedAtListener.class})
public class ProfileInfoTypeOption implements SystemMetadata {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_info_type_option_seq")
  @SequenceGenerator(name = "profile_info_type_option_seq", sequenceName = "profile_info_type_option_seq", allocationSize = 1)
  @Column(name = "profile_info_type_option_id", nullable = false)
  private Long profileInfoTypeOptionId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "profile_info_type_id")
  private ProfileInfoType profileInfoType;

  @Column(name = "profile_info_type_code") // uk2
  private String profileInfoTypeCode;

  @Column(name = "option_code")
  private String optionCode; // uk1

  @Column(name = "option_name")
  private String optionName;

  @Column(name = "option_description")
  private String optionDescription;

  @Column(name = "order_number")
  private String orderNumber; // uk2

  @Column(name = "created_at", nullable = false)
  private Long createdAt;

  @Column(name = "updated_at", nullable = false)
  private Long updatedAt;

}
