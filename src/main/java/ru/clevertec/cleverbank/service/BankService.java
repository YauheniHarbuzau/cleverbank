package ru.clevertec.cleverbank.service;

import ru.clevertec.cleverbank.dto.BankDto;
import ru.clevertec.cleverbank.dto.BankDtoWithLog;
import ru.clevertec.cleverbank.entity.Bank;
import ru.clevertec.cleverbank.service.impl.BankServiceImpl;

import java.util.List;

/**
 * Интерфейс-сервис для {@link Bank}
 *
 * @see BankDto
 * @see BankDtoWithLog
 * @see BankServiceImpl
 */
public interface BankService {

    BankDtoWithLog findById(Long id);

    List<BankDtoWithLog> findAll();

    void save(BankDto bankDto);

    void deleteById(Long id);
}
