package run.fleek.domain.lounge;

import run.fleek.application.post.dto.LoungePostPageDto;
import run.fleek.domain.lounge.vo.LoungePostVo;

public interface LoungePostRepositoryCustom {
  LoungePostPageDto pageLoungePosts(Integer size, Integer page, Long readerProfileId, String topicCode,
                                    String writerName, String keyword);

  LoungePostVo getLoungePost(Long postId, Long readerProfileId);
}
