package ru.clevertec.cleverbank.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.clevertec.cleverbank.entity.parent.BaseEntity;
import ru.clevertec.cleverbank.entity.parent.BaseEntityWithLog;

/**
 * Модель Банк
 * Содержит поля:
 * name - наименование банка
 *
 * @see BaseEntity
 * @see BaseEntityWithLog
 */
@Getter
@Setter
@SuperBuilder
public class Bank extends BaseEntityWithLog {

    private String name;
}
