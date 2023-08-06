package run.fleek.application.post.dto;

import lombok.*;
import run.fleek.enums.LoungeTopic;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoungeTopicDto {

  private String name;
  private String code;
  private String description;
  private List<TopicAttributeDto> attributeKeys;

  public static List<LoungeTopicDto> from(List<LoungeTopic> topics) {
    return topics.stream()
      .map(LoungeTopicDto::from)
      .collect(Collectors.toList());
  }

  public static LoungeTopicDto from(LoungeTopic loungeTopic) {
    return LoungeTopicDto.builder()
      .name(loungeTopic.getName())
      .code(loungeTopic.name())
      .description(loungeTopic.getDescription())
      .attributeKeys(loungeTopic.getAttributeKeys())
      .build();
  }
}
