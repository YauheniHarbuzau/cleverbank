package ru.clevertec.cleverbank.repository;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс, предоставляющий CRUD-операции
 */
public interface JdbcRepository<T> {

    Optional<T> findById(Long id);

    List<T> findAll();

    void create(T entity);

    void update(T entity);

    void save(T entity);

    void deleteById(Long id);
}
