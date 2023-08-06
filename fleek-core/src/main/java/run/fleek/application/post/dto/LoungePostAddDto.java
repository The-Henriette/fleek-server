package run.fleek.application.post.dto;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoungePostAddDto {
  private String profileName;
  private String topicCode;
  private String title;
  private String content;
  private List<String> photos;
  private Map<String, String> attributes;
}
