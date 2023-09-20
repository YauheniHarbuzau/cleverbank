package ru.clevertec.cleverbank.util.yamlread.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * Модель для данных конфигурационного yaml-файла
 *
 * @see Properties
 */
@Getter
@Setter
public class DataSource {

    private String driver;
    private String url;
    private String username;
    private String password;
}
