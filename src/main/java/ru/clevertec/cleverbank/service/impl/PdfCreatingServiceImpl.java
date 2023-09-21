package ru.clevertec.cleverbank.service.impl;

import ru.clevertec.cleverbank.service.AccountService;
import ru.clevertec.cleverbank.service.ClientService;
import ru.clevertec.cleverbank.service.PdfCreatingService;
import ru.clevertec.cleverbank.service.TransactionService;
import ru.clevertec.cleverbank.util.pdfwrite.PdfWriteUtil;

import static ru.clevertec.cleverbank.util.pdfwrite.PdfWriteUtil.accountTransactionsPdf;
import static ru.clevertec.cleverbank.util.pdfwrite.PdfWriteUtil.transactionPdf;

/**
 * Сервис для генерации pdf-файлов
 *
 * @see ClientServiceImpl
 * @see AccountServiceImpl
 * @see TransactionServiceImpl
 * @see PdfCreatingService
 * @see PdfWriteUtil
 */
public class PdfCreatingServiceImpl implements PdfCreatingService {

    private final ClientService clientService;
    private final AccountService accountService;
    private final TransactionService transactionService;

    public PdfCreatingServiceImpl() {
        this.clientService = new ClientServiceImpl();
        this.accountService = new AccountServiceImpl();
        this.transactionService = new TransactionServiceImpl();
    }

    @Override
    public void getTransactionPdf(Long id) {
        var transaction = transactionService.findById(id);

        var accountNumberFrom = transaction.getAccountNumberFrom();
        var accountNumberTo = transaction.getAccountNumberTo();

        var bankNameFrom = accountService.findBankNameByNumber(accountNumberFrom);
        var bankNameTo = accountService.findBankNameByNumber(accountNumberTo);
        var currency = accountService.findByNumber(accountNumberFrom).getCurrency();

        transactionPdf(bankNameFrom, bankNameTo, transaction, currency);
    }

    @Override
    public void getAccountTransactionsPdf(String accountNumber) {
        var transactions = transactionService.findAllByAccountNumber(accountNumber);
        var client = clientService.findByAccountNumber(accountNumber);
        var account = accountService.findByNumber(accountNumber);

        accountTransactionsPdf(client, account, transactions);
    }
}
