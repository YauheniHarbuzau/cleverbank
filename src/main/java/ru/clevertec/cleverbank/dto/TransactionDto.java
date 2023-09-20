package ru.clevertec.cleverbank.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.clevertec.cleverbank.dto.parent.BaseDto;
import ru.clevertec.cleverbank.entity.Transaction;

/**
 * DTO для {@link Transaction}
 *
 * @see BaseDto
 */
@Getter
@Setter
@SuperBuilder
public class TransactionDto extends BaseDto {

    private String accountNumberFrom;
    private String accountNumberTo;
    private Double amount;
}
