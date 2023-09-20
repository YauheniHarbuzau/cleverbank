package ru.clevertec.cleverbank.converter;

import ru.clevertec.cleverbank.converter.impl.AccountConverterImpl;
import ru.clevertec.cleverbank.dto.AccountDto;
import ru.clevertec.cleverbank.dto.AccountDtoWithLog;
import ru.clevertec.cleverbank.entity.Account;

/**
 * Интерфейс для конвертера Счет-DTO-Счет
 *
 * @see Account
 * @see AccountDto
 * @see AccountDtoWithLog
 * @see AccountConverterImpl
 */
public interface AccountConverter {

    AccountDtoWithLog convert(Account account);

    Account convert(AccountDto accountDto);
}
