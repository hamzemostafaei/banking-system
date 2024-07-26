package com.hamze.banking.system.core.service;

import com.blazebit.persistence.CriteriaBuilder;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.hamze.banking.system.core.api.criteria.ISearchCriteria;
import com.hamze.banking.system.core.api.criteria.SortDTO;
import com.hamze.banking.system.core.api.criteria.SortDirectionEnum;
import com.hamze.banking.system.core.api.criteria.query.condition.Condition;
import com.hamze.banking.system.core.api.criteria.query.condition.ConditionTypeEnum;
import com.hamze.banking.system.core.api.criteria.query.condition.IGenericConditionItem;
import com.hamze.banking.system.core.api.criteria.query.predicate.IPredicateFactory;
import com.hamze.banking.system.core.api.criteria.query.predicate.PredicateFactoryRegistry;
import com.hamze.banking.system.core.api.data.ABaseDTO;
import com.hamze.banking.system.core.api.exception.BadSortColumnNameException;
import com.hamze.banking.system.core.api.mapper.IEntityMapper;
import com.hamze.banking.system.core.api.service.ICoreService;
import com.hamze.banking.system.data.access.entity.ABaseEntity;
import com.hamze.banking.system.shared.util.ReflectionUtil;
import jakarta.persistence.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Slf4j
@Service("ABaseCoreService")
@Transactional(propagation = Propagation.REQUIRED)
public abstract class ABaseCoreService<D extends ABaseDTO,
                                       E extends ABaseEntity,
                                       ID extends Serializable,
                                       C extends ISearchCriteria,
                                       R extends JpaRepository<E, ID>>
        implements ICoreService<D, E, ID, C, R> {

    private final Class<E> entityClass;
    private final Class<ID> idClass;
    private final Class<C> criteriaClass;
    private final List<String> fieldNames;

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    protected R repository;
    @Autowired
    protected EntityManager entityManager;
    @Autowired
    protected CriteriaBuilderFactory criteriaBuilderFactory;
    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private IEntityMapper<E, D> mapper;

    public ABaseCoreService() {
        this.entityClass = ReflectionUtil.getGenericParameterType(this, 1);
        this.idClass = ReflectionUtil.getGenericParameterType(this, 2);
        this.criteriaClass = ReflectionUtil.getGenericParameterType(this, 3);
        this.fieldNames = ReflectionUtil.getFieldNames(this.entityClass);
    }

    @Override
    public List<String> getFieldNames() {
        return fieldNames;
    }

    @Override
    public D save(D dto) {
        E entity = dtoToEntity(dto);
        E savedEntity = repository.save(entity);
        return entityToDTO(savedEntity);
    }

    @Override
    public Iterable<D> saveAll(Iterable<D> dtoList) {
        List<E> entities = dtoCollectionToEntity(dtoList);
        Iterable<E> savedEntities = repository.saveAll(entities);
        return entityCollectionToDTO(savedEntities);
    }

    @Override
    public D findById(ID id) {

        Optional<E> result = repository.findById(id);
        return result
                .map(this::entityToDTO)
                .orElse(null);
    }

    @Override
    public boolean existsById(ID id) {
        return repository.existsById(id);
    }

    @Override
    public List<D> findAll() {
        Iterable<E> entities = repository.findAll();
        return entityCollectionToDTO(entities);
    }

    @Override
    public List<D> findAllById(Collection<ID> idList) {
        List<E> fetchedEntities = repository.findAllById(idList);
        return entityCollectionToDTO(fetchedEntities);
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    @Override
    public void delete(D dto) {
        E entity = dtoToEntity(dto);
        repository.delete(entity);
    }

    @Override
    public void deleteAll(Collection<D> dtoList) {
        List<E> entities = dtoCollectionToEntity(dtoList);
        repository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public List<D> findAll(Sort sort) {
        Iterable<E> entities = repository.findAll(sort);
        return entityCollectionToDTO(entities);
    }

    @Override
    public Page<D> findAll(Pageable pageable) {
        Page<E> entities = repository.findAll(pageable);
        return entityPageToDTO(entities);
    }

    @Override
    public void flush() {
        repository.flush();
    }

    @Override
    public Page<D> search(C criteria) throws BadSortColumnNameException {

        CriteriaBuilder<E> criteriaBuilder = criteriaBuilderFactory.create(entityManager, entityClass);

        buildPredicate(criteria, criteriaBuilder);

        List<Sort.Order> sortItems = buildSortItems(criteria, criteriaBuilder);

        List<E> resultList;
        PageImpl<D> result = null;

        if (ObjectUtils.isNotEmpty(criteria.getOffset()) && ObjectUtils.isNotEmpty(criteria.getPageSize())) {
            int offset = criteria.getOffset();
            int pageSize = criteria.getPageSize();

            long totalResults;
            if (BooleanUtils.isTrue(criteria.getReturnTotalSize())) {
                totalResults = criteriaBuilder.getCountQuery().getSingleResult();
            } else {
                totalResults = -1;
            }

            criteriaBuilder.setFirstResult(offset * pageSize);
            criteriaBuilder.setMaxResults(pageSize);

            resultList = criteriaBuilder.getResultList();
            List<D> ds = entityCollectionToDTO(resultList);

            Pageable pageable = PageRequest.of(offset, pageSize, Sort.by(sortItems));

            result = new PageImpl<>(ds, pageable, totalResults);
        } else {
            resultList = criteriaBuilder.getResultList();
            if (!CollectionUtils.isEmpty(resultList)) {
                List<D> ds = entityCollectionToDTO(resultList);
                Pageable pageable = PageRequest.of(0, ds.size(), Sort.by(sortItems));
                result = new PageImpl<>(ds, pageable, ds.size());
            }
        }

        return result;
    }

    @Override
    public List<D> listSearch(C criteria) {

        CriteriaBuilder<E> criteriaBuilder = criteriaBuilderFactory.create(entityManager, entityClass);

        buildPredicate(criteria, criteriaBuilder);

        List<E> resultList = criteriaBuilder.getResultList();
        List<D> ds = null;
        if (!CollectionUtils.isEmpty(resultList)) {
            ds = entityCollectionToDTO(resultList);
        }

        return ds;
    }

    private List<Sort.Order> buildSortItems(C criteria, CriteriaBuilder<E> criteriaBuilder) throws BadSortColumnNameException {
        List<Sort.Order> sortItems;
        if (!CollectionUtils.isEmpty(criteria.getSortItems())) {
            sortItems = new ArrayList<>(criteria.getSortItems().size());
            for (SortDTO data : criteria.getSortItems()) {
                if (!getFieldNames().contains(data.getSortBy())) {
                    throw new BadSortColumnNameException("Bad sort column name: " + data.getSortBy());
                }
                if (data.getSortDirection() == SortDirectionEnum.Asc) {
                    criteriaBuilder.orderByAsc(data.getSortBy());
                    sortItems.add(new Sort.Order(Sort.Direction.ASC, data.getSortBy()));
                } else {
                    criteriaBuilder.orderByDesc(data.getSortBy());
                    sortItems.add(new Sort.Order(Sort.Direction.DESC, data.getSortBy()));
                }
            }
            return sortItems;
        } else if (ObjectUtils.isNotEmpty(criteria.getOffset()) && ObjectUtils.isNotEmpty(criteria.getPageSize())) {
            sortItems = new ArrayList<>(getFieldNames().size());
            for (String entityFieldName : getFieldNames()) {
                Id id = ReflectionUtil.getFieldAnnotation(entityClass, entityFieldName, Id.class);
                if (ObjectUtils.isNotEmpty(id)) {
                    criteriaBuilder.orderByAsc(entityFieldName);
                    sortItems.add(new Sort.Order(Sort.Direction.ASC, entityFieldName));
                } else {
                    EmbeddedId embeddedId = ReflectionUtil.getFieldAnnotation(entityClass, entityFieldName, EmbeddedId.class);
                    if (ObjectUtils.isNotEmpty(embeddedId)) {
                        List<Field> embeddedIdFields = ReflectionUtil.getFields(idClass);
                        for (Field embeddedIdField : embeddedIdFields) {
                            String idFieldName = embeddedIdField.getName();
                            Column idFieldColumn =
                                    ReflectionUtil.getFieldAnnotation(idClass, idFieldName, Column.class);
                            if (ObjectUtils.isNotEmpty(idFieldColumn)) {
                                criteriaBuilder.orderByAsc(idFieldName);
                                sortItems.add(new Sort.Order(Sort.Direction.ASC, idFieldName));
                            }
                        }
                        break;
                    }
                }
            }
            return sortItems;
        }
        return Collections.emptyList();
    }

    @SuppressWarnings("unchecked")
    private <T> IGenericConditionItem<T> getConditionItem(T value, ConditionTypeEnum conditionType) {
        Class<?> conditionItemType = conditionType.getConditionItemType();
        try {
            Object object = conditionItemType.getConstructor(Object.class).newInstance(value);
            return (IGenericConditionItem<T>) object;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private void buildPredicate(C criteria, CriteriaBuilder<E> criteriaBuilder) {

        List<String> fieldNames = ReflectionUtil.getFieldNames(criteriaClass);

        for (String fieldName : fieldNames) {
            Condition fieldAnnotation = ReflectionUtil.getFieldAnnotation(criteriaClass, fieldName, Condition.class);
            if (ObjectUtils.isNotEmpty(fieldAnnotation)) {
                ConditionTypeEnum conditionType = fieldAnnotation.type();
                String conditionFieldName = fieldAnnotation.fieldName();

                Object value = ReflectionUtil.getValue(criteriaClass, fieldName, criteria);

                if (ObjectUtils.isNotEmpty(value)) {
                    IPredicateFactory<E, IGenericConditionItem<?>> predicateBuilder = PredicateFactoryRegistry.getBuilder(conditionType);
                    if (ObjectUtils.isNotEmpty(predicateBuilder)) {
                        predicateBuilder.buildPredicate(criteriaBuilder, conditionFieldName, getConditionItem(value, conditionType));
                    }
                }
            }
        }
    }

    @Override
    public D getSingleResult(C criteria) {
        CriteriaBuilder<E> criteriaBuilder = criteriaBuilderFactory.create(entityManager, entityClass);

        buildPredicate(criteria, criteriaBuilder);

        E singleResult;
        try {
            singleResult = criteriaBuilder.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

        return entityToDTO(singleResult);
    }

    protected Session getCurrentSession() {
        return getEntityManager().unwrap(Session.class);
    }

    protected D entityToDTO(E entity) {
        if (entity == null) {
            return null;
        }

        return mapper.entityToDTO(entity);
    }

    protected E dtoToEntity(D dto) {
        if (dto == null) {
            return null;
        }

        E entity = mapper.dtoToEntity(dto);

        entity = getCurrentSession().merge(entity);

        return entity;
    }

    protected List<D> entityCollectionToDTO(Iterable<E> entities) {
        if (entities == null) {
            return null;
        }

        List<D> dtoList = new ArrayList<>();
        for (E entity : entities) {
            dtoList.add(entityToDTO(entity));
        }

        return dtoList;
    }

    protected List<E> dtoCollectionToEntity(Iterable<D> dtoList) {
        if (dtoList == null) {
            return null;
        }

        List<E> entities = new ArrayList<>();
        for (D dto : dtoList) {
            entities.add(dtoToEntity(dto));
        }

        return entities;
    }

    protected Page<D> entityPageToDTO(Page<E> entityPage) {
        if (entityPage == null) {
            return null;
        }

        List<D> dtoList = entityCollectionToDTO(entityPage);

        return new PageImpl<>(dtoList, entityPage.getPageable(), entityPage.getTotalElements());
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public boolean exists(C criteria) {
        CriteriaBuilder<E> criteriaBuilder = criteriaBuilderFactory.create(entityManager, entityClass);
        buildPredicate(criteria, criteriaBuilder);
        Long count = criteriaBuilder.getCountQuery().getSingleResult();
        return count > 0;
    }
}
