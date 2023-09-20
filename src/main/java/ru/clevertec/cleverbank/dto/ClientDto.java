package ru.clevertec.cleverbank.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.clevertec.cleverbank.dto.parent.BaseDto;
import ru.clevertec.cleverbank.entity.Client;

/**
 * DTO для {@link Client}
 *
 * @see BaseDto
 */
@Getter
@Setter
@SuperBuilder
public class ClientDto extends BaseDto {

    private String name;
    private String lastName;
}
