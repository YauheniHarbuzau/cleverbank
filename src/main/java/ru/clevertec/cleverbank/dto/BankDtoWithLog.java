package ru.clevertec.cleverbank.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.clevertec.cleverbank.dto.parent.BaseDtoWithLog;
import ru.clevertec.cleverbank.entity.Bank;

/**
 * DTO для {@link Bank}
 *
 * @see BaseDtoWithLog
 */
@Getter
@Setter
@SuperBuilder
public class BankDtoWithLog extends BaseDtoWithLog {

    private String name;
}
