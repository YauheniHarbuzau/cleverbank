package ru.clevertec.cleverbank.service;

import ru.clevertec.cleverbank.dto.ClientDto;
import ru.clevertec.cleverbank.dto.ClientDtoWithLog;
import ru.clevertec.cleverbank.entity.Client;
import ru.clevertec.cleverbank.service.impl.ClientServiceImpl;

import java.util.List;

/**
 * Интерфейс-сервис для {@link Client}
 *
 * @see ClientDto
 * @see ClientDtoWithLog
 * @see ClientServiceImpl
 */
public interface ClientService {

    ClientDtoWithLog findById(Long id);

    ClientDtoWithLog findByAccountNumber(String accountNumber);

    List<ClientDtoWithLog> findAll();

    void save(ClientDto clientDto);

    void deleteById(Long id);
}
