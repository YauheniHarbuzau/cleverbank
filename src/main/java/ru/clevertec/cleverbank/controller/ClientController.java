package ru.clevertec.cleverbank.controller;

import com.google.gson.Gson;
import ru.clevertec.cleverbank.dto.ClientDto;
import ru.clevertec.cleverbank.dto.ClientDtoWithLog;
import ru.clevertec.cleverbank.entity.Client;
import ru.clevertec.cleverbank.service.ClientService;
import ru.clevertec.cleverbank.service.impl.ClientServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Контроллер для {@link Client}, {@link ClientDto}, {@link ClientDtoWithLog}
 *
 * @see ClientService
 * @see ClientServiceImpl
 */
@WebServlet(name = "clientController", value = "/clients")
public class ClientController extends HttpServlet {

    private ClientService clientService;
    private Gson gson;

    @Override
    public void init() {
        this.clientService = new ClientServiceImpl();
        this.gson = new Gson();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        var printWriter = response.getWriter();
        var id = request.getParameter("id");

        if (id != null) {
            printWriter.print(gson.toJson(clientService.findById(Long.valueOf(id))));
        } else {
            printWriter.print(gson.toJson(clientService.findAll()));
        }
        printWriter.flush();
    }


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        var printWriter = response.getWriter();
        var client = ClientDto.builder()
                .id(Long.valueOf(request.getParameter("id")))
                .name(request.getParameter("name"))
                .lastName(request.getParameter("lastName"))
                .build();

        clientService.save(client);
        printWriter.print(gson.toJson(client));
        printWriter.flush();
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var printWriter = response.getWriter();
        var id = request.getParameter("id");

        clientService.deleteById(Long.valueOf(id));
        printWriter.print("Client deleted by ID " + id);
        printWriter.flush();
    }
}
