package run.fleek.domain.lounge;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.fleek.application.post.dto.PostCommentPageDto;
import run.fleek.common.exception.FleekException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostCommentService {
  private final PostCommentRepository postCommentRepository;

  @Transactional
  public PostComment addComment(PostComment comment) {
    return postCommentRepository.save(comment);
  }

  @Transactional(readOnly = true)
  public PostCommentPageDto pagePostComments(Long postId, Integer page, Integer size, Long readerProfileId, Long parentCommentId) {
    return postCommentRepository.pageByPostId(postId, page, size, readerProfileId, parentCommentId);
  }

  @Transactional
  public PostComment getPostComment(Long commentId) {
    return postCommentRepository.findById(commentId)
        .orElseThrow(() -> new FleekException("존재하지 않는 댓글입니다."));
  }

  @Transactional
  public void deletePostComments(LoungePost loungePost) {
    postCommentRepository.deleteByLoungePost(loungePost);
  }

  @Transactional
  public void deletePostCommentsByCommentId(Long commentId) {
    postCommentRepository.deleteById(commentId);
  }

  @Transactional
  public void deleteChildren(Long commentId) {
    postCommentRepository.deleteByParentCommentId(commentId);
  }

  @Transactional(readOnly = true)
  public List<PostComment> getChildren(Long commentId) {
    return postCommentRepository.findByParentCommentId(commentId);
  }
}
