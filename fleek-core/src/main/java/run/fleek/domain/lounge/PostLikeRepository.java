package run.fleek.domain.lounge;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import run.fleek.domain.profile.Profile;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    boolean existsByLoungePostAndProfile(LoungePost loungePost, Profile profile);
}
