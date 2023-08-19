package run.fleek.domain.lounge;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import org.springframework.util.StringUtils;
import run.fleek.application.post.dto.PostCommentDto;
import run.fleek.application.post.dto.PostCommentPageDto;
import run.fleek.configuration.database.FleekQueryDslRepositorySupport;
import run.fleek.domain.lounge.vo.PostCommentVo;
import run.fleek.domain.profile.QProfile;
import run.fleek.domain.users.QFleekUser;
import run.fleek.domain.users.QFleekUserDetail;
import run.fleek.enums.LoungeTopic;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static run.fleek.domain.lounge.vo.PostCommentVo.POST_COMMENT_VO_PROJECTION;

public class PostCommentRepositoryImpl extends FleekQueryDslRepositorySupport implements PostCommentRepositoryCustom {

    QLoungePost qLoungePost = QLoungePost.loungePost;
    QPostImage qLoungeImage = QPostImage.postImage;
    QPostLike qPostLike = QPostLike.postLike;
    QProfile qProfile = QProfile.profile;
    QFleekUser qFleekUser = QFleekUser.fleekUser;
    QFleekUserDetail qFleekUserDetail = QFleekUserDetail.fleekUserDetail;
    QPostComment qPostComment = QPostComment.postComment;
    QCommentLike qCommentLike = QCommentLike.commentLike;

    QProfile qWriterProfile = new QProfile("writerProfile");
    QProfile qReaderProfile = new QProfile("readerProfile");


    public PostCommentRepositoryImpl() {
        super(PostComment.class);
    }


    @Override
    public PostCommentPageDto pageByPostId(Long postId, Integer page, Integer size, Long readerProfileId,
                                           Long parentCommentId) {
        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and(qPostComment.loungePost.loungePostId.eq(postId));
        if (Objects.nonNull(parentCommentId)) {
            predicate.and(qPostComment.parentCommentId.eq(parentCommentId));
        } else {
            predicate.and(qPostComment.parentCommentId.isNull());
        }

        QueryResults<PostCommentVo> queryResults = from(qPostComment)
          .innerJoin(qPostComment.profile, qWriterProfile)
          .innerJoin(qWriterProfile.fleekUser, qFleekUser)
          .innerJoin(qFleekUserDetail).on(qFleekUser.fleekUserId.eq(qFleekUserDetail.fleekUser.fleekUserId))
          .leftJoin(qCommentLike).on(qCommentLike.comment.postCommentId.eq(qPostComment.postCommentId)
            .and(qCommentLike.profile.profileId.eq(Objects.isNull(readerProfileId) ? -1L : readerProfileId)))
          .where(predicate)
            .select(Projections.constructor(PostCommentVo.class,
              qPostComment.postCommentId,
              qWriterProfile.profileName,
              qPostComment.content,
              qPostComment.createdAt,
              qPostComment.likes,
              qFleekUserDetail.gender,
              qCommentLike.commentLikeId,
              qPostComment.subComments,
              qPostComment.parentCommentId
              ))
            .orderBy(qPostComment.createdAt.desc())
            .offset((long) size * page)
            .limit(size)
            .fetchResults();

        List<PostCommentVo> results = queryResults.getResults();

        return PostCommentPageDto.builder()
            .page(page)
            .totalSize(queryResults.getTotal())
            .comments(results.stream()
                .map(PostCommentDto::from)
                .collect(Collectors.toList()))
            .build();
    }
}
