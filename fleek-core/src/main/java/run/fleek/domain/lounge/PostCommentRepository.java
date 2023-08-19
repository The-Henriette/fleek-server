package run.fleek.domain.lounge;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, Long>, PostCommentRepositoryCustom {
  void deleteByLoungePost(LoungePost loungePost);

  void deleteByParentCommentId(Long commentId);

  List<PostComment> findByParentCommentId(Long commentId);
}
