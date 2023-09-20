package ru.clevertec.cleverbank.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.clevertec.cleverbank.dto.parent.BaseDto;
import ru.clevertec.cleverbank.entity.Account;

/**
 * DTO для {@link Account}
 *
 * @see BaseDto
 */
@Getter
@Setter
@SuperBuilder
public class AccountDto extends BaseDto {

    private String number;
    private Double amount;
    private String currency;
}
