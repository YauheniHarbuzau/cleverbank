package ru.clevertec.cleverbank.converter;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.clevertec.cleverbank.converter.impl.ClientConverterImpl;
import ru.clevertec.cleverbank.dto.ClientDto;
import ru.clevertec.cleverbank.entity.Client;
import ru.clevertec.cleverbank.exeption.EntityNotCorrectException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Тесты для {@link ClientConverter}, {@link ClientConverterImpl}
 */
public class ClientConverterTest {

    private final ClientConverter clientConverter = new ClientConverterImpl();

    @Nested
    class ConvertClientToClientDtoWithLogTest {
        @Test
        void checkConvertShouldReturnCorrectClientDtoWithLog() throws ParseException {
            Date date = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("08.03.2020 10:00:00");
            Client client = Client.builder()
                    .id(1L)
                    .name("NAME")
                    .lastName("LAST NAME")
                    .dateCreation(date)
                    .lastModified(date)
                    .version(1L)
                    .build();

            assertAll(
                    () -> assertEquals(1L, clientConverter.convert(client).getId()),
                    () -> assertEquals("NAME", clientConverter.convert(client).getName()),
                    () -> assertEquals("LAST NAME", clientConverter.convert(client).getLastName()),
                    () -> assertEquals("08.03.2020 10:00:00", clientConverter.convert(client).getDateCreation()),
                    () -> assertEquals("08.03.2020 10:00:00", clientConverter.convert(client).getLastModified()),
                    () -> assertEquals(1L, clientConverter.convert(client).getVersion())
            );
        }

        @Test
        void checkConvertShouldThrowEntityNotCorrectException() {
            assertThrows(EntityNotCorrectException.class, () -> clientConverter.convert((Client) null));
        }
    }

    @Nested
    class ConvertClientDtoToClientTest {
        @Test
        void checkConvertShouldReturnCorrectClient() {
            ClientDto clientDto = ClientDto.builder()
                    .id(1L)
                    .name("NAME")
                    .lastName("LAST NAME")
                    .build();

            assertAll(
                    () -> assertEquals(1L, clientConverter.convert(clientDto).getId()),
                    () -> assertEquals("NAME", clientConverter.convert(clientDto).getName()),
                    () -> assertEquals("LAST NAME", clientConverter.convert(clientDto).getLastName())
            );
        }

        @Test
        void checkConvertShouldThrowEntityNotCorrectException() {
            assertThrows(EntityNotCorrectException.class, () -> clientConverter.convert((ClientDto) null));
        }
    }
}
