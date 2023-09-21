package ru.clevertec.cleverbank.service.impl;

import ru.clevertec.cleverbank.converter.TransactionConverter;
import ru.clevertec.cleverbank.converter.impl.TransactionConverterImpl;
import ru.clevertec.cleverbank.dto.TransactionDto;
import ru.clevertec.cleverbank.dto.TransactionDtoWithLog;
import ru.clevertec.cleverbank.entity.Transaction;
import ru.clevertec.cleverbank.exeption.TransactionNotFoundException;
import ru.clevertec.cleverbank.repository.TransactionRepository;
import ru.clevertec.cleverbank.repository.impl.TransactionRepositoryImpl;
import ru.clevertec.cleverbank.service.TransactionService;

import java.util.List;

/**
 * Сервис для {@link Transaction}
 *
 * @see TransactionDto
 * @see TransactionDtoWithLog
 * @see TransactionConverterImpl
 * @see TransactionRepositoryImpl
 * @see TransactionService
 * @see AccountServiceImpl
 */
public class TransactionServiceImpl implements TransactionService {

    private final TransactionConverter transactionConverter;
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl() {
        this.transactionConverter = new TransactionConverterImpl();
        this.transactionRepository = new TransactionRepositoryImpl();
    }

    /**
     * Получение транзакции по ID
     *
     * @param id ID транзакции
     * @return TransactionDtoWithLog
     * @see TransactionRepositoryImpl#findById(Long)
     * @see TransactionConverterImpl#convert(Transaction)
     */
    @Override
    public TransactionDtoWithLog findById(Long id) {
        return transactionConverter.convert(transactionRepository.findById(id).orElseThrow(TransactionNotFoundException::new));
    }

    /**
     * Получение всех транзакций
     *
     * @return List<TransactionDtoWithLog>
     * @see TransactionRepositoryImpl#findAll()
     * @see TransactionConverterImpl#convert(Transaction)
     */
    @Override
    public List<TransactionDtoWithLog> findAll() {
        return transactionRepository.findAll().stream().map(transactionConverter::convert).toList();
    }

    /**
     * Получение всех транзакций по номеру счета
     *
     * @param accountNumber номер счета
     * @return List<TransactionDtoWithLog>
     * @see TransactionRepositoryImpl#findAllByAccountNumber(String)
     * @see TransactionConverterImpl#convert(Transaction)
     */
    @Override
    public List<TransactionDtoWithLog> findAllByAccountNumber(String accountNumber) {
        return transactionRepository.findAllByAccountNumber(accountNumber).stream().map(transactionConverter::convert).toList();
    }

    /**
     * Сохранение транзакции
     *
     * @param transactionDto DTO транзакции
     * @see TransactionRepositoryImpl#save(Transaction)
     * @see TransactionConverterImpl#convert(TransactionDto)
     */
    @Override
    public void save(TransactionDto transactionDto) {
        transactionRepository.save(transactionConverter.convert(transactionDto));
    }

    /**
     * Удаление транзакции по ID
     *
     * @param id ID транзакции
     * @see TransactionRepositoryImpl#deleteById(Long)
     */
    @Override
    public void deleteById(Long id) {
        transactionRepository.deleteById(id);
    }

    /**
     * Выполнение транзакции
     *
     * @param transactionDto транзакция
     * @see TransactionRepositoryImpl#transactionExecution(String, String, Double)
     */
    @Override
    public void transactionExecution(TransactionDto transactionDto) {
        var accountNumberFrom = transactionDto.getAccountNumberFrom();
        var accountNumberTo = transactionDto.getAccountNumberTo();
        var amount = transactionDto.getAmount();

        transactionRepository.transactionExecution(accountNumberFrom, accountNumberTo, amount);
    }
}
