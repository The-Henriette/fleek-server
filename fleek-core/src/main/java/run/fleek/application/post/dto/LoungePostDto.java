package run.fleek.application.post.dto;

import lombok.*;
import run.fleek.domain.lounge.vo.LoungePostVo;
import run.fleek.utils.JsonUtil;

import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoungePostDto {
  private Long postId;
  private String topicCode;
  private Long createdAt;
  private String title;
  private String content;
  private Map<String, String> attributes;
  private String profileName;
  private String profileGender;
  private Integer likes;
  private Integer comments;
  private Integer views;
  private Boolean didLike;
  private String thumbnailImage;

  public static LoungePostDto from(LoungePostVo vo) {
    return LoungePostDto.builder()
      .postId(vo.getPostId())
      .topicCode(vo.getTopicCode().name())
      .createdAt(vo.getCreatedAt())
      .title(vo.getTitle())
      .content(vo.getContent())
      .attributes(JsonUtil.readMap(vo.getAttributes()))
      .profileName(vo.getProfileName())
      .profileGender(vo.getProfileGender().name())
      .likes(vo.getLikes())
      .comments(vo.getComments())
      .views(vo.getViews())
      .didLike(vo.getPostLikeId() != null)
      .thumbnailImage(vo.getThumbnailImage())
      .build();
  }
}
