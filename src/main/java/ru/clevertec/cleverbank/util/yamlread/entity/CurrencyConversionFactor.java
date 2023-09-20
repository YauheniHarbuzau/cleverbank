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
public class CurrencyConversionFactor {

    private Double bynToRub;
    private Double bynToUsd;
    private Double bynToEur;

    private Double rubToByn;
    private Double rubToUsd;
    private Double rubToEur;

    private Double usdToByn;
    private Double usdToRub;
    private Double usdToEur;

    private Double eurToByn;
    private Double eurToRub;
    private Double eurToUsd;
}
