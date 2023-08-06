package run.fleek.domain.lounge;

import com.querydsl.core.QueryResults;
import run.fleek.application.post.dto.PostCommentDto;
import run.fleek.application.post.dto.PostCommentPageDto;
import run.fleek.configuration.database.FleekQueryDslRepositorySupport;
import run.fleek.domain.lounge.vo.PostCommentVo;
import run.fleek.domain.profile.QProfile;
import run.fleek.domain.users.QFleekUser;
import run.fleek.domain.users.QFleekUserDetail;

import java.util.List;
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

    public PostCommentRepositoryImpl() {
        super(PostComment.class);
    }


    @Override
    public PostCommentPageDto pageByPostId(Long postId, Integer page, Integer size) {
        QueryResults<PostCommentVo> queryResults = from(qPostComment)
            .innerJoin(qPostComment.profile, qProfile)
            .where(qPostComment.loungePost.loungePostId.eq(postId))
            .select(POST_COMMENT_VO_PROJECTION)
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
