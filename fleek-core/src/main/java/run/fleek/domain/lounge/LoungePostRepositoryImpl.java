package run.fleek.domain.lounge;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import org.springframework.util.StringUtils;
import run.fleek.application.post.dto.LoungePostDto;
import run.fleek.application.post.dto.LoungePostPageDto;
import run.fleek.configuration.database.FleekQueryDslRepositorySupport;
import run.fleek.domain.chat.vo.ProfileChatVo;
import run.fleek.domain.lounge.vo.LoungePostVo;
import run.fleek.domain.profile.QProfile;
import run.fleek.domain.users.QFleekUser;
import run.fleek.domain.users.QFleekUserDetail;
import run.fleek.enums.LoungeTopic;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static run.fleek.domain.lounge.vo.LoungePostVo.LOUNGE_POST_VO_PROJECTION;

public class LoungePostRepositoryImpl extends FleekQueryDslRepositorySupport implements LoungePostRepositoryCustom {

    QLoungePost qLoungePost = QLoungePost.loungePost;
    QPostImage qLoungeImage = QPostImage.postImage;
    QPostLike qPostLike = QPostLike.postLike;
    QProfile qProfile = QProfile.profile;

    QProfile qWriterProfile = new QProfile("writerProfile");
    QProfile qReaderProfile = new QProfile("readerProfile");
    QFleekUser qFleekUser = QFleekUser.fleekUser;
    QFleekUserDetail qFleekUserDetail = QFleekUserDetail.fleekUserDetail;


    public LoungePostRepositoryImpl() {
        super(LoungePost.class);
    }


    @Override
    public LoungePostPageDto pageLoungePosts(Integer size, Integer page, Long readerProfileId, String topicCode,
                                             String writerName, String keyword) {
        BooleanBuilder predicate = new BooleanBuilder();
        if (StringUtils.hasLength(topicCode)) {
            predicate.and(qLoungePost.topic.eq(LoungeTopic.valueOf(topicCode)));
        }

        if (StringUtils.hasLength(writerName)) {
            predicate.and(qWriterProfile.profileName.eq(writerName));
        }

        if (StringUtils.hasLength(keyword)) {
            predicate.and(qLoungePost.title.containsIgnoreCase(keyword)
              .or(qLoungePost.content.containsIgnoreCase(keyword)));
        }

        QueryResults<LoungePostVo> queryResults =
          from(qLoungePost)
            .innerJoin(qLoungePost.profile, qWriterProfile)
            .innerJoin(qWriterProfile.fleekUser, qFleekUser)
            .innerJoin(qFleekUserDetail).on(qFleekUser.fleekUserId.eq(qFleekUserDetail.fleekUser.fleekUserId))
            .leftJoin(qPostLike).on(qPostLike.loungePost.loungePostId.eq(qLoungePost.loungePostId)
              .and(qPostLike.profile.profileId.eq(readerProfileId != null ? readerProfileId : 0L)))
            .where(predicate)
            .select(Projections.constructor(LoungePostVo.class,
              qLoungePost.loungePostId,
              qLoungePost.topic,
              qLoungePost.createdAt,
              qLoungePost.title,
              qLoungePost.content,
              qLoungePost.topicAttributes,
              qWriterProfile.profileName,
              qFleekUserDetail.gender,
              qLoungePost.likes,
              qLoungePost.comments,
              qLoungePost.views,
              qPostLike.postLikeId,
              qLoungePost.thumbnail
            ))
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
    public LoungePostVo getLoungePost(Long postId, Long readerProfileId) {
        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and(qLoungePost.loungePostId.eq(postId));

        return from(qLoungePost)
          .innerJoin(qLoungePost.profile, qProfile)
          .innerJoin(qProfile.fleekUser, qFleekUser)
          .innerJoin(qFleekUserDetail).on(qFleekUser.fleekUserId.eq(qFleekUserDetail.fleekUser.fleekUserId))
          .leftJoin(qPostLike).on(qPostLike.loungePost.loungePostId.eq(qLoungePost.loungePostId)
            .and(qPostLike.profile.profileId.eq(Objects.nonNull(readerProfileId) ? readerProfileId : 0L)))
          .where(predicate)
          .select(LOUNGE_POST_VO_PROJECTION)
          .fetchOne();
    }
}
