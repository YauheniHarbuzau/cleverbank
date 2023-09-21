package ru.clevertec.cleverbank.util.pdfwrite;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import lombok.SneakyThrows;
import ru.clevertec.cleverbank.dto.AccountDtoWithLog;
import ru.clevertec.cleverbank.dto.ClientDtoWithLog;
import ru.clevertec.cleverbank.dto.TransactionDtoWithLog;
import ru.clevertec.cleverbank.util.currencyconverter.CurrencyConverterUtil;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.clevertec.cleverbank.util.currencyconverter.CurrencyConverterUtil.currencyConvert;

/**
 * Утилитарный класс для генерации pdf-файлов
 *
 * @see CurrencyConverterUtil#currencyConvert(String, String, Double)
 */
public class PdfWriteUtil {

    private static Long fileTransactionNumber = 1L;
    private static Long fileAccountTransactionsNumber = 1L;

    @SneakyThrows
    public static void transactionPdf(String bankNameFrom, String bankNameTo, TransactionDtoWithLog transaction, String currency) {
        String transactionReceiptNumber = new DecimalFormat("0000000000").format(fileTransactionNumber);
        fileTransactionNumber++;

        PdfWriter writer = new PdfWriter("check\\transaction_" + transactionReceiptNumber + ".pdf");
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        doc.add(transactionPdfTable(transactionReceiptNumber, bankNameFrom, bankNameTo, transaction, currency));
        doc.close();
    }

    private static Table transactionPdfTable(String transactionReceiptNumber, String bankNameFrom, String bankNameTo, TransactionDtoWithLog transaction, String currency) {
        Table table = new Table(new float[]{120F, 200F});
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);

        table.addCell(putBigCell("Transaction Receipt", 16F, TextAlignment.CENTER, 1, 2));

        table.addCell(putCell("receipt", 12F, TextAlignment.LEFT));
        table.addCell(putCell(transactionReceiptNumber, 12F, TextAlignment.RIGHT));
        table.addCell(putCell(String.valueOf(LocalDate.now()), 12F, TextAlignment.LEFT));
        table.addCell(putCell(String.valueOf(LocalTime.now().withNano(0)), 12F, TextAlignment.RIGHT));

        table.addCell(putCell("bank from", 12F, TextAlignment.LEFT));
        table.addCell(putCell(bankNameFrom, 12F, TextAlignment.RIGHT));
        table.addCell(putCell("bank to", 12F, TextAlignment.LEFT));
        table.addCell(putCell(bankNameTo, 12F, TextAlignment.RIGHT));
        table.addCell(putCell("account from", 12F, TextAlignment.LEFT));
        table.addCell(putCell(transaction.getAccountNumberFrom(), 12F, TextAlignment.RIGHT));
        table.addCell(putCell("account to:", 12F, TextAlignment.LEFT));
        table.addCell(putCell(transaction.getAccountNumberTo(), 12F, TextAlignment.RIGHT));
        table.addCell(putCell("amount:", 12F, TextAlignment.LEFT));
        table.addCell(putCell(new DecimalFormat("0.00").format(transaction.getAmount()) + " " + currency, 12F, TextAlignment.RIGHT));

        return table;
    }

    @SneakyThrows
    public static void accountTransactionsPdf(ClientDtoWithLog client, AccountDtoWithLog account, List<TransactionDtoWithLog> transactionList) {
        String accountTransactionsReceiptNumber = new DecimalFormat("0000000000").format(fileAccountTransactionsNumber);
        fileAccountTransactionsNumber++;

        PdfWriter writer = new PdfWriter("statement-money\\account_transactions_" + accountTransactionsReceiptNumber + ".pdf");
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        var accountNumber = account.getNumber();
        var accountCurrency = account.getCurrency();

        doc.add(accountTransactionsPdfHeadTable(client, accountNumber, accountCurrency));
        doc.add(accountTransactionsPdfInfoTable(transactionList, accountNumber));
        doc.close();
    }

    private static Table accountTransactionsPdfHeadTable(ClientDtoWithLog client, String accountNumber, String accountCurrency) {
        Table table = new Table(new float[]{100F, 200F});
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);

        table.addCell(putBigCell("All Transactions Receipt", 12F, TextAlignment.CENTER, 1, 2));
        table.addCell(putBigCell("Clever Bank", 12F, TextAlignment.CENTER, 1, 2));

        table.addCell(putCell("Client", 8F, TextAlignment.LEFT));
        table.addCell(putCell(client.getName() + " " + client.getLastName(), 8F, TextAlignment.LEFT));
        table.addCell(putCell("Account", 8F, TextAlignment.LEFT));
        table.addCell(putCell(accountNumber, 8F, TextAlignment.LEFT));
        table.addCell(putCell("Currency", 8F, TextAlignment.LEFT));
        table.addCell(putCell(accountCurrency, 8F, TextAlignment.LEFT));

        return table;
    }

    private static Table accountTransactionsPdfInfoTable(List<TransactionDtoWithLog> transactionList, String accountNumber) {
        Table table = new Table(new float[]{100F, 50F, 150F, 100F});
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);

        var dF = new DecimalFormat("0.00");

        for (TransactionDtoWithLog transaction : transactionList) {
            var fromCurrency = currencies(transaction)[0];
            var toCurrency = currencies(transaction)[1];

            if (accountNumber.equals(transaction.getAccountNumberFrom())) {
                table.addCell(putCell(transaction.getDateCreation(), 8F, TextAlignment.LEFT));
                table.addCell(putCell("withdraw to", 8F, TextAlignment.LEFT));
                table.addCell(putCell(transaction.getAccountNumberTo(), 8F, TextAlignment.LEFT));
                table.addCell(putCell("-" + dF.format(transaction.getAmount()) + " " + fromCurrency, 8F, TextAlignment.LEFT));
            } else if (accountNumber.equals(transaction.getAccountNumberTo())) {
                table.addCell(putCell(transaction.getDateCreation(), 8F, TextAlignment.LEFT));
                table.addCell(putCell("refill from", 8F, TextAlignment.LEFT));
                table.addCell(putCell(transaction.getAccountNumberFrom(), 8F, TextAlignment.LEFT));
                table.addCell(putCell(dF.format(currencyConvert(fromCurrency, toCurrency, transaction.getAmount())) + " " + toCurrency, 8F, TextAlignment.LEFT));
            }
        }
        return table;
    }

    private static String[] currencies(TransactionDtoWithLog transaction) {
        var currencies = transaction.getCurrency();
        return new String[]{currencies.substring(0, 3), currencies.substring(4)};
    }

    private static Cell putCell(String text, Float fontSize, TextAlignment textAlignment) {
        return new Cell()
                .add(new Paragraph(text))
                .setFontSize(fontSize)
                .setTextAlignment(textAlignment)
                .setBorder(Border.NO_BORDER);
    }

    private static Cell putBigCell(String text, Float fontSize, TextAlignment textAlignment, int rowspan, int colspan) {
        return new Cell(rowspan, colspan)
                .add(new Paragraph(text))
                .setFontSize(fontSize)
                .setTextAlignment(textAlignment)
                .setBorder(Border.NO_BORDER);
    }
}
