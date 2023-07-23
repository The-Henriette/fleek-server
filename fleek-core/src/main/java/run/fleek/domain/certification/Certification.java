package run.fleek.domain.certification;

import lombok.*;
import run.fleek.common.jpa.CreatedAtListener;
import run.fleek.common.jpa.SystemMetadata;
import run.fleek.common.jpa.UpdatedAtListener;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "certification", schema = "public")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EntityListeners({CreatedAtListener.class, UpdatedAtListener.class})
public class Certification implements SystemMetadata {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "certification_seq")
  @SequenceGenerator(name = "certification_seq", sequenceName = "certification_seq", allocationSize = 1)
  @Column(name = "certification_id", nullable = false)
  private Long profileId;

  @Column(name = "certification_code")
  private String certificationCode;

  @Column(name = "certification_description")
  private String certificationDescription;

  @Column(name = "created_at", nullable = false)
  private Long createdAt;

  @Column(name = "updated_at", nullable = false)
  private Long updatedAt;
}
