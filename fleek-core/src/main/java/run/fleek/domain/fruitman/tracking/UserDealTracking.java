package run.fleek.domain.fruitman.tracking;

import lombok.*;
import run.fleek.common.jpa.CreatedAtListener;
import run.fleek.common.jpa.SystemMetadata;
import run.fleek.common.jpa.UpdatedAtListener;
import run.fleek.enums.DealTrackingStatus;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "user_deal_tracking", schema = "public")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EntityListeners({CreatedAtListener.class, UpdatedAtListener.class})
public class UserDealTracking implements SystemMetadata {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_deal_tracking_seq")
  @SequenceGenerator(name = "user_deal_tracking_seq", sequenceName = "user_deal_tracking_seq", allocationSize = 1)
  @Column(name = "user_deal_tracking_id", nullable = false)
  private Long userDealTrackingId;

  @JoinColumn(name = "user_deal_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private UserDeal userDeal;

  @Column(name = "tracking_status")
  private DealTrackingStatus trackingStatus;

  @Column(name = "tracking_at")
  private Long trackingAt;

  @Column(name = "shipping_label_number")
  private String shippingLabelNumber;

  @Column(name = "created_at", nullable = false)
  private Long createdAt;

  @Column(name = "updated_at", nullable = false)
  private Long updatedAt;


}
