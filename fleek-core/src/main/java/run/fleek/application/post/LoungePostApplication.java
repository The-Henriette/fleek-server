package run.fleek.application.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import run.fleek.application.post.dto.*;
import run.fleek.common.exception.FleekException;
import run.fleek.common.response.FleekGeneralResponse;
import run.fleek.domain.lounge.*;
import run.fleek.domain.lounge.vo.LoungePostVo;
import run.fleek.domain.profile.Profile;
import run.fleek.domain.profile.ProfileService;
import run.fleek.domain.users.FleekUser;
import run.fleek.domain.users.FleekUserDetail;
import run.fleek.domain.users.FleekUserDetailService;
import run.fleek.utils.JsonUtil;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LoungePostApplication {

  private final LoungePostService loungePostService;
  private final PostImageService postImageService;
  private final ProfileService profileService;
  private final PostCommentService postCommentService;
  private final PostLikeService postLikeService;
  private final CommentLikeService commentLikeService;
  private final FleekUserDetailService fleekUserDetailService;

  @Transactional
  public LoungePostDto addLoungePost(LoungePostAddDto addDto) {

    Profile profile = profileService.getProfileByProfileName(addDto.getProfileName())
      .orElseThrow(() -> new FleekException("존재하지 않는 프로필입니다."));

    LoungePost loungePost = loungePostService.addLoungePost(addDto, profile);

    postImageService.addPostImages(addDto.getPhotos().stream()
      .map(ph -> PostImage.builder()
        .loungePost(loungePost)
        .imageUrl(ph)
        .build())
      .collect(Collectors.toList())
    );

    FleekUser user = profile.getFleekUser();
    FleekUserDetail detail = fleekUserDetailService.getUserDetail(user);

    return buildLoungePostResponse(loungePost, profile, detail, false);
  }

  @Transactional(readOnly = true)
  public LoungePostPageDto pageLoungePosts(Integer size, Integer page, String profileName, String topicCode,
                                           String writerName, String keyword) {
    if (StringUtils.hasLength(profileName)) {
      Profile readerProfile = profileService.getProfileByProfileName(profileName)
        .orElseThrow(() -> new FleekException("존재하지 않는 프로필입니다."));

      return loungePostService.pageLoungePosts(size, page, readerProfile.getProfileId(), topicCode, writerName, keyword);
    }

    return loungePostService.pageLoungePosts(size, page, null, topicCode, writerName, keyword);
  }

  @Transactional
  public LoungePostDto viewLoungePost(Long postId, String profileName) {

    LoungePostVo loungePost;
    if (StringUtils.hasLength(profileName)) {
      Profile readerProfile = profileService.getProfileByProfileName(profileName)
        .orElseThrow(() -> new FleekException("존재하지 않는 프로필입니다."));
      loungePost = loungePostService.getLoungePostVo(postId, readerProfile.getProfileId());
    } else {
      loungePost = loungePostService.getLoungePostVo(postId, null);
    }

    LoungePost loungePostEntity = loungePostService.getLoungePost(postId);

    loungePostEntity.setViews(loungePostEntity.getViews() + 1);
    loungePostService.addLoungePost(loungePostEntity);

    return LoungePostDto.from(loungePost);
  }

  @Transactional
  public PostCommentDto addComment(Long postId, CommentAddDto addDto) {
    LoungePost loungePost = loungePostService.getLoungePost(postId);
    Profile profile = profileService.getProfileByProfileName(addDto.getProfileName())
      .orElseThrow(() -> new FleekException("존재하지 않는 프로필입니다."));

    PostComment comment = postCommentService.addComment(PostComment.from(addDto, loungePost, profile));
    loungePost.setComments(loungePost.getComments() + 1);
    loungePostService.addLoungePost(loungePost);

    if (Objects.nonNull(addDto.getParentCommentId())) {
      PostComment parentComment = postCommentService.getPostComment(addDto.getParentCommentId());
      parentComment.setSubComments(parentComment.getSubComments() + 1);
      postCommentService.addComment(parentComment);
    }

    FleekUser user = profile.getFleekUser();
    FleekUserDetail detail = fleekUserDetailService.getUserDetail(user);

    return PostCommentDto.builder()
      .commentId(comment.getPostCommentId())
      .profileName(profile.getProfileName())
      .content(comment.getContent())
      .createdAt(comment.getCreatedAt())
      .likes(comment.getLikes())
      .didLike(false)
      .profileGender(detail.getGender().name())
      .subComments(comment.getSubComments())
      .parentCommentId(comment.getParentCommentId())
      .build();
  }

  @Transactional(readOnly = true)
  public PostCommentPageDto pagePostComments(Long postId, Integer page, Integer size, String profileName, Long parentCommentId) {
    if (StringUtils.hasLength(profileName)) {
      Profile readerProfile = profileService.getProfileByProfileName(profileName)
        .orElseThrow(() -> new FleekException("존재하지 않는 프로필입니다."));

      return postCommentService.pagePostComments(postId, page, size, readerProfile.getProfileId(), parentCommentId);
    }

    return postCommentService.pagePostComments(postId, page, size, null, parentCommentId);
  }

  @Transactional
  public FleekGeneralResponse likePost(Long postId, String profileName) {
    LoungePost loungePost = loungePostService.getLoungePost(postId);
    Profile profile = profileService.getProfileByProfileName(profileName)
        .orElseThrow(() -> new FleekException("존재하지 않는 프로필입니다."));

    if (postLikeService.isLiked(postId, profileName)) {
      loungePost.setLikes(Math.max(loungePost.getLikes() - 1, 0));
    } else {
      loungePost.setLikes(loungePost.getLikes() + 1);
    }

    loungePostService.addLoungePost(loungePost);

    return postLikeService.likePost(PostLike.from(loungePost, profile));
  }

  @Transactional
  public FleekGeneralResponse likeComment(Long commentId, String profileName) {
    PostComment postComment = postCommentService.getPostComment(commentId);
    Profile profile = profileService.getProfileByProfileName(profileName)
        .orElseThrow(() -> new FleekException("존재하지 않는 프로필입니다."));

    Optional<CommentLike> didLike = commentLikeService.getCommentLikeByCommentAndProfile(postComment, profile);
    if (didLike.isPresent()) {
      postComment.setLikes(Math.max(postComment.getLikes() - 1, 0));
      postCommentService.addComment(postComment);
      return commentLikeService.dislikeComment(didLike.get());
    } else {
      postComment.setLikes(postComment.getLikes() + 1);
      postCommentService.addComment(postComment);
      return commentLikeService.likeComment(CommentLike.from(postComment, profile));
    }
  }

  @Transactional
  public LoungePostDto editLoungePost(Long postId, LoungePostAddDto loungePostAddDto) {
    LoungePost loungePost = loungePostService.getLoungePost(postId);

    loungePost.setTitle(Optional.ofNullable(loungePostAddDto.getTitle()).orElse(loungePost.getTitle()));
    loungePost.setContent(Optional.ofNullable(loungePostAddDto.getContent()).orElse(loungePost.getContent()));
    loungePost.setTopicAttributes(JsonUtil.write(loungePostAddDto.getAttributes()));

    loungePostService.addLoungePost(loungePost);

    Profile writer = loungePost.getProfile();

    FleekUser user = writer.getFleekUser();
    FleekUserDetail detail = fleekUserDetailService.getUserDetail(user);

    boolean didLike = postLikeService.isLiked(postId, writer.getProfileName());

    return buildLoungePostResponse(loungePost, writer, detail, didLike);
  }

  private LoungePostDto buildLoungePostResponse(LoungePost loungePost, Profile profile, FleekUserDetail detail, boolean didLike) {
    return LoungePostDto.builder()
      .postId(loungePost.getLoungePostId())
      .topicCode(loungePost.getTopic().name())
      .createdAt(loungePost.getCreatedAt())
      .title(loungePost.getTitle())
      .content(loungePost.getContent())
      .attributes(JsonUtil.readMap(loungePost.getTopicAttributes()))
      .profileName(profile.getProfileName())
      .profileGender(detail.getGender().name())
      .likes(loungePost.getLikes())
      .comments(loungePost.getComments())
      .views(loungePost.getViews())
      .didLike(didLike)
      .thumbnailImage(loungePost.getThumbnail())
      .build();
  }

  @Transactional
  public PostCommentDto editComment(Long commentId, CommentAddDto addDto) {
    PostComment postComment = postCommentService.getPostComment(commentId);

    postComment.setContent(addDto.getContent());
    postCommentService.addComment(postComment);

    Profile writer = postComment.getProfile();

    FleekUser user = writer.getFleekUser();
    FleekUserDetail detail = fleekUserDetailService.getUserDetail(user);

    boolean didLike = commentLikeService.getCommentLikeByCommentAndProfile(postComment, writer).isPresent();

    return PostCommentDto.builder()
      .commentId(postComment.getPostCommentId())
      .profileName(writer.getProfileName())
      .content(postComment.getContent())
      .createdAt(postComment.getCreatedAt())
      .likes(postComment.getLikes())
      .didLike(didLike)
      .profileGender(detail.getGender().name())
      .subComments(postComment.getSubComments())
      .parentCommentId(postComment.getParentCommentId())
      .build();
  }

  @Transactional
  public FleekGeneralResponse deletePost(Long postId) {
    LoungePost loungePost = loungePostService.getLoungePost(postId);

    postCommentService.deletePostComments(loungePost);
    loungePostService.deleteLoungePost(loungePost);
    return FleekGeneralResponse.builder()
      .success(true)
      .errorMessage(null)
      .build();
  }

  @Transactional
  public FleekGeneralResponse deleteComment(Long commentId) {
    PostComment comment = postCommentService.getPostComment(commentId);
    List<PostComment> children = postCommentService.getChildren(commentId);

    if (Objects.nonNull(comment.getParentCommentId())) {
      PostComment parent = postCommentService.getPostComment(comment.getParentCommentId());
      parent.setSubComments(Math.max(parent.getSubComments() - 1 - children.size(), 0));
      postCommentService.addComment(parent);
    }

    LoungePost post = comment.getLoungePost();
    post.setComments(Math.max(post.getComments() - 1, 0));
    loungePostService.addLoungePost(post);

    postCommentService.deletePostCommentsByCommentId(commentId);
    postCommentService.deleteChildren(commentId);
    return FleekGeneralResponse.builder()
      .success(true)
      .errorMessage(null)
      .build();
  }
}
