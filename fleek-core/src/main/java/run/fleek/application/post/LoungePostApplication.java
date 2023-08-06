package run.fleek.application.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import run.fleek.application.post.dto.*;
import run.fleek.common.exception.FleekException;
import run.fleek.common.response.FleekGeneralResponse;
import run.fleek.domain.lounge.*;
import run.fleek.domain.lounge.vo.LoungePostVo;
import run.fleek.domain.profile.Profile;
import run.fleek.domain.profile.ProfileService;

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

  @Transactional
  public void addLoungePost(LoungePostAddDto addDto) {

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
  }

  @Transactional(readOnly = true)
  public LoungePostPageDto pageLoungePosts(Integer size, Integer page, String profileName, String topicCode) {
    return loungePostService.pageLoungePosts(size, page, profileName, topicCode);
  }

  public LoungePostDto viewLoungePost(Long postId, String profileName) {
    LoungePostVo loungePost = loungePostService.getLoungePostVo(postId, profileName);
    LoungePost loungePostEntity = loungePostService.getLoungePost(postId);

    loungePostEntity.setViews(loungePostEntity.getViews() + 1);
    loungePostService.addLoungePost(loungePostEntity);

    return LoungePostDto.from(loungePost);
  }

  @Transactional
  public void addComment(Long postId, CommentAddDto addDto) {
    LoungePost loungePost = loungePostService.getLoungePost(postId);
    Profile profile = profileService.getProfileByProfileName(addDto.getProfileName())
      .orElseThrow(() -> new FleekException("존재하지 않는 프로필입니다."));

    postCommentService.addComment(PostComment.from(addDto, loungePost, profile));
    loungePost.setComments(loungePost.getComments() + 1);
    loungePostService.addLoungePost(loungePost);
  }

  @Transactional(readOnly = true)
  public PostCommentPageDto pagePostComments(Long postId, Integer page, Integer size) {
    return postCommentService.pagePostComments(postId, page, size);
  }

  @Transactional
  public FleekGeneralResponse likePost(Long postId, String profileName) {
    LoungePost loungePost = loungePostService.getLoungePost(postId);
    Profile profile = profileService.getProfileByProfileName(profileName)
        .orElseThrow(() -> new FleekException("존재하지 않는 프로필입니다."));

    loungePost.setLikes(loungePost.getLikes() + 1);
    loungePostService.addLoungePost(loungePost);

    return postLikeService.likePost(PostLike.from(loungePost, profile));
  }

  @Transactional
  public FleekGeneralResponse likeComment(Long commentId, String profileName) {
    PostComment postComment = postCommentService.getPostComment(commentId);
    Profile profile = profileService.getProfileByProfileName(profileName)
        .orElseThrow(() -> new FleekException("존재하지 않는 프로필입니다."));

    postComment.setLikes(postComment.getLikes() + 1);
    postCommentService.addComment(postComment);

    return commentLikeService.likeComment(CommentLike.from(postComment, profile));
  }
}
