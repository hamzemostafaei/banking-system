package com.hamze.banking.system.core.api.service;

import com.hamze.banking.system.core.api.criteria.ISearchCriteria;
import com.hamze.banking.system.core.api.data.ABaseDTO;
import com.hamze.banking.system.core.api.exception.BadSortColumnNameException;
import com.hamze.banking.system.data.access.entity.ABaseEntity;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Transactional(propagation = Propagation.REQUIRED)
public interface ICoreService<D extends ABaseDTO,
                              E extends ABaseEntity,
                              ID extends Serializable,
                              C extends ISearchCriteria,
                              R extends JpaRepository<E, ID>> {

    List<String> getFieldNames();

    D save(D dto);

    Iterable<D> saveAll(Iterable<D> entities);

    D findById(ID id);

    boolean existsById(ID id);

    List<D> findAll();

    List<D> findAllById(Collection<ID> idList);

    long count();

    void deleteById(ID id);

    void delete(D dto);

    void deleteAll(Collection<D> dtoList);

    void deleteAll();

    List<D> findAll(Sort sort);

    Page<D> findAll(Pageable pageable);

    void flush();

    Page<D> search(C criteria) throws BadSortColumnNameException;

    List<D> listSearch(C criteria);

    D getSingleResult(C criteria);

    EntityManager getEntityManager();

    boolean exists(C criteria);
}
