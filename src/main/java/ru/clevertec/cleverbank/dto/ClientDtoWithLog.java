package ru.clevertec.cleverbank.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.clevertec.cleverbank.dto.parent.BaseDtoWithLog;
import ru.clevertec.cleverbank.entity.Client;

/**
 * DTO для {@link Client}
 *
 * @see BaseDtoWithLog
 */
@Getter
@Setter
@SuperBuilder
public class ClientDtoWithLog extends BaseDtoWithLog {

    private String name;
    private String lastName;
}
