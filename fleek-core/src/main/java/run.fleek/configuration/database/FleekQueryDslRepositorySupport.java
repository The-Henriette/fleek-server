package run.fleek.configuration.database;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public abstract class FleekQueryDslRepositorySupport extends QuerydslRepositorySupport {

    public FleekQueryDslRepositorySupport(Class<?> domainClass) {
      super(domainClass);
    }

    @Override
    @PersistenceContext(unitName = "fleek")
    public void setEntityManager(EntityManager entityManager) {
      super.setEntityManager(entityManager);
    }

}
