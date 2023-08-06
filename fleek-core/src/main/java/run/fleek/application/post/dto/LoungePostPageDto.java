package run.fleek.application.post.dto;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoungePostPageDto {
  private Integer page;
  private Long totalSize;
  private List<LoungePostDto> posts;
}
