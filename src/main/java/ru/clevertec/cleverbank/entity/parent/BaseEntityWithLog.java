package ru.clevertec.cleverbank.entity.parent;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * Базовая сущность для моделей
 * Содержит поля:
 * dateCreation - дата создания,
 * lastModified - дата последнего редактирования,
 * version - версия
 *
 * @see BaseEntity
 */
@Getter
@Setter
@SuperBuilder
public abstract class BaseEntityWithLog extends BaseEntity {

    private Date dateCreation;
    private Date lastModified;
    private Long version;
}
