package ru.clevertec.cleverbank.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.clevertec.cleverbank.entity.parent.BaseEntity;
import ru.clevertec.cleverbank.entity.parent.BaseEntityWithLog;

/**
 * Модель Транзакция
 * Содержит поля:
 * accountNumberFrom - номер счета отправителя,
 * accountNumberTo - номер счета получателя,
 * amount - сумма транзакции,
 * currency - вид валюты транзакции
 *
 * @see BaseEntity
 * @see BaseEntityWithLog
 */
@Getter
@Setter
@SuperBuilder
public class Transaction extends BaseEntityWithLog {

    private String accountNumberFrom;
    private String accountNumberTo;
    private Double amount;
    private String currency;
}
