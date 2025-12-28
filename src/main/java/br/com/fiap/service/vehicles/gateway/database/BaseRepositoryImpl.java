package br.com.fiap.service.vehicles.gateway.database;

import jakarta.persistence.EntityManager;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

public class BaseRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID>
    implements BaseRepository<T, ID> {

  private final EntityManager entityManager;

  public BaseRepositoryImpl(
      JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
    super(entityInformation, entityManager);
    this.entityManager = entityManager;
  }

  @NotNull
  @Transactional
  public <S extends T> S save(@NotNull S entity) {
    S result = super.save(entity);
    this.entityManager.flush();
    this.entityManager.refresh(result);
    return result;
  }

  @NotNull
  public <S extends T> S saveAndFlush(@NotNull S entity) {
    return this.save(entity);
  }

  @Override
  public <S extends T> void refresh(S entity) {
    this.entityManager.refresh(entity);
  }
}
