package ru.clevertec.cleverbank.converter;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.clevertec.cleverbank.converter.impl.AccountConverterImpl;
import ru.clevertec.cleverbank.dto.AccountDto;
import ru.clevertec.cleverbank.entity.Account;
import ru.clevertec.cleverbank.exeption.EntityNotCorrectException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Тесты для {@link AccountConverter}, {@link AccountConverterImpl}
 */
public class AccountConverterTest {

    private final AccountConverter accountConverter = new AccountConverterImpl();

    @Nested
    class ConvertAccountToAccountDtoWithLogTest {
        @Test
        void checkConvertShouldReturnCorrectAccountDtoWithLog() throws ParseException {
            Date date = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("08.03.2020 10:00:00");
            Account account = Account.builder()
                    .id(1L)
                    .number("1111 1111 1111 1111")
                    .amount(2000.0)
                    .currency("USD")
                    .dateCreation(date)
                    .lastModified(date)
                    .version(1L)
                    .build();

            assertAll(
                    () -> assertEquals(1L, accountConverter.convert(account).getId()),
                    () -> assertEquals("1111 1111 1111 1111", accountConverter.convert(account).getNumber()),
                    () -> assertEquals(2000.0, accountConverter.convert(account).getAmount()),
                    () -> assertEquals("USD", accountConverter.convert(account).getCurrency()),
                    () -> assertEquals("08.03.2020 10:00:00", accountConverter.convert(account).getDateCreation()),
                    () -> assertEquals("08.03.2020 10:00:00", accountConverter.convert(account).getLastModified()),
                    () -> assertEquals(1L, accountConverter.convert(account).getVersion())
            );
        }

        @Test
        void checkConvertShouldThrowEntityNotCorrectException() {
            assertThrows(EntityNotCorrectException.class, () -> accountConverter.convert((Account) null));
        }
    }

    @Nested
    class ConvertAccountDtoToAccountTest {
        @Test
        void checkConvertShouldReturnCorrectAccount() {
            AccountDto accountDto = AccountDto.builder()
                    .id(1L)
                    .number("1111 1111 1111 1111")
                    .amount(2000.0)
                    .currency("USD")
                    .build();

            assertAll(
                    () -> assertEquals(1L, accountConverter.convert(accountDto).getId()),
                    () -> assertEquals("1111 1111 1111 1111", accountConverter.convert(accountDto).getNumber()),
                    () -> assertEquals(2000.0, accountConverter.convert(accountDto).getAmount()),
                    () -> assertEquals("USD", accountConverter.convert(accountDto).getCurrency())
            );
        }

        @Test
        void checkConvertShouldThrowEntityNotCorrectException() {
            assertThrows(EntityNotCorrectException.class, () -> accountConverter.convert((AccountDto) null));
        }
    }
}
