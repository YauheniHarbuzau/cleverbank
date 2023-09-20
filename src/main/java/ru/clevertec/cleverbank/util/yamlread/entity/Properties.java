package ru.clevertec.cleverbank.util.yamlread.entity;

import lombok.Getter;
import lombok.Setter;
import ru.clevertec.cleverbank.util.yamlread.YamlReadUtil;

/**
 * Модель для данных конфигурационного yaml-файла
 *
 * @see DataSource
 * @see CurrencyConversionFactor
 * @see InterestRate
 * @see YamlReadUtil
 */
@Getter
@Setter
public class Properties {

    private DataSource dataSource;
    private CurrencyConversionFactor currencyConversionFactor;
    private InterestRate interestRate;
}
