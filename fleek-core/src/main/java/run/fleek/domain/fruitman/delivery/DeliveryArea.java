package run.fleek.domain.fruitman.delivery;

import lombok.*;
import run.fleek.common.jpa.CreatedAtListener;
import run.fleek.common.jpa.SystemMetadata;
import run.fleek.common.jpa.UpdatedAtListener;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "delivery_area", schema = "public")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EntityListeners({CreatedAtListener.class, UpdatedAtListener.class})
public class DeliveryArea implements SystemMetadata {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "delivery_area_seq")
  @SequenceGenerator(name = "delivery_area_seq", sequenceName = "delivery_area_seq", allocationSize = 1)
  @Column(name = "delivery_area_id", nullable = false)
  private Long deliveryAreaId;

  @JoinColumn(name = "delivery_area_group_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private DeliveryAreaGroup deliveryAreaGroup;

  @Column(name = "postal_code")
  private String postalCode;

  @Column(name = "created_at", nullable = false)
  private Long createdAt;

  @Column(name = "updated_at", nullable = false)
  private Long updatedAt;


}
