package run.fleek.domain.users.term;

import lombok.*;
import run.fleek.common.jpa.CreatedAtListener;
import run.fleek.common.jpa.SystemMetadata;
import run.fleek.common.jpa.UpdatedAtListener;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "term", schema = "public")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EntityListeners({CreatedAtListener.class, UpdatedAtListener.class})
public class Term implements SystemMetadata {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "term_seq")
  @SequenceGenerator(name = "term_seq", sequenceName = "term_seq", allocationSize = 1)
  @Column(name = "term_seq_id", nullable = false)
  private Long termId;

  @Column(name = "mandatory")
  private Boolean mandatory;

  @Column(name = "term_contents_url")
  private String termContentsUrl;

  @Column(name = "term_code")
  private String termCode;

  @Column(name = "created_at", nullable = false)
  private Long createdAt;

  @Column(name = "updated_at", nullable = false)
  private Long updatedAt;

}
