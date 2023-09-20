package ru.clevertec.cleverbank.entity.parent;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Базовая сущность для моделей, предоставляющая поле ID
 */
@Getter
@Setter
@SuperBuilder
public abstract class BaseEntity {

    private Long id;
}
