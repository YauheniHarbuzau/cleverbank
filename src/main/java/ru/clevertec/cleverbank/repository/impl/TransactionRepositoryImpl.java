package ru.clevertec.cleverbank.repository.impl;

import lombok.SneakyThrows;
import ru.clevertec.cleverbank.constants.Constants;
import ru.clevertec.cleverbank.entity.Account;
import ru.clevertec.cleverbank.entity.Transaction;
import ru.clevertec.cleverbank.repository.AccountRepository;
import ru.clevertec.cleverbank.repository.TransactionRepository;
import ru.clevertec.cleverbank.repository.impl.enumentity.Reamounting;
import ru.clevertec.cleverbank.util.currencyconverter.CurrencyConverterUtil;
import ru.clevertec.cleverbank.util.connection.ConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static ru.clevertec.cleverbank.constants.Constants.TRANSACTION_CREATURE_SQL;
import static ru.clevertec.cleverbank.constants.Constants.TRANSACTION_DELETE_BY_ID_SQL;
import static ru.clevertec.cleverbank.constants.Constants.TRANSACTION_FIND_ALL_SQL;
import static ru.clevertec.cleverbank.constants.Constants.TRANSACTION_FIND_BY_ID_SQL;
import static ru.clevertec.cleverbank.constants.Constants.TRANSACTION_UPDATE_SQL;
import static ru.clevertec.cleverbank.repository.impl.enumentity.Reamounting.REFILL;
import static ru.clevertec.cleverbank.repository.impl.enumentity.Reamounting.WITHDRAW;
import static ru.clevertec.cleverbank.util.currencyconverter.CurrencyConverterUtil.currencyConvert;

/**
 * Репозиторий для {@link Transaction}
 *
 * @see TransactionRepository
 * @see AccountRepository
 * @see AccountRepositoryImpl
 * @see Constants
 * @see CurrencyConverterUtil
 */
public class TransactionRepositoryImpl implements TransactionRepository {

    private final Connection connection;
    private final Lock lock;
    private final AccountRepository accountRepository;

    public TransactionRepositoryImpl() {
        this.connection = ConnectionUtil.open();
        this.lock = new ReentrantLock();
        this.accountRepository = new AccountRepositoryImpl();
    }

