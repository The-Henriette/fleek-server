package run.fleek.domain.lounge;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import run.fleek.domain.profile.Profile;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    boolean existsByLoungePostAndProfile(LoungePost loungePost, Profile profile);

  boolean existsByLoungePost_LoungePostIdAndProfile_ProfileName(Long postId, String profileName);

  void deleteByLoungePostAndProfile(LoungePost loungePost, Profile profile);
}
