package ru.clevertec.cleverbank.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.clevertec.cleverbank.dto.parent.BaseDtoWithLog;
import ru.clevertec.cleverbank.entity.Account;

/**
 * DTO для {@link Account}
 *
 * @see BaseDtoWithLog
 */
@Getter
@Setter
@SuperBuilder
public class AccountDtoWithLog extends BaseDtoWithLog {

    private String number;
    private Double amount;
    private String currency;
}
