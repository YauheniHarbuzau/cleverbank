package ru.clevertec.cleverbank.repository.impl;

import ru.clevertec.cleverbank.constants.Constants;
import ru.clevertec.cleverbank.entity.Client;
import ru.clevertec.cleverbank.repository.ClientRepository;
import ru.clevertec.cleverbank.util.connection.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static ru.clevertec.cleverbank.constants.Constants.CLIENT_CREATURE_SQL;
import static ru.clevertec.cleverbank.constants.Constants.CLIENT_DELETE_BY_ID_SQL;
import static ru.clevertec.cleverbank.constants.Constants.CLIENT_FIND_ALL_SQL;
import static ru.clevertec.cleverbank.constants.Constants.CLIENT_FIND_BY_ACCOUNT_NUMBER_SQL;
import static ru.clevertec.cleverbank.constants.Constants.CLIENT_FIND_BY_ID_SQL;
import static ru.clevertec.cleverbank.constants.Constants.CLIENT_UPDATE_SQL;

/**
 * Репозиторий для {@link Client}
 *
 * @see ClientRepository
 * @see Constants
 */
public class ClientRepositoryImpl implements ClientRepository {

    private final Connection connection;

    public ClientRepositoryImpl() {
        this.connection = ConnectionUtil.open();
    }

    /**
     * Вспомогательный метод для методов findById(Long) и findByAccountNumber(String)
     *
     * @param preparedStatement PreparedStatement
     * @return Optional<Client>
     */
    private Optional<Client> forFindMethods(PreparedStatement preparedStatement) {
        try (var resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return ofNullable(Client.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .lastName(resultSet.getString("last_name"))
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
     * Получение пользователя по ID
     *
     * @param id ID пользователя
     * @return Optional<Client>
     * @see #forFindMethods(PreparedStatement)
     */
    @Override
    public Optional<Client> findById(Long id) {
        try (var preparedStatement = connection.prepareStatement(CLIENT_FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            return forFindMethods(preparedStatement);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Получение пользователя по номеру счета
     *
     * @param accountNumber номер счета
     * @return Optional<Client>
     * @see #forFindMethods(PreparedStatement)
     */
    @Override
    public Optional<Client> findByAccountNumber(String accountNumber) {
        try (var preparedStatement = connection.prepareStatement(CLIENT_FIND_BY_ACCOUNT_NUMBER_SQL)) {
            preparedStatement.setString(1, accountNumber);
            return forFindMethods(preparedStatement);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Получение всех пользователей
     *
     * @return List<Client>
     */
    @Override
    public List<Client> findAll() {
        try (var statement = connection.createStatement()) {
            List<Client> clients = new ArrayList<>(0);

            var resultSet = statement.executeQuery(CLIENT_FIND_ALL_SQL);
            while (resultSet.next()) {
                clients.add(Client.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .lastName(resultSet.getString("last_name"))
                        .dateCreation(resultSet.getTimestamp("date_creation"))
                        .lastModified(resultSet.getTimestamp("last_modified"))
                        .version(resultSet.getLong("version"))
                        .build());
            }
            return clients;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Создание нового пользователя
     *
     * @param client пользователь
     */
    @Override
    public void create(Client client) {
        try (var preparedStatement = connection.prepareStatement(CLIENT_CREATURE_SQL)) {
            preparedStatement.setLong(1, client.getId());
            preparedStatement.setString(2, client.getName());
            preparedStatement.setString(3, client.getLastName());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Обновление существующего пользователя
     *
     * @param client пользователь
     * @see #findById(Long)
     */
    @Override
    public void update(Client client) {
        var clientOld = findById(client.getId());

        if (clientOld.isPresent()) {
            try (var preparedStatement = connection.prepareStatement(CLIENT_UPDATE_SQL)) {
                preparedStatement.setString(1, client.getName());
                preparedStatement.setString(2, client.getLastName());
                preparedStatement.setLong(3, clientOld.get().getVersion() + 1);
                preparedStatement.setLong(4, client.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * Сохранение пользователя
     *
     * @param client пользователь
     * @see #findById(Long)
     * @see #create(Client)
     * @see #update(Client)
     */
    @Override
    public void save(Client client) {
        if (findById(client.getId()).isPresent()) {
            update(client);
        } else {
            create(client);
        }
    }

    /**
     * Удаление пользователя по ID
     *
     * @param id ID пользователя
     * @see #findById(Long)
     */
    @Override
    public void deleteById(Long id) {
        if (findById(id).isPresent()) {
            try (var preparedStatement = connection.prepareStatement(CLIENT_DELETE_BY_ID_SQL)) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
