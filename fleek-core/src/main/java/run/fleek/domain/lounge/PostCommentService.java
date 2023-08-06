package run.fleek.domain.lounge;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.fleek.application.post.dto.PostCommentPageDto;
import run.fleek.common.exception.FleekException;

@Service
@RequiredArgsConstructor
public class PostCommentService {
  private final PostCommentRepository postCommentRepository;

  @Transactional
  public void addComment(PostComment comment) {
    postCommentRepository.save(comment);
  }

  @Transactional(readOnly = true)
  public PostCommentPageDto pagePostComments(Long postId, Integer page, Integer size) {
    return postCommentRepository.pageByPostId(postId, page, size);
  }

  @Transactional
  public PostComment getPostComment(Long commentId) {
    return postCommentRepository.findById(commentId)
        .orElseThrow(() -> new FleekException("존재하지 않는 댓글입니다."));
  }
}
