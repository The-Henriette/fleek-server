package run.fleek.domain.users.wallet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserWalletRepository extends JpaRepository<UserWallet, Long> {
  Optional<UserWallet> findByFleekUser_FleekUserId(Long userId);
}
