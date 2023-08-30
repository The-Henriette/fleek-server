package run.fleek.domain.fruitman.meta;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostalCodeMetaRepository extends JpaRepository<PostalCodeMeta, String> {
  List<PostalCodeMeta> findAllBySidoIn(List<String> sido);
  List<PostalCodeMeta> findAllBySigunguIn(List<String> siGunGu);
}
