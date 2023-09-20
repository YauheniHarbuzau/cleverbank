package ru.clevertec.cleverbank.converter.impl;

import ru.clevertec.cleverbank.converter.TransactionConverter;
import ru.clevertec.cleverbank.dto.TransactionDto;
import ru.clevertec.cleverbank.dto.TransactionDtoWithLog;
import ru.clevertec.cleverbank.entity.Transaction;
import ru.clevertec.cleverbank.exeption.EntityNotCorrectException;
import ru.clevertec.cleverbank.util.date.DateTimeUtil;

/**
 * Конвертер Транзакция-DTO-Транзакция
 *
 * @see Transaction
 * @see TransactionDto
 * @see TransactionDtoWithLog
 * @see TransactionConverter
 * @see DateTimeUtil
 */
public class TransactionConverterImpl implements TransactionConverter {

    @Override
    public TransactionDtoWithLog convert(Transaction transaction) {
        if (transaction == null) {
            throw new EntityNotCorrectException();
        }
        return TransactionDtoWithLog.builder()
                .id(transaction.getId())
                .accountNumberFrom(transaction.getAccountNumberFrom())
                .accountNumberTo(transaction.getAccountNumberTo())
                .amount(transaction.getAmount())
                .currency(transaction.getCurrency())
                .dateCreation(DateTimeUtil.getDateTime(transaction.getDateCreation()))
                .lastModified(DateTimeUtil.getDateTime(transaction.getLastModified()))
                .version(transaction.getVersion())
                .build();
    }

    @Override
    public Transaction convert(TransactionDto transactionDto) {
        if (transactionDto == null) {
            throw new EntityNotCorrectException();
        }
        return Transaction.builder()
                .id(transactionDto.getId())
                .accountNumberFrom(transactionDto.getAccountNumberFrom())
                .accountNumberTo(transactionDto.getAccountNumberTo())
                .amount(transactionDto.getAmount())
                .build();
    }
}
