package run.fleek.domain.fruitman.meta;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostalCodeMetaRepository extends JpaRepository<PostalCodeMeta, String> {
  List<PostalCodeMeta> findAllBySido(String sido);
  List<PostalCodeMeta> findAllBySigungu(String siGunGu);
  List<PostalCodeMeta> findAllByEupmyeon(String eupmyun);
}
