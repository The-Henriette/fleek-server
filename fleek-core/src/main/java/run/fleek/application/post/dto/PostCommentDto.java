package run.fleek.application.post.dto;

import lombok.*;
import run.fleek.domain.lounge.vo.PostCommentVo;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCommentDto {

  private Long commentId;
  private String profileName;
  private String content;
  private Long createdAt;
  private Integer likes;
  private Boolean didLike;
  private String profileGender;
  private Integer subComments;
  private Long parentCommentId;

  public static PostCommentDto from(PostCommentVo vo) {
    return PostCommentDto.builder()
      .commentId(vo.getCommentId())
      .profileName(vo.getProfileName())
      .content(vo.getContent())
      .createdAt(vo.getCreatedAt())
      .likes(vo.getLikes())
      .didLike(vo.getCommentLikeId() != null)
      .profileGender(vo.getProfileGender().name())
      .subComments(vo.getSubComments())
      .parentCommentId(vo.getParentCommentId())
      .build();
  }
}
