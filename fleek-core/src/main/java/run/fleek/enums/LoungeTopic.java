package run.fleek.enums;

import com.google.common.collect.Lists;
import lombok.Getter;
import run.fleek.application.post.dto.TopicAttributeDto;

import java.util.List;

@Getter
public enum LoungeTopic implements FleekEnum {

  DATING("소개팅", "", Lists.newArrayList()),
  TRAVEL("여행/동행", "", Lists.newArrayList(TopicAttributeDto.builder()
      .name("약속 희망 장소")
      .code("location")
    .build())),
  GROUP("미팅", "", Lists.newArrayList(TopicAttributeDto.builder()
    .name("약속 희망 장소")
    .code("location")
    .build())),
  NSFW("19+", "", Lists.newArrayList()),
  FREE("자유", "", Lists.newArrayList());

  LoungeTopic(String name, String description, List<TopicAttributeDto> attributeKeys) {
    this.name = name;
    this.description = description;
    this.attributeKeys = attributeKeys;
  }

  private final String description;
  private final String name;
  private final List<TopicAttributeDto> attributeKeys;
}
