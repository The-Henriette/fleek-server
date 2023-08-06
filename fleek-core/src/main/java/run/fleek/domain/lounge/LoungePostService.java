package run.fleek.domain.lounge;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.fleek.application.post.dto.LoungePostAddDto;
import run.fleek.application.post.dto.LoungePostPageDto;
import run.fleek.domain.lounge.vo.LoungePostVo;
import run.fleek.domain.profile.Profile;

@Service
@RequiredArgsConstructor
public class LoungePostService {

  private final LoungePostRepository loungePostRepository;

  @Transactional
  public LoungePost addLoungePost(LoungePostAddDto addDto, Profile profile) {
    LoungePost post = LoungePost.from(addDto, profile);
    return loungePostRepository.save(post);
  }

  @Transactional(readOnly = true)
  public LoungePostPageDto pageLoungePosts(Integer size, Integer page, String profileName, String topicCode) {
    return loungePostRepository.pageLoungePosts(size, page, profileName, topicCode);
  }

  @Transactional(readOnly = true)
  public LoungePostVo getLoungePostVo(Long postId, String profileName) {
    return loungePostRepository.getLoungePost(postId, profileName);
  }

  public LoungePost getLoungePost(Long postId) {
    return loungePostRepository.getById(postId);
  }

  public void addLoungePost(LoungePost loungePostEntity) {
    loungePostRepository.save(loungePostEntity);
  }
}
