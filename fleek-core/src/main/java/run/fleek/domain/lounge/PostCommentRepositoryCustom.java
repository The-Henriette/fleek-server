package run.fleek.domain.lounge;

import run.fleek.application.post.dto.PostCommentPageDto;

public interface PostCommentRepositoryCustom {
  PostCommentPageDto pageByPostId(Long postId, Integer page, Integer size, Long readerProfileId, Long parentCommentId);
}
