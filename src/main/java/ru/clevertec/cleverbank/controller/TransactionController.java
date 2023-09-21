package ru.clevertec.cleverbank.controller;

import com.google.gson.Gson;
import ru.clevertec.cleverbank.dto.TransactionDto;
import ru.clevertec.cleverbank.dto.TransactionDtoWithLog;
import ru.clevertec.cleverbank.entity.Transaction;
import ru.clevertec.cleverbank.service.TransactionService;
import ru.clevertec.cleverbank.service.impl.TransactionServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Контроллер для {@link Transaction}, {@link TransactionDto}, {@link TransactionDtoWithLog}
 *
 * @see TransactionService
 * @see TransactionServiceImpl
 */
@WebServlet(name = "transactionController", value = "/transactions")
public class TransactionController extends HttpServlet {

    private TransactionService transactionService;
    private Gson gson;

    @Override
    public void init() {
        this.transactionService = new TransactionServiceImpl();
        this.gson = new Gson();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        var printWriter = response.getWriter();
        var id = request.getParameter("id");

        if (id != null) {
            printWriter.print(gson.toJson(transactionService.findById(Long.valueOf(id))));
        } else {
            printWriter.print(gson.toJson(transactionService.findAll()));
        }
        printWriter.flush();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        var printWriter = response.getWriter();
        var id = request.getParameter("id");
        var accountNumberFrom = request.getParameter("accountNumberFrom");
        var accountNumberTo = request.getParameter("accountNumberTo");
        var amount = request.getParameter("amount");

        TransactionDto transaction;
        if (id != null) {
            transaction = TransactionDto.builder()
                    .id(Long.valueOf(id))
                    .accountNumberFrom(accountNumberFrom)
                    .accountNumberTo(accountNumberTo)
                    .amount(Double.valueOf(amount))
                    .build();
            transactionService.save(transaction);
            printWriter.print(gson.toJson(transaction));
        } else {
            transaction = TransactionDto.builder()
                    .accountNumberFrom(accountNumberFrom)
                    .accountNumberTo(accountNumberTo)
                    .amount(Double.valueOf(amount))
                    .build();
            transactionService.transactionExecution(transaction);
            printWriter.print("Transaction completed");
        }
        printWriter.flush();
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var printWriter = response.getWriter();
        var id = request.getParameter("id");

        transactionService.deleteById(Long.valueOf(id));
        printWriter.print("Transaction deleted by ID " + id);
        printWriter.flush();
    }
}
