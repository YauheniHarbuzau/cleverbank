package ru.clevertec.cleverbank.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.clevertec.cleverbank.entity.parent.BaseEntity;
import ru.clevertec.cleverbank.entity.parent.BaseEntityWithLog;

/**
 * Модель Пользователь
 * Содержит поля:
 * name - имя пользователя,
 * lastName - фамилия пользователя
 *
 * @see BaseEntity
 * @see BaseEntityWithLog
 */
@Getter
@Setter
@SuperBuilder
public class Client extends BaseEntityWithLog {

    private String name;
    private String lastName;
}
