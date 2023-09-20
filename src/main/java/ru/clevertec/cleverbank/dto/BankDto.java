package ru.clevertec.cleverbank.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.clevertec.cleverbank.dto.parent.BaseDto;
import ru.clevertec.cleverbank.entity.Bank;

/**
 * DTO для {@link Bank}
 *
 * @see BaseDto
 */
@Getter
@Setter
@SuperBuilder
public class BankDto extends BaseDto {

    private String name;
}
