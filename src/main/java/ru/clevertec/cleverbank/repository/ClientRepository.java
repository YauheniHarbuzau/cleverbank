package ru.clevertec.cleverbank.repository;

import ru.clevertec.cleverbank.entity.Client;
import ru.clevertec.cleverbank.repository.impl.ClientRepositoryImpl;

import java.util.Optional;

/**
 * Интерфейс репозитория для {@link Client}
 *
 * @see JdbcRepository
 * @see ClientRepositoryImpl
 */
public interface ClientRepository extends JdbcRepository<Client> {

    Optional<Client> findByAccountNumber(String accountNumber);
}
