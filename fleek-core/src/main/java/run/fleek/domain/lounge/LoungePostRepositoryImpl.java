package run.fleek.domain.lounge;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import org.springframework.util.StringUtils;
import run.fleek.application.post.dto.LoungePostDto;
import run.fleek.application.post.dto.LoungePostPageDto;
import run.fleek.configuration.database.FleekQueryDslRepositorySupport;
import run.fleek.domain.lounge.vo.LoungePostVo;
import run.fleek.domain.profile.QProfile;
import run.fleek.domain.users.QFleekUser;
import run.fleek.domain.users.QFleekUserDetail;
import run.fleek.enums.LoungeTopic;

import java.util.List;
import java.util.stream.Collectors;

import static run.fleek.domain.lounge.vo.LoungePostVo.LOUNGE_POST_VO_PROJECTION;

public class LoungePostRepositoryImpl extends FleekQueryDslRepositorySupport implements LoungePostRepositoryCustom {

    QLoungePost qLoungePost = QLoungePost.loungePost;
    QPostImage qLoungeImage = QPostImage.postImage;
    QPostLike qPostLike = QPostLike.postLike;
    QProfile qProfile = QProfile.profile;
    QFleekUser qFleekUser = QFleekUser.fleekUser;
    QFleekUserDetail qFleekUserDetail = QFleekUserDetail.fleekUserDetail;


    public LoungePostRepositoryImpl() {
        super(LoungePost.class);
    }


    @Override
    public LoungePostPageDto pageLoungePosts(Integer size, Integer page, String profileName, String topicCode) {
        BooleanBuilder predicate = new BooleanBuilder();
        if (StringUtils.hasLength(topicCode)) {
            predicate.and(qLoungePost.topic.eq(LoungeTopic.valueOf(topicCode)));
        }

        if (StringUtils.hasLength(profileName)) {
            predicate.and(qProfile.profileName.eq(profileName));
        }

        QueryResults<LoungePostVo> queryResults =
          from(qLoungePost)
            .innerJoin(qLoungePost.profile, qProfile)
            .innerJoin(qProfile.fleekUser, qFleekUser)
            .innerJoin(qFleekUserDetail).on(qFleekUser.fleekUserId.eq(qFleekUserDetail.fleekUser.fleekUserId))
            .leftJoin(qPostLike).on(qPostLike.loungePost.loungePostId.eq(qLoungePost.loungePostId)
                  .and(qPostLike.profile.profileId.eq(qProfile.profileId)))
            .where(predicate)
            .select(LOUNGE_POST_VO_PROJECTION)
            .orderBy(qLoungePost.createdAt.desc())
            .offset((long) size * page)
            .limit(size)
            .fetchResults();

        List<LoungePostVo> results = queryResults.getResults();

        return LoungePostPageDto.builder()
          .page(page)
          .totalSize(queryResults.getTotal())
          .posts(results.stream().map(LoungePostDto::from).collect(Collectors.toList()))
          .build();
    }

    @Override
    public LoungePostVo getLoungePost(Long postId, String profileName) {
        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and(qLoungePost.loungePostId.eq(postId));
        if (StringUtils.hasLength(profileName)) {
            predicate.and(qProfile.profileName.eq(profileName));
        }

        return from(qLoungePost)
          .innerJoin(qLoungePost.profile, qProfile)
          .innerJoin(qProfile.fleekUser, qFleekUser)
          .innerJoin(qFleekUserDetail).on(qFleekUser.fleekUserId.eq(qFleekUserDetail.fleekUser.fleekUserId))
           .leftJoin(qPostLike).on(qPostLike.loungePost.loungePostId.eq(qLoungePost.loungePostId)
                .and(qPostLike.profile.profileId.eq(qProfile.profileId)))
          .where(predicate)
          .select(LOUNGE_POST_VO_PROJECTION)
          .fetchOne();
    }
}
