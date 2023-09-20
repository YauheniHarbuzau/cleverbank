package ru.clevertec.cleverbank.repository.impl;

import ru.clevertec.cleverbank.constants.Constants;
import ru.clevertec.cleverbank.entity.Bank;
import ru.clevertec.cleverbank.repository.BankRepository;
import ru.clevertec.cleverbank.util.connection.ConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static ru.clevertec.cleverbank.constants.Constants.BANK_CREATURE_SQL;
import static ru.clevertec.cleverbank.constants.Constants.BANK_DELETE_BY_ID_SQL;
import static ru.clevertec.cleverbank.constants.Constants.BANK_FIND_ALL_SQL;
import static ru.clevertec.cleverbank.constants.Constants.BANK_FIND_BY_ID_SQL;
import static ru.clevertec.cleverbank.constants.Constants.BANK_UPDATE_SQL;

/**
 * Репозиторий для {@link Bank}
 *
 * @see BankRepository
 * @see Constants
 */
public class BankRepositoryImpl implements BankRepository {

    private final Connection connection;

    public BankRepositoryImpl() {
        this.connection = ConnectionUtil.open();
    }

    /**
     * Получение банка по ID
     *
     * @param id ID банка
     * @return Optional<Bank>
     */
    @Override
    public Optional<Bank> findById(Long id) {
        try (var preparedStatement = connection.prepareStatement(BANK_FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);

            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return ofNullable(Bank.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
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
     * Получение всех банков
     *
     * @return List<Bank>
     */
    @Override
    public List<Bank> findAll() {
        try (var statement = connection.createStatement()) {
            List<Bank> banks = new ArrayList<>(0);

            var resultSet = statement.executeQuery(BANK_FIND_ALL_SQL);
            while (resultSet.next()) {
                banks.add(Bank.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .dateCreation(resultSet.getTimestamp("date_creation"))
                        .lastModified(resultSet.getTimestamp("last_modified"))
                        .version(resultSet.getLong("version"))
                        .build());
            }
            return banks;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Создание нового банка
     *
     * @param bank банк
     */
    @Override
    public void create(Bank bank) {
        try (var preparedStatement = connection.prepareStatement(BANK_CREATURE_SQL)) {
            preparedStatement.setLong(1, bank.getId());
            preparedStatement.setString(2, bank.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Обновление существующего банка
     *
     * @param bank банк
     * @see #findById(Long)
     */
    @Override
    public void update(Bank bank) {
        var bankOld = findById(bank.getId());

        if (bankOld.isPresent()) {
            try (var preparedStatement = connection.prepareStatement(BANK_UPDATE_SQL)) {
                preparedStatement.setString(1, bank.getName());
                preparedStatement.setLong(2, bankOld.get().getVersion() + 1);
                preparedStatement.setLong(3, bank.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * Сохранение банка
     *
     * @param bank банк
     * @see #findById(Long)
     * @see #create(Bank)
     * @see #update(Bank)
     */
    @Override
    public void save(Bank bank) {
        if (findById(bank.getId()).isPresent()) {
            update(bank);
        } else {
            create(bank);
        }
    }


    /**
     * Удаление банка по ID
     *
     * @param id ID банка
     * @see #findById(Long)
     */
    @Override
    public void deleteById(Long id) {
        if (findById(id).isPresent()) {
            try (var preparedStatement = connection.prepareStatement(BANK_DELETE_BY_ID_SQL)) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
