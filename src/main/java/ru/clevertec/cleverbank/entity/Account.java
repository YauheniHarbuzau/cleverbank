package ru.clevertec.cleverbank.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.clevertec.cleverbank.entity.parent.BaseEntity;
import ru.clevertec.cleverbank.entity.parent.BaseEntityWithLog;

/**
 * Модель Счет
 * Содержит поля:
 * number - номер счета,
 * amount - сумма счета,
 * currency - вид валюты счета
 *
 * @see BaseEntity
 * @see BaseEntityWithLog
 */
@Getter
@Setter
@SuperBuilder
public class Account extends BaseEntityWithLog {

    private String number;
    private Double amount;
    private String currency;
}
