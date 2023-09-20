package ru.clevertec.cleverbank.controller;

import com.google.gson.Gson;
import ru.clevertec.cleverbank.dto.AccountDto;
import ru.clevertec.cleverbank.dto.AccountDtoWithLog;
import ru.clevertec.cleverbank.entity.Account;
import ru.clevertec.cleverbank.service.AccountService;
import ru.clevertec.cleverbank.service.impl.AccountServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Контроллер для {@link Account}, {@link AccountDto}, {@link AccountDtoWithLog}
 *
 * @see AccountService
 * @see AccountServiceImpl
 */
@WebServlet(name = "accountController", value = "/accounts")
public class AccountController extends HttpServlet {

    private AccountService accountService;
    private Gson gson;

    @Override
    public void init() {
        this.accountService = new AccountServiceImpl();
        this.gson = new Gson();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        var printWriter = response.getWriter();
        var id = request.getParameter("id");

        if (id != null) {
            printWriter.print(gson.toJson(accountService.findById(Long.valueOf(id))));
        } else {
            printWriter.print(gson.toJson(accountService.findAll()));
        }
        printWriter.flush();
    }


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        var printWriter = response.getWriter();
        var account = AccountDto.builder()
                .id(Long.valueOf(request.getParameter("id")))
                .number(request.getParameter("number"))
                .amount(Double.valueOf(request.getParameter("amount")))
                .currency(request.getParameter("currency"))
                .build();

        accountService.save(account);
        printWriter.print(gson.toJson(account));
        printWriter.flush();
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var printWriter = response.getWriter();
        var id = request.getParameter("id");

        accountService.deleteById(Long.valueOf(id));
        printWriter.print("Account deleted by ID " + id);
        printWriter.flush();
    }
}
