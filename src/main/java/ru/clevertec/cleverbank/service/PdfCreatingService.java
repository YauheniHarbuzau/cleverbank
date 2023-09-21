package ru.clevertec.cleverbank.service;

/**
 * Интерфейс-сервис для генерации pdf-файлов
 */
public interface PdfCreatingService {

    void getTransactionPdf(Long id);

    void getAccountTransactionsPdf(String accountNumber);
}
