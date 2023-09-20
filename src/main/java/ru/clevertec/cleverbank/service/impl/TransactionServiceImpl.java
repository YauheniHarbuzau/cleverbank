package ru.clevertec.cleverbank.service.impl;

import ru.clevertec.cleverbank.converter.TransactionConverter;
import ru.clevertec.cleverbank.converter.impl.TransactionConverterImpl;
import ru.clevertec.cleverbank.dto.AccountDtoWithLog;
import ru.clevertec.cleverbank.dto.ClientDtoWithLog;
import ru.clevertec.cleverbank.dto.TransactionDto;
import ru.clevertec.cleverbank.dto.TransactionDtoWithLog;
import ru.clevertec.cleverbank.entity.Transaction;
import ru.clevertec.cleverbank.exeption.TransactionNotFoundException;
import ru.clevertec.cleverbank.repository.TransactionRepository;
import ru.clevertec.cleverbank.repository.impl.TransactionRepositoryImpl;
import ru.clevertec.cleverbank.service.AccountService;
import ru.clevertec.cleverbank.service.ClientService;
import ru.clevertec.cleverbank.service.TransactionService;
import ru.clevertec.cleverbank.util.pdfwrite.PdfWriteUtil;

import java.util.List;

import static ru.clevertec.cleverbank.util.pdfwrite.PdfWriteUtil.getAccountTransactionsPdf;
import static ru.clevertec.cleverbank.util.pdfwrite.PdfWriteUtil.getTransactionPdf;

/**
 * Сервис для {@link Transaction}
 *
 * @see TransactionDto
 * @see TransactionDtoWithLog
 * @see TransactionConverterImpl
 * @see TransactionRepositoryImpl
 * @see TransactionService
 * @see AccountServiceImpl
 * @see PdfWriteUtil
 */
public class TransactionServiceImpl implements TransactionService {

    private final TransactionConverter transactionConverter;
    private final TransactionRepository transactionRepository;
    private final ClientService clientService;
    private final AccountService accountService;

    public TransactionServiceImpl() {
        this.transactionConverter = new TransactionConverterImpl();
        this.transactionRepository = new TransactionRepositoryImpl();
        this.clientService = new ClientServiceImpl();
        this.accountService = new AccountServiceImpl();
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
     * @see ClientServiceImpl#findByAccountNumber(String)
     * @see AccountServiceImpl#findByNumber(String)
     * @see PdfWriteUtil#getAccountTransactionsPdf(ClientDtoWithLog, AccountDtoWithLog, List)
     */
    @Override
    public List<TransactionDtoWithLog> findAllByAccountNumber(String accountNumber) {
        var transactions = transactionRepository.findAllByAccountNumber(accountNumber);
        var client = clientService.findByAccountNumber(accountNumber);
        var account = accountService.findByNumber(accountNumber);

        getAccountTransactionsPdf(client, account, transactions);
        return transactions.stream().map(transactionConverter::convert).toList();
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
     * @see AccountServiceImpl#findByNumber(String)
     * @see AccountServiceImpl#findBankNameByNumber(String)
     * @see TransactionRepositoryImpl#transactionExecution(String, String, Double)
     * @see PdfWriteUtil#getTransactionPdf(String, String, TransactionDto, String)
     */
    @Override
    public void transactionExecution(TransactionDto transactionDto) {
        var accountNumberFrom = transactionDto.getAccountNumberFrom();
        var accountNumberTo = transactionDto.getAccountNumberTo();
        var amount = transactionDto.getAmount();

        var bankNameFrom = accountService.findBankNameByNumber(accountNumberFrom);
        var bankNameTo = accountService.findBankNameByNumber(accountNumberTo);
        var currency = accountService.findByNumber(accountNumberFrom).getCurrency();

        getTransactionPdf(bankNameFrom, bankNameTo, transactionDto, currency);
        transactionRepository.transactionExecution(accountNumberFrom, accountNumberTo, amount);
    }
}
