package ru.clevertec.cleverbank.repository.impl;

import ru.clevertec.cleverbank.constants.Constants;
import ru.clevertec.cleverbank.entity.Account;
import ru.clevertec.cleverbank.repository.AccountRepository;
import ru.clevertec.cleverbank.repository.impl.enumentity.Reamounting;
import ru.clevertec.cleverbank.util.connection.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static ru.clevertec.cleverbank.constants.Constants.ACCOUNT_CREATURE_SQL;
import static ru.clevertec.cleverbank.constants.Constants.ACCOUNT_DELETE_BY_ID_SQL;
import static ru.clevertec.cleverbank.constants.Constants.ACCOUNT_FIND_ALL_SQL;
import static ru.clevertec.cleverbank.constants.Constants.ACCOUNT_FIND_BANK_NAME_BY_NUMBER_SQL;
import static ru.clevertec.cleverbank.constants.Constants.ACCOUNT_FIND_BY_ID_SQL;
import static ru.clevertec.cleverbank.constants.Constants.ACCOUNT_FIND_BY_NUMBER_SQL;
import static ru.clevertec.cleverbank.constants.Constants.ACCOUNT_REAMOUNTING_SQL;
import static ru.clevertec.cleverbank.constants.Constants.ACCOUNT_UPDATE_SQL;

/**
 * Репозиторий для {@link Account}
 *
 * @see AccountRepository
 * @see Constants
 * @see Reamounting
 */
public class AccountRepositoryImpl implements AccountRepository {

    private final Connection connection;

    public AccountRepositoryImpl() {
        this.connection = ConnectionUtil.open();
    }

    /**
     * Вспомогательный метод для методов findById(Long) и findByNumber(String)
     *
     * @param preparedStatement PreparedStatement
     * @return Optional<Account>
     */
    private Optional<Account> forFindMethods(PreparedStatement preparedStatement) {
        try (var resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return ofNullable(Account.builder()
                        .id(resultSet.getLong("id"))
                        .number(resultSet.getString("number"))
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
     * Получение счета по ID
     *
     * @param id ID счета
     * @return Optional<Account>
     * @see #forFindMethods(PreparedStatement)
     */
    @Override
    public Optional<Account> findById(Long id) {
        try (var preparedStatement = connection.prepareStatement(ACCOUNT_FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            return forFindMethods(preparedStatement);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Получение счета по номеру
     *
     * @param number номер счета
     * @return Optional<Account>
     * @see #forFindMethods(PreparedStatement)
     */
    @Override
    public Optional<Account> findByNumber(String number) {
        try (var preparedStatement = connection.prepareStatement(ACCOUNT_FIND_BY_NUMBER_SQL)) {
            preparedStatement.setString(1, number);
            return forFindMethods(preparedStatement);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Получение всех счетов
     *
     * @return List<Account>
     */
    @Override
    public List<Account> findAll() {
        try (var statement = connection.createStatement()) {
            List<Account> accounts = new ArrayList<>(0);

            var resultSet = statement.executeQuery(ACCOUNT_FIND_ALL_SQL);
            while (resultSet.next()) {
                accounts.add(Account.builder()
                        .id(resultSet.getLong("id"))
                        .number(resultSet.getString("number"))
                        .amount(resultSet.getDouble("amount"))
                        .currency(resultSet.getString("currency"))
                        .dateCreation(resultSet.getTimestamp("date_creation"))
                        .lastModified(resultSet.getTimestamp("last_modified"))
                        .version(resultSet.getLong("version"))
                        .build());
            }
            return accounts;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Получение наименования банка по номеру счета
     *
     * @param number номер счета
     * @return наименование банка (String)
     * @see #findByNumber(String)
     */
    @Override
    public String findBankNameByNumber(String number) {
        try (var preparedStatement = connection.prepareStatement(ACCOUNT_FIND_BANK_NAME_BY_NUMBER_SQL)) {
            preparedStatement.setString(1, number);

            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("name");
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    /**
     * Создание нового счета
     *
     * @param account счет
     */
    @Override
    public void create(Account account) {
        try (var preparedStatement = connection.prepareStatement(ACCOUNT_CREATURE_SQL)) {
            preparedStatement.setLong(1, account.getId());
            preparedStatement.setString(2, account.getNumber());
            preparedStatement.setDouble(3, account.getAmount());
            preparedStatement.setString(4, account.getCurrency());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Обновление существующего счета
     *
     * @param account счет
     * @see #findById(Long)
     */
    @Override
    public void update(Account account) {
        var accountOld = findById(account.getId());

        if (accountOld.isPresent()) {
            try (var preparedStatement = connection.prepareStatement(ACCOUNT_UPDATE_SQL)) {
                preparedStatement.setString(1, account.getNumber());
                preparedStatement.setDouble(2, account.getAmount());
                preparedStatement.setString(3, account.getCurrency());
                preparedStatement.setLong(4, accountOld.get().getVersion() + 1);
                preparedStatement.setLong(5, account.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * Сохранение счета
     *
     * @param account счет
     * @see #findById(Long)
     * @see #create(Account)
     * @see #update(Account)
     */
    @Override
    public void save(Account account) {
        if (findById(account.getId()).isPresent()) {
            update(account);
        } else {
            create(account);
        }
    }

    /**
     * Удаление счета по ID
     *
     * @param id ID счета
     * @see #findById(Long)
     */
    @Override
    public void deleteById(Long id) {
        if (findById(id).isPresent()) {
            try (var preparedStatement = connection.prepareStatement(ACCOUNT_DELETE_BY_ID_SQL)) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * Изменение суммы счета
     *
     * @param number    номер счета
     * @param amount    сумма пополнения/списания
     * @param operation операция пополнение/списание
     * @see #findByNumber(String)
     */
    @Override
    public void reamounting(String number, Double amount, Reamounting operation) {
        var account = findByNumber(number);

        if (account.isPresent()) {
            try (var preparedStatement = connection.prepareStatement(ACCOUNT_REAMOUNTING_SQL)) {
                switch (operation) {
                    case REFILL -> preparedStatement.setDouble(1, account.get().getAmount() + amount);
                    case WITHDRAW -> preparedStatement.setDouble(1, account.get().getAmount() - amount);
                    default -> preparedStatement.setDouble(1, account.get().getAmount());
                }
                preparedStatement.setLong(2, account.get().getVersion() + 1);
                preparedStatement.setLong(3, account.get().getId());
                preparedStatement.executeUpdate();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
