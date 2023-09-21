package ru.clevertec.cleverbank.util.currencyconverter;

import ru.clevertec.cleverbank.util.yamlread.YamlReadUtil;

import static ru.clevertec.cleverbank.util.yamlread.YamlReadUtil.readYaml;

/**
 * Утилитарный класс для конвертирования валют
 *
 * @see YamlReadUtil#readYaml()
 */
public class CurrencyConverterUtil {

    public static Double currencyConvert(String currencyFrom, String currencyTo, Double amount) {
        return switch (currencyFrom) {

            case "BYN" -> switch (currencyTo) {
                case "BYN" -> amount;
                case "RUB" -> amount * readYaml().getCurrencyConversionFactor().getBynToRub();
                case "USD" -> amount * readYaml().getCurrencyConversionFactor().getBynToUsd();
                case "EUR" -> amount * readYaml().getCurrencyConversionFactor().getBynToEur();
                default -> throw new IllegalStateException("Unexpected value: " + currencyTo);
            };

            case "RUB" -> switch (currencyTo) {
                case "RUB" -> amount;
                case "BYN" -> amount * readYaml().getCurrencyConversionFactor().getRubToByn();
                case "USD" -> amount * readYaml().getCurrencyConversionFactor().getRubToUsd();
                case "EUR" -> amount * readYaml().getCurrencyConversionFactor().getRubToEur();
                default -> throw new IllegalStateException("Unexpected value: " + currencyTo);
            };

            case "USD" -> switch (currencyTo) {
                case "USD" -> amount;
                case "BYN" -> amount * readYaml().getCurrencyConversionFactor().getUsdToByn();
                case "RUB" -> amount * readYaml().getCurrencyConversionFactor().getUsdToRub();
                case "EUR" -> amount * readYaml().getCurrencyConversionFactor().getUsdToEur();
                default -> throw new IllegalStateException("Unexpected value: " + currencyTo);
            };

            case "EUR" -> switch (currencyTo) {
                case "EUR" -> amount;
                case "BYN" -> amount * readYaml().getCurrencyConversionFactor().getEurToByn();
                case "RUB" -> amount * readYaml().getCurrencyConversionFactor().getEurToRub();
                case "USD" -> amount * readYaml().getCurrencyConversionFactor().getEurToUsd();
                default -> throw new IllegalStateException("Unexpected value: " + currencyTo);
            };

            default -> throw new IllegalStateException("Unexpected value: " + currencyFrom);
        };
    }
}
