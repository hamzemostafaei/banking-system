package com.hamze.banking.system.data.access.repository.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableRetry
@EnableTransactionManagement
@EntityScan({"com.hamze.banking.system.data.access.entity"})
@Configuration("RepositoryConfiguration")
@EnableJpaRepositories({"com.hamze.banking.system.data.access.repository"})
public class RepositoryConfiguration {

    //TODO
    /*@Bean
    public Scheduler jdbcScheduler() {
        return Schedulers.fromExecutor(Executors.newVirtualThreadPerTaskExecutor());
    }*/
}
