package run.fleek.domain.lounge;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostImageService {
  private final PostImageRepository postImageRepository;

  @Transactional
  public void addPostImages(List<PostImage> images) {
    postImageRepository.saveAll(images);
  }
}
