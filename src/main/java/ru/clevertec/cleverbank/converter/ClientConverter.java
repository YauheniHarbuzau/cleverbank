package ru.clevertec.cleverbank.converter;

import ru.clevertec.cleverbank.converter.impl.ClientConverterImpl;
import ru.clevertec.cleverbank.dto.ClientDto;
import ru.clevertec.cleverbank.dto.ClientDtoWithLog;
import ru.clevertec.cleverbank.entity.Client;

/**
 * Интерфейс для конвертера Пользователь-DTO-Пользователь
 *
 * @see Client
 * @see ClientDto
 * @see ClientDtoWithLog
 * @see ClientConverterImpl
 */
public interface ClientConverter {

    ClientDtoWithLog convert(Client client);

    Client convert(ClientDto clientDto);
}
