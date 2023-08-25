package run.fleek.domain.fruitman.sku;

import lombok.*;
import run.fleek.common.jpa.CreatedAtListener;
import run.fleek.common.jpa.SystemMetadata;
import run.fleek.common.jpa.UpdatedAtListener;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "sku", schema = "public")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EntityListeners({CreatedAtListener.class, UpdatedAtListener.class})
public class Sku implements SystemMetadata {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sku_seq")
  @SequenceGenerator(name = "sku_seq", sequenceName = "sku_seq", allocationSize = 1)
  @Column(name = "sku_id", nullable = false)
  private Long skuId;

  @Column(name = "sku_name", nullable = false)
  private String skuName;

  @Column(name = "sku_main_image")
  private String skuMainImage;

  @Column(name = "created_at", nullable = false)
  private Long createdAt;

  @Column(name = "updated_at", nullable = false)
  private Long updatedAt;

}
