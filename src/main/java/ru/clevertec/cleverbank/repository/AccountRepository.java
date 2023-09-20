package ru.clevertec.cleverbank.repository;

import ru.clevertec.cleverbank.entity.Account;
import ru.clevertec.cleverbank.repository.impl.AccountRepositoryImpl;
import ru.clevertec.cleverbank.repository.impl.enumentity.Reamounting;

import java.util.Optional;

/**
 * Интерфейс репозитория для {@link Account}
 *
 * @see JdbcRepository
 * @see AccountRepositoryImpl
 * @see Reamounting
 */
public interface AccountRepository extends JdbcRepository<Account> {

    Optional<Account> findByNumber(String number);

    String findBankNameByNumber(String number);

    void reamounting(String number, Double amount, Reamounting operation);
}
