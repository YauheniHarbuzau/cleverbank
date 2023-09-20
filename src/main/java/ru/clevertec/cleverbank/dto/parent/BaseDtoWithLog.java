package ru.clevertec.cleverbank.dto.parent;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Базовая сущность для DTO
 * Содержит поля:
 * dateCreation - дата создания,
 * lastModified - дата последнего редактирования,
 * version - версия
 *
 * @see BaseDto
 */
@Getter
@Setter
@SuperBuilder
public abstract class BaseDtoWithLog extends BaseDto {

    private String dateCreation;
    private String lastModified;
    private Long version;
}
