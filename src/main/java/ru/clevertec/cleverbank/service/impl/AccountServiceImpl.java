package ru.clevertec.cleverbank.service.impl;

import ru.clevertec.cleverbank.converter.AccountConverter;
import ru.clevertec.cleverbank.converter.impl.AccountConverterImpl;
import ru.clevertec.cleverbank.dto.AccountDto;
import ru.clevertec.cleverbank.dto.AccountDtoWithLog;
import ru.clevertec.cleverbank.entity.Account;
import ru.clevertec.cleverbank.exeption.AccountNotFoundException;
import ru.clevertec.cleverbank.repository.AccountRepository;
import ru.clevertec.cleverbank.repository.impl.AccountRepositoryImpl;
import ru.clevertec.cleverbank.repository.impl.enumentity.Reamounting;
import ru.clevertec.cleverbank.service.AccountService;

import java.util.List;

/**
 * Сервис для {@link Account}
 *
 * @see AccountDto
 * @see AccountDtoWithLog
 * @see AccountConverterImpl
 * @see AccountRepositoryImpl
 * @see AccountService
 * @see Reamounting
 */
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountConverter accountConverter;

    public AccountServiceImpl() {
        this.accountRepository = new AccountRepositoryImpl();
        this.accountConverter = new AccountConverterImpl();
    }

    /**
     * Получение счета по ID
     *
     * @param id ID счета
     * @return AccountDtoWithLog
     * @see AccountRepositoryImpl#findById(Long)
     * @see AccountConverterImpl#convert(Account)
     */
    @Override
    public AccountDtoWithLog findById(Long id) {
        return accountConverter.convert(accountRepository.findById(id).orElseThrow(AccountNotFoundException::new));
    }

    /**
     * Получение счета по номеру
     *
     * @param number номер счета
     * @return AccountDtoWithLog
     * @see AccountRepositoryImpl#findByNumber(String)
     * @see AccountConverterImpl#convert(Account)
     */
    @Override
    public AccountDtoWithLog findByNumber(String number) {
        return accountConverter.convert(accountRepository.findByNumber(number).orElseThrow(AccountNotFoundException::new));
    }

    /**
     * Получение всех счетов
     *
     * @return List<AccountDtoWithLog>
     * @see AccountRepositoryImpl#findAll()
     * @see AccountConverterImpl#convert(Account)
     */
    @Override
    public List<AccountDtoWithLog> findAll() {
        return accountRepository.findAll().stream().map(accountConverter::convert).toList();
    }

    /**
     * Получение наименования банка по номеру счета
     *
     * @param number номер счета
     * @return наименование банка (String)
     * @see AccountRepositoryImpl#findBankNameByNumber(String)
     */
    @Override
    public String findBankNameByNumber(String number) {
        return accountRepository.findBankNameByNumber(number);
    }

    /**
     * Сохранение счета
     *
     * @param accountDto DTO счета
     * @see AccountRepositoryImpl#save(Account)
     * @see AccountConverterImpl#convert(AccountDto)
     */
    @Override
    public void save(AccountDto accountDto) {
        accountRepository.save(accountConverter.convert(accountDto));
    }

    /**
     * Удаление счета по ID
     *
     * @param id ID счета
     * @see AccountRepositoryImpl#deleteById(Long)
     */
    @Override
    public void deleteById(Long id) {
        accountRepository.deleteById(id);
    }

    /**
     * Изменение суммы счета
     *
     * @param number    номер счета
     * @param amount    сумма пополнения/списания
     * @param operation операция пополнение/списание
     * @see AccountRepositoryImpl#reamounting(String, Double, Reamounting)
     */
    @Override
    public void reamounting(String number, Double amount, Reamounting operation) {
        accountRepository.reamounting(number, amount, operation);
    }
}
