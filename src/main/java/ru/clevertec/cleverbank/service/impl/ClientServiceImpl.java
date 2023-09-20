package ru.clevertec.cleverbank.service.impl;

import ru.clevertec.cleverbank.converter.ClientConverter;
import ru.clevertec.cleverbank.converter.impl.ClientConverterImpl;
import ru.clevertec.cleverbank.dto.ClientDto;
import ru.clevertec.cleverbank.dto.ClientDtoWithLog;
import ru.clevertec.cleverbank.entity.Client;
import ru.clevertec.cleverbank.exeption.ClientNotFoundException;
import ru.clevertec.cleverbank.repository.ClientRepository;
import ru.clevertec.cleverbank.repository.impl.ClientRepositoryImpl;
import ru.clevertec.cleverbank.service.ClientService;

import java.util.List;

/**
 * Сервис для {@link Client}
 *
 * @see ClientDto
 * @see ClientDtoWithLog
 * @see ClientConverterImpl
 * @see ClientRepositoryImpl
 * @see ClientService
 */
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientConverter clientConverter;

    public ClientServiceImpl() {
        this.clientRepository = new ClientRepositoryImpl();
        this.clientConverter = new ClientConverterImpl();
    }

    /**
     * Получение пользователя по ID
     *
     * @param id ID пользователя
     * @return ClientDtoWithLog
     * @see ClientRepositoryImpl#findById(Long)
     * @see ClientConverterImpl#convert(Client)
     */
    @Override
    public ClientDtoWithLog findById(Long id) {
        return clientConverter.convert(clientRepository.findById(id).orElseThrow(ClientNotFoundException::new));
    }

    /**
     * Получение пользователя по номеру счета
     *
     * @param accountNumber номер счета
     * @return ClientDtoWithLog
     * @see ClientRepositoryImpl#findByAccountNumber(String)
     * @see ClientConverterImpl#convert(Client)
     */
    @Override
    public ClientDtoWithLog findByAccountNumber(String accountNumber) {
        return clientConverter.convert(clientRepository.findByAccountNumber(accountNumber).orElseThrow(ClientNotFoundException::new));
    }

    /**
     * Получение всех пользователей
     *
     * @return List<ClientDtoWithLog>
     * @see ClientRepositoryImpl#findAll()
     * @see ClientConverterImpl#convert(Client)
     */
    @Override
    public List<ClientDtoWithLog> findAll() {
        return clientRepository.findAll().stream().map(clientConverter::convert).toList();
    }

    /**
     * Сохранение пользователя
     *
     * @param clientDto DTO пользователя
     * @see ClientRepositoryImpl#save(Client)
     * @see ClientConverterImpl#convert(ClientDto)
     */
    @Override
    public void save(ClientDto clientDto) {
        clientRepository.save(clientConverter.convert(clientDto));
    }

    /**
     * Удаление пользователя по ID
     *
     * @param id ID пользователя
     * @see ClientRepositoryImpl#deleteById(Long)
     */
    @Override
    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }
}
