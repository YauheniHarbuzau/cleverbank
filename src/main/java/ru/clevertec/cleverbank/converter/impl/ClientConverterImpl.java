package ru.clevertec.cleverbank.converter.impl;

import ru.clevertec.cleverbank.converter.ClientConverter;
import ru.clevertec.cleverbank.dto.ClientDto;
import ru.clevertec.cleverbank.dto.ClientDtoWithLog;
import ru.clevertec.cleverbank.entity.Client;
import ru.clevertec.cleverbank.exeption.EntityNotCorrectException;
import ru.clevertec.cleverbank.util.date.DateTimeUtil;

/**
 * Конвертер Пользователь-DTO-Пользователь
 *
 * @see Client
 * @see ClientDto
 * @see ClientDtoWithLog
 * @see ClientConverter
 * @see DateTimeUtil
 */
public class ClientConverterImpl implements ClientConverter {

    @Override
    public ClientDtoWithLog convert(Client client) {
        if (client == null) {
            throw new EntityNotCorrectException();
        }
        return ClientDtoWithLog.builder()
                .id(client.getId())
                .name(client.getName())
                .lastName(client.getLastName())
                .dateCreation(DateTimeUtil.getDateTime(client.getDateCreation()))
                .lastModified(DateTimeUtil.getDateTime(client.getLastModified()))
                .version(client.getVersion())
                .build();
    }

    @Override
    public Client convert(ClientDto clientDto) {
        if (clientDto == null) {
            throw new EntityNotCorrectException();
        }
        return Client.builder()
                .id(clientDto.getId())
                .name(clientDto.getName())
                .lastName(clientDto.getLastName())
                .build();
    }
}
