package run.fleek.domain.lounge.vo;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.MappingProjection;
import lombok.*;
import run.fleek.domain.lounge.QLoungePost;
import run.fleek.domain.lounge.QPostLike;
import run.fleek.domain.profile.QProfile;
import run.fleek.domain.users.QFleekUser;
import run.fleek.domain.users.QFleekUserDetail;
import run.fleek.domain.users.vo.FleekUserVo;
import run.fleek.enums.Gender;
import run.fleek.enums.LoungeTopic;

import java.util.Map;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoungePostVo {

  private Long postId;
  private LoungeTopic topicCode;
  private Long createdAt;
  private String title;
  private String content;
  private String attributes;
  private String profileName;
  private Gender profileGender;
  private Integer likes;
  private Integer comments;
  private Integer views;
  private Long postLikeId;
  private String thumbnailImage;

  public static final LoungePostVoProjection LOUNGE_POST_VO_PROJECTION = new LoungePostVoProjection();

  public static class LoungePostVoProjection extends MappingProjection<LoungePostVo> {
    public LoungePostVoProjection() {
      super(LoungePostVo.class,
        QLoungePost.loungePost.loungePostId,
        QLoungePost.loungePost.topic,
        QLoungePost.loungePost.createdAt,
        QLoungePost.loungePost.title,
        QLoungePost.loungePost.content,
        QLoungePost.loungePost.topicAttributes,
        QProfile.profile.profileName,
        QFleekUserDetail.fleekUserDetail.gender,
        QLoungePost.loungePost.likes,
        QLoungePost.loungePost.comments,
        QLoungePost.loungePost.views,
        QPostLike.postLike.postLikeId,
        QLoungePost.loungePost.thumbnail
        );
    }

    @Override
    protected LoungePostVo map(Tuple row) {
      return LoungePostVo.builder()
        .postId(row.get(QLoungePost.loungePost.loungePostId))
        .topicCode(row.get(QLoungePost.loungePost.topic))
        .createdAt(row.get(QLoungePost.loungePost.createdAt))
        .title(row.get(QLoungePost.loungePost.title))
        .content(row.get(QLoungePost.loungePost.content))
        .attributes(row.get(QLoungePost.loungePost.topicAttributes))
        .profileName(row.get(QProfile.profile.profileName))
        .profileGender(row.get(QFleekUserDetail.fleekUserDetail.gender))
        .likes(row.get(QLoungePost.loungePost.likes))
        .comments(row.get(QLoungePost.loungePost.comments))
        .views(row.get(QLoungePost.loungePost.views))
        .postLikeId(row.get(QPostLike.postLike.postLikeId))
        .thumbnailImage(row.get(QLoungePost.loungePost.thumbnail))
        .build();
    }
  }

}
