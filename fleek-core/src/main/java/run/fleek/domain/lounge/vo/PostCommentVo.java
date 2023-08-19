package run.fleek.domain.lounge.vo;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.MappingProjection;
import lombok.*;
import run.fleek.domain.lounge.QLoungePost;
import run.fleek.domain.lounge.QPostComment;
import run.fleek.domain.lounge.QPostLike;
import run.fleek.domain.profile.QProfile;
import run.fleek.domain.users.QFleekUserDetail;
import run.fleek.enums.Gender;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCommentVo {
  private Long commentId;
  private String profileName;
  private String content;
  private Long createdAt;
  private Integer likes;
  private Gender profileGender;
  private Long commentLikeId;
  private Integer subComments;
  private Long parentCommentId;

  public static final PostCommentVoProjection POST_COMMENT_VO_PROJECTION = new PostCommentVoProjection();

  public static class PostCommentVoProjection extends MappingProjection<PostCommentVo> {
    public PostCommentVoProjection() {
      super(PostCommentVo.class,
        QPostComment.postComment.postCommentId,
        QProfile.profile.profileName,
        QPostComment.postComment.content,
        QPostComment.postComment.createdAt,
        QPostComment.postComment.likes
      );
    }

    @Override
    protected PostCommentVo map(Tuple row) {
      return PostCommentVo.builder()
        .commentId(row.get(QPostComment.postComment.postCommentId))
        .createdAt(row.get(QPostComment.postComment.createdAt))
        .content(row.get(QPostComment.postComment.content))
        .profileName(row.get(QProfile.profile.profileName))
        .likes(row.get(QPostComment.postComment.likes))
        .build();
    }
  }

}
