package ru.clevertec.cleverbank.dto.parent;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * Базовая сущность для DTO, предоставляющая поле ID
 */
@Getter
@Setter
@SuperBuilder
public abstract class BaseDto implements Serializable {

    private Long id;
}
