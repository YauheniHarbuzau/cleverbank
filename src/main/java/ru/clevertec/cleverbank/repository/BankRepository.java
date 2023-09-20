package ru.clevertec.cleverbank.repository;

import ru.clevertec.cleverbank.entity.Bank;
import ru.clevertec.cleverbank.repository.impl.BankRepositoryImpl;

/**
 * Интерфейс репозитория для {@link Bank}
 *
 * @see JdbcRepository
 * @see BankRepositoryImpl
 */
public interface BankRepository extends JdbcRepository<Bank> {
}