    /**
     * Получение транзакции по ID
     *
     * @param id ID транзакции
     * @return Optional<Transaction>
     */
    @Override
    public Optional<Transaction> findById(Long id) {
        try (var preparedStatement = connection.prepareStatement(TRANSACTION_FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);

            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return ofNullable(Transaction.builder()
                        .id(resultSet.getLong("id"))
                        .accountNumberFrom(resultSet.getString("account_number_from"))
                        .accountNumberTo(resultSet.getString("account_number_to"))
                        .amount(resultSet.getDouble("amount"))
                        .currency(resultSet.getString("currency"))
                        .dateCreation(resultSet.getTimestamp("date_creation"))
                        .lastModified(resultSet.getTimestamp("last_modified"))
                        .version(resultSet.getLong("version"))
                        .build());
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return empty();
    }

    /**
     * Получение всех транзакций
     *
     * @return List<Transaction>
     */
    @Override
    public List<Transaction> findAll() {
        try (var statement = connection.createStatement()) {
            List<Transaction> transactions = new ArrayList<>(0);

            var resultSet = statement.executeQuery(TRANSACTION_FIND_ALL_SQL);
            while (resultSet.next()) {
                transactions.add(Transaction.builder()
                        .id(resultSet.getLong("id"))
                        .accountNumberFrom(resultSet.getString("account_number_from"))
                        .accountNumberTo(resultSet.getString("account_number_to"))
                        .amount(resultSet.getDouble("amount"))
                        .currency(resultSet.getString("currency"))
                        .dateCreation(resultSet.getTimestamp("date_creation"))
                        .lastModified(resultSet.getTimestamp("last_modified"))
                        .version(resultSet.getLong("version"))
                        .build());
            }
            return transactions;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Получение всех транзакций по номеру счета
     *
     * @param accountNumber номер счета
     * @return List<Transaction>
     * @see #findAll()
     */
    @Override
    public List<Transaction> findAllByAccountNumber(String accountNumber) {
        return findAll().stream()
                .filter(tr -> accountNumber.equals(tr.getAccountNumberFrom()) || accountNumber.equals(tr.getAccountNumberTo()))
                .toList();
    }

    /**
     * Создание новой транзакции
     *
     * @param transaction транзакция
     * @see AccountRepositoryImpl#findByNumber(String)
     */
    @Override
    public void create(Transaction transaction) {
        var accountFrom = accountRepository.findByNumber(transaction.getAccountNumberFrom()).get();
        var accountTo = accountRepository.findByNumber(transaction.getAccountNumberTo()).get();

        try (var preparedStatement = connection.prepareStatement(TRANSACTION_CREATURE_SQL)) {
            preparedStatement.setLong(1, transaction.getId());
            preparedStatement.setString(2, transaction.getAccountNumberFrom());
            preparedStatement.setString(3, transaction.getAccountNumberTo());
            preparedStatement.setDouble(4, transaction.getAmount());
            preparedStatement.setString(5, accountFrom.getCurrency() + "-" + accountTo.getCurrency());
            preparedStatement.setLong(6, transaction.getId());
            preparedStatement.setLong(7, accountFrom.getId());
            preparedStatement.setLong(8, accountTo.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Обновление существующей транзакции
     *
     * @param transaction транзакция
     * @see #findById(Long)
     * @see AccountRepositoryImpl#findByNumber(String)
     */
    @Override
    public void update(Transaction transaction) {
        var transactionOld = findById(transaction.getId());

        if (transactionOld.isPresent()) {
            var accountFrom = accountRepository.findByNumber(transaction.getAccountNumberFrom()).get();
            var accountTo = accountRepository.findByNumber(transaction.getAccountNumberTo()).get();
            try (var preparedStatement = connection.prepareStatement(TRANSACTION_UPDATE_SQL)) {
                preparedStatement.setString(1, transaction.getAccountNumberFrom());
                preparedStatement.setString(2, transaction.getAccountNumberTo());
                preparedStatement.setDouble(3, transaction.getAmount());
                preparedStatement.setString(4, accountFrom.getCurrency() + "-" + accountTo.getCurrency());
                preparedStatement.setLong(5, transactionOld.get().getVersion() + 1);
                preparedStatement.setLong(6, transaction.getId());
                preparedStatement.setLong(7, accountFrom.getId());
                preparedStatement.setLong(8, accountTo.getId());
                preparedStatement.setLong(9, transaction.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * Сохранение транзакции
     *
     * @param transaction транзакция
     * @see AccountRepositoryImpl#findByNumber(String)
     * @see #findById(Long)
     */
    @Override
    public void save(Transaction transaction) {
        var accountFrom = accountRepository.findByNumber(transaction.getAccountNumberFrom());
        var accountTo = accountRepository.findByNumber(transaction.getAccountNumberTo());

        if (accountFrom.isPresent() && accountTo.isPresent()) {
            if (findById(transaction.getId()).isPresent()) {
                update(transaction);
            } else {
                create(transaction);
            }
        }
    }

    /**
     * Удаление транзакции по ID
     *
     * @param id ID транзакции
     * @see #findById(Long)
     */
    @Override
    public void deleteById(Long id) {
        if (findById(id).isPresent()) {
            try (var preparedStatement = connection.prepareStatement(TRANSACTION_DELETE_BY_ID_SQL)) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * Вспомогательный метод для денежных переводов с одного счета на другой с учетом вида валюты
     *
     * @param accountFrom счет отправителя
     * @param accountTo   счет получателя
     * @param amount      сумма транзакции
     * @see AccountRepositoryImpl#reamounting(String, Double, Reamounting)
     * @see CurrencyConverterUtil#currencyConvert(String, String, Double)
     */
    private void transaction(Account accountFrom, Account accountTo, Double amount) {
        var accountNumberFrom = accountFrom.getNumber();
        var accountNumberTo = accountTo.getNumber();
        var currencyFrom = accountFrom.getCurrency();
        var currencyTo = accountTo.getCurrency();

        lock.lock();
        try {
            accountRepository.reamounting(accountNumberFrom, amount, WITHDRAW);
            accountRepository.reamounting(accountNumberTo, currencyConvert(currencyFrom, currencyTo, amount), REFILL);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Вспомогательный метод для генерирования нового ID для транзакции с учетом уже имеющихся
     *
     * @return ID для транзакции
     * @see #findAll()
     */
    private Long createId() {
        return findAll().stream().map(Transaction::getId).max(Long::compare).map(id -> id + 1L).orElse(1L);
    }

    /**
     * Выполнение транзакции
     *
     * @param accountNumberFrom номер счета отправителя
     * @param accountNumberTo   номер счета получателя
     * @param amount            сумма транзакции
     * @see AccountRepositoryImpl#findByNumber(String)
     * @see #save
     * @see #createId()
     * @see #transaction(Account, Account, Double)
     */
    @SneakyThrows
    @Override
    public void transactionExecution(String accountNumberFrom, String accountNumberTo, Double amount) {
        var accountFrom = accountRepository.findByNumber(accountNumberFrom);
        var accountTo = accountRepository.findByNumber(accountNumberTo);

        if (accountFrom.isPresent() && accountTo.isPresent()) {
            try {
                connection.setAutoCommit(false);

                save(Transaction.builder()
                        .id(createId())
                        .accountNumberFrom(accountNumberFrom)
                        .accountNumberTo(accountNumberTo)
                        .amount(amount)
                        .currency(accountFrom.get().getCurrency() + "-" + accountTo.get().getCurrency())
                        .build());

                transaction(accountFrom.get(), accountTo.get(), amount);
                connection.commit();
            } catch (SQLException ex) {
                connection.rollback();
                throw new RuntimeException(ex);
            } finally {
                connection.setAutoCommit(true);
            }
        }
    }
}
