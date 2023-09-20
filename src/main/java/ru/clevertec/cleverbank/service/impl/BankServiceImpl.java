package ru.clevertec.cleverbank.service.impl;

import ru.clevertec.cleverbank.converter.BankConverter;
import ru.clevertec.cleverbank.converter.impl.BankConverterImpl;
import ru.clevertec.cleverbank.dto.BankDto;
import ru.clevertec.cleverbank.dto.BankDtoWithLog;
import ru.clevertec.cleverbank.entity.Bank;
import ru.clevertec.cleverbank.exeption.BankNotFoundException;
import ru.clevertec.cleverbank.repository.BankRepository;
import ru.clevertec.cleverbank.repository.impl.BankRepositoryImpl;
import ru.clevertec.cleverbank.service.BankService;

import java.util.List;

/**
 * Сервис для {@link Bank}
 *
 * @see BankDto
 * @see BankDtoWithLog
 * @see BankConverterImpl
 * @see BankRepositoryImpl
 * @see BankService
 */
public class BankServiceImpl implements BankService {

    private final BankRepository bankRepository;
    private final BankConverter bankConverter;

    public BankServiceImpl() {
        this.bankRepository = new BankRepositoryImpl();
        this.bankConverter = new BankConverterImpl();
    }

    /**
     * Получение банка по ID
     *
     * @param id ID банка
     * @return BankDtoWithLog
     * @see BankRepositoryImpl#findById(Long)
     * @see BankConverterImpl#convert(Bank)
     */
    @Override
    public BankDtoWithLog findById(Long id) {
        return bankConverter.convert(bankRepository.findById(id).orElseThrow(BankNotFoundException::new));
    }

    /**
     * Получение всех банков
     *
     * @return List<BankDtoWithLog>
     * @see BankRepositoryImpl#findAll()
     * @see BankConverterImpl#convert(Bank)
     */
    @Override
    public List<BankDtoWithLog> findAll() {
        return bankRepository.findAll().stream().map(bankConverter::convert).toList();
    }

    /**
     * Сохранение банка
     *
     * @param bankDto DTO банка
     * @see BankRepositoryImpl#save(Bank)
     * @see BankConverterImpl#convert(BankDto)
     */
    @Override
    public void save(BankDto bankDto) {
        bankRepository.save(bankConverter.convert(bankDto));
    }

    /**
     * Удаление банка по ID
     *
     * @param id ID банка
     * @see BankRepositoryImpl#deleteById(Long)
     */
    @Override
    public void deleteById(Long id) {
        bankRepository.deleteById(id);
    }
}
