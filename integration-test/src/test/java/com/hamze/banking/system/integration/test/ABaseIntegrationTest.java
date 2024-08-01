package com.hamze.banking.system.integration.test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(classes = {BankingSystemIntegrationTestApplication.class})
@ContextConfiguration(classes = {BankingSystemIntegrationTestApplication.class})
public abstract class ABaseIntegrationTest {
}
