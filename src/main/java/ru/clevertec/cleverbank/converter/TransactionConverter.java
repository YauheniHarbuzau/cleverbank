package ru.clevertec.cleverbank.converter;

import ru.clevertec.cleverbank.converter.impl.TransactionConverterImpl;
import ru.clevertec.cleverbank.dto.TransactionDto;
import ru.clevertec.cleverbank.dto.TransactionDtoWithLog;
import ru.clevertec.cleverbank.entity.Transaction;

/**
 * Интерфейс для конвертера Транзакция-DTO-Транзакция
 *
 * @see Transaction
 * @see TransactionDto
 * @see TransactionDtoWithLog
 * @see TransactionConverterImpl
 */
public interface TransactionConverter {

    TransactionDtoWithLog convert(Transaction transaction);

    Transaction convert(TransactionDto transactionDto);
}
