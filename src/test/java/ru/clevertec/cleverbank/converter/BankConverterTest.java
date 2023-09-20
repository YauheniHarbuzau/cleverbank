package ru.clevertec.cleverbank.converter;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.clevertec.cleverbank.converter.impl.BankConverterImpl;
import ru.clevertec.cleverbank.dto.BankDto;
import ru.clevertec.cleverbank.entity.Bank;
import ru.clevertec.cleverbank.exeption.EntityNotCorrectException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Тесты для {@link BankConverter}, {@link BankConverterImpl}
 */
public class BankConverterTest {

    private final BankConverter bankConverter = new BankConverterImpl();

    @Nested
    class ConvertBankToBankDtoWithLogTest {
        @Test
        void checkConvertShouldReturnCorrectBankDtoWithLog() throws ParseException {
            Date date = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("08.03.2020 10:00:00");
            Bank bank = Bank.builder()
                    .id(1L)
                    .name("BANK")
                    .dateCreation(date)
                    .lastModified(date)
                    .version(1L)
                    .build();

            assertAll(
                    () -> assertEquals(1L, bankConverter.convert(bank).getId()),
                    () -> assertEquals("BANK", bankConverter.convert(bank).getName()),
                    () -> assertEquals("08.03.2020 10:00:00", bankConverter.convert(bank).getDateCreation()),
                    () -> assertEquals("08.03.2020 10:00:00", bankConverter.convert(bank).getLastModified()),
                    () -> assertEquals(1L, bankConverter.convert(bank).getVersion())
            );
        }

        @Test
        void checkConvertShouldThrowEntityNotCorrectException() {
            assertThrows(EntityNotCorrectException.class, () -> bankConverter.convert((Bank) null));
        }
    }

    @Nested
    class ConvertBankDtoToBankTest {
        @Test
        void checkConvertShouldReturnCorrectBank() {
            BankDto bankDto = BankDto.builder()
                    .id(1L)
                    .name("BANK")
                    .build();

            assertAll(
                    () -> assertEquals(1L, bankConverter.convert(bankDto).getId()),
                    () -> assertEquals("BANK", bankConverter.convert(bankDto).getName())
            );
        }

        @Test
        void checkConvertShouldThrowEntityNotCorrectException() {
            assertThrows(EntityNotCorrectException.class, () -> bankConverter.convert((BankDto) null));
        }
    }
}
