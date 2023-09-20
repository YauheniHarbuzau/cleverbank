package ru.clevertec.cleverbank.converter.impl;

import ru.clevertec.cleverbank.converter.AccountConverter;
import ru.clevertec.cleverbank.dto.AccountDto;
import ru.clevertec.cleverbank.dto.AccountDtoWithLog;
import ru.clevertec.cleverbank.entity.Account;
import ru.clevertec.cleverbank.exeption.EntityNotCorrectException;
import ru.clevertec.cleverbank.util.date.DateTimeUtil;

/**
 * Конвертер Счет-DTO-Счет
 *
 * @see Account
 * @see AccountDto
 * @see AccountDtoWithLog
 * @see AccountConverter
 * @see DateTimeUtil
 */
public class AccountConverterImpl implements AccountConverter {

    @Override
    public AccountDtoWithLog convert(Account account) {
        if (account == null) {
            throw new EntityNotCorrectException();
        }
        return AccountDtoWithLog.builder()
                .id(account.getId())
                .number(account.getNumber())
                .amount(account.getAmount())
                .currency(account.getCurrency())
                .dateCreation(DateTimeUtil.getDateTime(account.getDateCreation()))
                .lastModified(DateTimeUtil.getDateTime(account.getLastModified()))
                .version(account.getVersion())
                .build();
    }

    @Override
    public Account convert(AccountDto accountDto) {
        if (accountDto == null) {
            throw new EntityNotCorrectException();
        }
        return Account.builder()
                .id(accountDto.getId())
                .number(accountDto.getNumber())
                .amount(accountDto.getAmount())
                .currency(accountDto.getCurrency())
                .build();
    }
}
