package ru.clevertec.cleverbank.converter;

import ru.clevertec.cleverbank.converter.impl.BankConverterImpl;
import ru.clevertec.cleverbank.dto.BankDto;
import ru.clevertec.cleverbank.dto.BankDtoWithLog;
import ru.clevertec.cleverbank.entity.Bank;

/**
 * Интерфейс для конвертера Банк-DTO-Банк
 *
 * @see Bank
 * @see BankDto
 * @see BankDtoWithLog
 * @see BankConverterImpl
 */
public interface BankConverter {

    BankDtoWithLog convert(Bank bank);

    Bank convert(BankDto bankDto);
}
