package com.hamze.banking.system.integration.test.web.controller;

import com.hamze.banking.system.core.api.criteria.CustomerCriteria;
import com.hamze.banking.system.core.api.data.CustomerDTO;
import com.hamze.banking.system.core.api.service.ICustomerService;
import com.hamze.banking.system.integration.test.ABaseIntegrationTest;
import com.hamze.banking.system.shared.data.base.enumeration.ServiceStatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class CustomerControllerTest extends ABaseIntegrationTest {

    private static final Integer CUSTOMER_NUMBER = 12345;

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private MockMvc mockMvc;

    @MockBean
    private ICustomerService customerService;

    @BeforeEach
    public void setup() {
        setupMockCustomerData(CUSTOMER_NUMBER);
    }

    @Test
    @DisplayName("Getting customer information")
    public void testGetCustomer() throws Exception {

        mockMvc.perform(get("/customer/v1/{customerNumber}", CUSTOMER_NUMBER)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.trackingNumber").exists())
                .andExpect(jsonPath("$.transactionId").exists())
                .andExpect(jsonPath("$.registrationDate").exists())
                .andExpect(jsonPath("$.serviceStatus").value(ServiceStatusEnum.Successful.getStatusCode()))
                .andExpect(jsonPath("$.data.customerNumber").value(CUSTOMER_NUMBER))
                .andExpect(jsonPath("$.data.firstName").value("حمزه"))
                .andExpect(jsonPath("$.data.lastName").value("مصطفیء"))
                .andExpect(jsonPath("$.data.nationalId").value("3319977441"));
    }

    private void setupMockCustomerData(Integer customerNumber) {
        CustomerCriteria customerCriteria = new CustomerCriteria();
        customerCriteria.setCustomerNumberEquals(customerNumber);

        CustomerDTO mockCustomer = createMockCustomer(customerNumber);

        given(customerService.getSingleResult(customerCriteria)).willReturn(mockCustomer);
    }

    private CustomerDTO createMockCustomer(Integer customerNumber) {
        CustomerDTO customer = new CustomerDTO();
        customer.setCustomerNumber(customerNumber);
        customer.setFirstName("حمزه");
        customer.setLastName("مصطفیء");
        customer.setNationalId("3319977441");
        return customer;
    }
}
