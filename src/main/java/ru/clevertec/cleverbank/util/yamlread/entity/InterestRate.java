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
public class InterestRate {

    private Double rate;
}
