package ru.clevertec.cleverbank.service;

import ru.clevertec.cleverbank.dto.TransactionDto;
import ru.clevertec.cleverbank.dto.TransactionDtoWithLog;
import ru.clevertec.cleverbank.entity.Transaction;
import ru.clevertec.cleverbank.service.impl.TransactionServiceImpl;

import java.util.List;

/**
 * Интерфейс-сервис для {@link Transaction}
 *
 * @see TransactionDto
 * @see TransactionDtoWithLog
 * @see TransactionServiceImpl
 */
public interface TransactionService {

    TransactionDtoWithLog findById(Long id);

    List<TransactionDtoWithLog> findAll();

    List<TransactionDtoWithLog> findAllByAccountNumber(String accountNumber);

    void save(TransactionDto transactionDto);

    void deleteById(Long id);

    void transactionExecution(TransactionDto transactionDto);
}
