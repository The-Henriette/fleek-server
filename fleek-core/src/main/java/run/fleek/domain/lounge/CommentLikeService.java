package run.fleek.domain.lounge;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.fleek.common.response.FleekGeneralResponse;

@Service
@RequiredArgsConstructor
public class CommentLikeService {
  private final CommentLikeRepository commentLikeRepository;

  @Transactional
  public FleekGeneralResponse likeComment(CommentLike from) {
    if (commentLikeRepository.existsByCommentAndProfile(from.getComment(), from.getProfile())) {
      return FleekGeneralResponse.builder()
          .success(false)
          .errorMessage("이미 좋아요를 누른 댓글입니다.")
          .build();
    }

    commentLikeRepository.save(from);
    return FleekGeneralResponse.builder()
        .success(true)
        .build();
  }
}
