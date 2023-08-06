package run.fleek.domain.lounge;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import run.fleek.common.response.FleekGeneralResponse;

@Service
@RequiredArgsConstructor
public class PostLikeService {
  private final PostLikeRepository postLikeRepository;

  public FleekGeneralResponse likePost(PostLike postLike) {
    if (postLikeRepository.existsByLoungePostAndProfile(postLike.getLoungePost(), postLike.getProfile())) {
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
}
