package run.fleek.domain.lounge;

import run.fleek.application.post.dto.LoungePostPageDto;
import run.fleek.domain.lounge.vo.LoungePostVo;

public interface LoungePostRepositoryCustom {
  LoungePostPageDto pageLoungePosts(Integer size, Integer page, String profileName, String topicCode);

  LoungePostVo getLoungePost(Long postId, String profileName);
}
