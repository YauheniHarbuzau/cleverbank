package ru.clevertec.cleverbank.converter;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.clevertec.cleverbank.converter.impl.TransactionConverterImpl;
import ru.clevertec.cleverbank.dto.TransactionDto;
import ru.clevertec.cleverbank.entity.Transaction;
import ru.clevertec.cleverbank.exeption.EntityNotCorrectException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Тесты для {@link TransactionConverter}, {@link TransactionConverterImpl}
 */
public class TransactionConverterTest {

    private final TransactionConverter transactionConverter = new TransactionConverterImpl();

    @Nested
    class ConvertTransactionToTransactionDtoWithLogTest {
        @Test
        void checkConvertShouldReturnCorrectTransactionDtoWithLog() throws ParseException {
            Date date = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("08.03.2020 10:00:00");
            Transaction transaction = Transaction.builder()
                    .id(1L)
                    .accountNumberFrom("1111 1111 1111 1111")
                    .accountNumberTo("2222 2222 2222 2222")
                    .amount(2000.0)
                    .currency("USD-BYN")
                    .dateCreation(date)
                    .lastModified(date)
                    .version(1L)
                    .build();

            assertAll(
                    () -> assertEquals(1L, transactionConverter.convert(transaction).getId()),
                    () -> assertEquals("1111 1111 1111 1111", transactionConverter.convert(transaction).getAccountNumberFrom()),
                    () -> assertEquals("2222 2222 2222 2222", transactionConverter.convert(transaction).getAccountNumberTo()),
                    () -> assertEquals(2000.0, transactionConverter.convert(transaction).getAmount()),
                    () -> assertEquals("USD-BYN", transactionConverter.convert(transaction).getCurrency()),
                    () -> assertEquals("08.03.2020 10:00:00", transactionConverter.convert(transaction).getDateCreation()),
                    () -> assertEquals("08.03.2020 10:00:00", transactionConverter.convert(transaction).getLastModified()),
                    () -> assertEquals(1L, transactionConverter.convert(transaction).getVersion())
            );
        }

        @Test
        void checkConvertShouldThrowEntityNotCorrectException() {
            assertThrows(EntityNotCorrectException.class, () -> transactionConverter.convert((Transaction) null));
        }
    }

    @Nested
    class ConvertTransactionDtoToTransactionTest {
        @Test
        void checkConvertShouldReturnCorrectTransaction() {
            TransactionDto transactionDto = TransactionDto.builder()
                    .id(1L)
                    .accountNumberFrom("1111 1111 1111 1111")
                    .accountNumberTo("2222 2222 2222 2222")
                    .amount(2000.0)
                    .build();

            assertAll(
                    () -> assertEquals(1L, transactionConverter.convert(transactionDto).getId()),
                    () -> assertEquals("1111 1111 1111 1111", transactionConverter.convert(transactionDto).getAccountNumberFrom()),
                    () -> assertEquals("2222 2222 2222 2222", transactionConverter.convert(transactionDto).getAccountNumberTo()),
                    () -> assertEquals(2000.0, transactionConverter.convert(transactionDto).getAmount())
            );
        }

        @Test
        void checkConvertShouldThrowEntityNotCorrectException() {
            assertThrows(EntityNotCorrectException.class, () -> transactionConverter.convert((TransactionDto) null));
        }
    }
}
