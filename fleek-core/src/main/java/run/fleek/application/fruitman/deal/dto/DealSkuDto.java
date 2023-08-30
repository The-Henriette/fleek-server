package run.fleek.application.fruitman.deal.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DealSkuDto {
  private Long skuId;
  private String producedLocation;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
  private Date producedAt;
  private String producedBy;
}
