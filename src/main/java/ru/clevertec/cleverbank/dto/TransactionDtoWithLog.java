package ru.clevertec.cleverbank.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.clevertec.cleverbank.dto.parent.BaseDtoWithLog;
import ru.clevertec.cleverbank.entity.Transaction;

/**
 * DTO для {@link Transaction}
 *
 * @see BaseDtoWithLog
 */
@Getter
@Setter
@SuperBuilder
public class TransactionDtoWithLog extends BaseDtoWithLog {

    private String accountNumberFrom;
    private String accountNumberTo;
    private Double amount;
    private String currency;
}
