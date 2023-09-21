package ru.clevertec.cleverbank.listener;

import ru.clevertec.cleverbank.entity.Account;
import ru.clevertec.cleverbank.repository.AccountRepository;
import ru.clevertec.cleverbank.repository.impl.AccountRepositoryImpl;
import ru.clevertec.cleverbank.util.yamlread.YamlReadUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static ru.clevertec.cleverbank.util.yamlread.YamlReadUtil.readYaml;

/**
 * Класс для выполнения начислений по процентной ставке
 *
 * @see AccountRepository
 * @see AccountRepositoryImpl
 * @see YamlReadUtil
 */
@WebListener
public class ApplyInterest implements ServletContextListener {

    private final AccountRepository accountRepository;
    private final Lock lock;
    private final ExecutorService executor;
    private final ScheduledExecutorService scheduler;

    public ApplyInterest() {
        this.accountRepository = new AccountRepositoryImpl();
        this.lock = new ReentrantLock();
        this.executor = Executors.newFixedThreadPool(4);
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        var currentDate = LocalDate.now();
        var currentTime = LocalTime.now();

        Runnable task = () -> {
            if (
                    currentDate.getDayOfMonth() == currentDate.lengthOfMonth() &&
                    currentTime.isAfter(LocalTime.of(23, 59, 29)) &&
                    currentTime.isBefore(LocalTime.of(23, 59, 59))
            ) {
                lock.lock();
                try {
                    executor.submit(() -> accountRepository.findAll().forEach(account -> accountRepository.update(Account.builder()
                            .id(account.getId())
                            .number(account.getNumber())
                            .amount(account.getAmount() * (1 + readYaml().getInterestRate().getRate() / 100))
                            .currency(account.getCurrency())
                            .build())));
                } finally {
                    lock.unlock();
                }
            }
        };
        scheduler.scheduleAtFixedRate(task, 0, 30, TimeUnit.SECONDS);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        if (executor != null && scheduler != null) {
            executor.shutdown();
            scheduler.shutdown();
        }
    }
}
