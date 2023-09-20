package ru.clevertec.cleverbank.repository;

import ru.clevertec.cleverbank.entity.Transaction;
import ru.clevertec.cleverbank.repository.impl.TransactionRepositoryImpl;

import java.util.List;

/**
 * Интерфейс репозитория для {@link Transaction}
 *
 * @see JdbcRepository
 * @see TransactionRepositoryImpl
 */
public interface TransactionRepository extends JdbcRepository<Transaction> {

    List<Transaction> findAllByAccountNumber(String accountNumber);

    void transactionExecution(String accountNumberFrom, String accountNumberTo, Double amount);
}
