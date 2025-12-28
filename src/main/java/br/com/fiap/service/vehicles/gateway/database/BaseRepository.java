package br.com.fiap.service.vehicles.gateway.database;

import jakarta.transaction.Transactional;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
@Transactional
public interface BaseRepository<T, ID extends Serializable>
    extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

  <S extends T> void refresh(S entity);
}
