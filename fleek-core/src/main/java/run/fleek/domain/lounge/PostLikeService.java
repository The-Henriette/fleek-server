package run.fleek.domain.lounge;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.fleek.common.response.FleekGeneralResponse;

@Service
@RequiredArgsConstructor
public class PostLikeService {
  private final PostLikeRepository postLikeRepository;

  @Transactional
  public FleekGeneralResponse likePost(PostLike postLike) {
    if (postLikeRepository.existsByLoungePostAndProfile(postLike.getLoungePost(), postLike.getProfile())) {
      postLikeRepository.deleteByLoungePostAndProfile(postLike.getLoungePost(), postLike.getProfile());

      return FleekGeneralResponse.builder()
          .success(false)
          .errorMessage("이미 좋아요를 누른 게시물입니다.")
          .build();
    }

    postLikeRepository.save(postLike);
    return FleekGeneralResponse.builder()
        .success(true)
        .build();
  }

  @Transactional(readOnly = true)
  public boolean isLiked(Long postId, String profileName) {
    return postLikeRepository.existsByLoungePost_LoungePostIdAndProfile_ProfileName(postId, profileName);
  }
}
