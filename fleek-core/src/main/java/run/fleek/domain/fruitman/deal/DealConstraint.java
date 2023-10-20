package run.fleek.domain.fruitman.deal;

import lombok.*;
import run.fleek.application.fruitman.deal.dto.DealAddDto;
import run.fleek.common.jpa.CreatedAtListener;
import run.fleek.common.jpa.SystemMetadata;
import run.fleek.common.jpa.UpdatedAtListener;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "deal_constraint", schema = "public")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EntityListeners({CreatedAtListener.class, UpdatedAtListener.class})
public class DealConstraint implements SystemMetadata {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "deal_constraint_seq")
  @SequenceGenerator(name = "deal_constraint_seq", sequenceName = "deal_constraint_seq", allocationSize = 1)
  @Column(name = "deal_constraint_id", nullable = false)
  private Long dealConstraintId;

  @JoinColumn(name = "deal_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private Deal deal;

  @Column(name = "required_quantity")
  private Integer requiredQuantity;

  @Column(name = "current_quantity")
  private Integer currentQuantity;

//  User당 해당 Deal을 살 수 있는 최대 수량을 나타냄
//  개인 구매의 경우 카운팅되지 않음.
  @Column(name = "quantity_per_user")
  private Integer quantityPerUser;

  @Column(name = "created_at", nullable = false)
  private Long createdAt;

  @Column(name = "updated_at", nullable = false)
  private Long updatedAt;

  public static DealConstraint from(DealAddDto dealAddDto, Deal deal) {
    return DealConstraint.builder()
      .deal(deal)
      .requiredQuantity(dealAddDto.getRequiredQuantity())
      .currentQuantity(0)
      .quantityPerUser(dealAddDto.getQuantityPerUser())
      .build();
  }

}
