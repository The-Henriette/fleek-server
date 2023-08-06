package run.fleek.application.post.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopicAttributeDto {
  private String code;
  private String name;
}
