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
  public LoungePostPageDto pageLoungePosts(Integer size, Integer page, Long readerProfileId, String topicCode) {
    return loungePostRepository.pageLoungePosts(size, page, readerProfileId, topicCode);
  }

  @Transactional(readOnly = true)
  public LoungePostVo getLoungePostVo(Long postId, Long readerProfileId) {
    return loungePostRepository.getLoungePost(postId, readerProfileId);
  }

  @Transactional(readOnly = true)
  public LoungePost getLoungePost(Long postId) {
    return loungePostRepository.getById(postId);
  }

  @Transactional
  public void addLoungePost(LoungePost loungePostEntity) {
    loungePostRepository.save(loungePostEntity);
  }

  @Transactional
  public void deleteLoungePost(LoungePost loungePost) {
    loungePostRepository.delete(loungePost);
  }
}
