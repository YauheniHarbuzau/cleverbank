package ru.clevertec.cleverbank.service;

import ru.clevertec.cleverbank.dto.AccountDto;
import ru.clevertec.cleverbank.dto.AccountDtoWithLog;
import ru.clevertec.cleverbank.entity.Account;
import ru.clevertec.cleverbank.repository.impl.enumentity.Reamounting;
import ru.clevertec.cleverbank.service.impl.AccountServiceImpl;

import java.util.List;

/**
 * Интерфейс-сервис для {@link Account}
 *
 * @see AccountDto
 * @see AccountDtoWithLog
 * @see Reamounting
 * @see AccountServiceImpl
 */
public interface AccountService {

    AccountDtoWithLog findById(Long id);

    AccountDtoWithLog findByNumber(String number);

    List<AccountDtoWithLog> findAll();

    String findBankNameByNumber(String number);

    void save(AccountDto accountDto);

    void deleteById(Long id);

    void reamounting(String number, Double amount, Reamounting operation);
}
