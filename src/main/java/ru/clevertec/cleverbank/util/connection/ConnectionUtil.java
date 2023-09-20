package ru.clevertec.cleverbank.util.connection;

import ru.clevertec.cleverbank.util.yamlread.YamlReadUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static ru.clevertec.cleverbank.util.yamlread.YamlReadUtil.readYaml;

/**
 * Утилитарный класс для выполнения и закрытия подключения к базе данных
 *
 * @see YamlReadUtil
 */
public class ConnectionUtil {

    public static Connection open() {
        try {
            Class.forName(readYaml().getDataSource().getDriver());
            return DriverManager.getConnection(
                    readYaml().getDataSource().getUrl(),
                    readYaml().getDataSource().getUsername(),
                    readYaml().getDataSource().getPassword()
            );
        } catch (ClassNotFoundException | SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
