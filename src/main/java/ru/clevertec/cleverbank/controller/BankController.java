package ru.clevertec.cleverbank.controller;

import com.google.gson.Gson;
import ru.clevertec.cleverbank.dto.BankDto;
import ru.clevertec.cleverbank.dto.BankDtoWithLog;
import ru.clevertec.cleverbank.entity.Bank;
import ru.clevertec.cleverbank.service.BankService;
import ru.clevertec.cleverbank.service.impl.BankServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Контроллер для {@link Bank}, {@link BankDto}, {@link BankDtoWithLog}
 *
 * @see BankService
 * @see BankServiceImpl
 */
@WebServlet(name = "bankController", value = "/banks")
public class BankController extends HttpServlet {

    private BankService bankService;
    private Gson gson;

    @Override
    public void init() {
        this.bankService = new BankServiceImpl();
        this.gson = new Gson();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        var printWriter = response.getWriter();
        var id = request.getParameter("id");

        if (id != null) {
            printWriter.print(gson.toJson(bankService.findById(Long.valueOf(id))));
        } else {
            printWriter.print(gson.toJson(bankService.findAll()));
        }
        printWriter.flush();
    }


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        var printWriter = response.getWriter();
        var bank = BankDto.builder()
                .id(Long.valueOf(request.getParameter("id")))
                .name(request.getParameter("name"))
                .build();

        bankService.save(bank);
        printWriter.print(gson.toJson(bank));
        printWriter.flush();
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var printWriter = response.getWriter();
        var id = request.getParameter("id");

        bankService.deleteById(Long.valueOf(id));
        printWriter.print("Bank deleted by ID " + id);
        printWriter.flush();
    }
}
