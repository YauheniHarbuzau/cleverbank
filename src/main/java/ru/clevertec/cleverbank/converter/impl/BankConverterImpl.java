package ru.clevertec.cleverbank.converter.impl;

import ru.clevertec.cleverbank.converter.BankConverter;
import ru.clevertec.cleverbank.dto.BankDto;
import ru.clevertec.cleverbank.dto.BankDtoWithLog;
import ru.clevertec.cleverbank.entity.Bank;
import ru.clevertec.cleverbank.exeption.EntityNotCorrectException;
import ru.clevertec.cleverbank.util.date.DateTimeUtil;

/**
 * Конвертер Банк-DTO-Банк
 *
 * @see Bank
 * @see BankDto
 * @see BankDtoWithLog
 * @see BankConverter
 * @see DateTimeUtil
 */
public class BankConverterImpl implements BankConverter {

    @Override
    public BankDtoWithLog convert(Bank bank) {
        if (bank == null) {
            throw new EntityNotCorrectException();
        }
        return BankDtoWithLog.builder()
                .id(bank.getId())
                .name(bank.getName())
                .dateCreation(DateTimeUtil.getDateTime(bank.getDateCreation()))
                .lastModified(DateTimeUtil.getDateTime(bank.getLastModified()))
                .version(bank.getVersion())
                .build();
    }

    @Override
    public Bank convert(BankDto bankDto) {
        if (bankDto == null) {
            throw new EntityNotCorrectException();
        }
        return Bank.builder()
                .id(bankDto.getId())
                .name(bankDto.getName())
                .build();
    }
}
