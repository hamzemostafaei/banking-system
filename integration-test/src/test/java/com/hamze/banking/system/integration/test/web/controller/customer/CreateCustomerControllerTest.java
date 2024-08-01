package com.hamze.banking.system.integration.test.web.controller.customer;

import com.hamze.banking.system.core.api.data.CustomerDTO;
import com.hamze.banking.system.core.api.exception.CoreServiceException;
import com.hamze.banking.system.core.api.service.ICustomerService;
import com.hamze.banking.system.integration.test.ABaseIntegrationTest;
import com.hamze.banking.system.shared.data.base.dto.ErrorDTO;
import com.hamze.banking.system.shared.data.base.enumeration.ErrorCodeEnum;
import com.hamze.banking.system.shared.data.base.enumeration.ServiceStatusEnum;
import com.hamze.banking.system.shared.util.JsonSerializationUtil;
import com.hamze.banking.system.web.api.data.CreateCustomerEdgeRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class CreateCustomerControllerTest extends ABaseIntegrationTest {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private MockMvc mockMvc;

    @MockBean
    private ICustomerService customerService;

    private CreateCustomerEdgeRequestDTO validRequest;

    @BeforeEach
    public void setup() {
        validRequest = new CreateCustomerEdgeRequestDTO();
        validRequest.setCustomerNumber(12345);
        validRequest.setFirstName("حمزه");
        validRequest.setLastName("مصطفیء");
        validRequest.setNationalId("3319977441");
        validRequest.setTrackingNumber(UUID.randomUUID().toString());
    }

    @Test
    @DisplayName("Test creating a customer successfully")
    public void testCreateCustomerSuccess() throws Exception {

        this.validRequest.setTrackingNumber(UUID.randomUUID().toString());

        Map<String, Object> customerCreationRequestMap = new HashMap<>();
        customerCreationRequestMap.put("customerNumber", validRequest.getCustomerNumber());
        customerCreationRequestMap.put("firstName", validRequest.getFirstName());
        customerCreationRequestMap.put("lastName", validRequest.getLastName());
        customerCreationRequestMap.put("nationalId", validRequest.getNationalId());
        customerCreationRequestMap.put("trackingNumber", validRequest.getTrackingNumber());
        String jsonString = JsonSerializationUtil.objectToJsonString(customerCreationRequestMap);

        // Mock the service to return a valid response
        given(customerService.createCustomer(any(CustomerDTO.class)))
                .willReturn(new CustomerDTO(validRequest.getCustomerNumber(), validRequest.getFirstName(), validRequest.getLastName(), validRequest.getNationalId()));


        assert jsonString != null;
        mockMvc.perform(post("/customer/v1/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.trackingNumber").value(validRequest.getTrackingNumber()))
                .andExpect(jsonPath("$.transactionId").exists())
                .andExpect(jsonPath("$.registrationDate").exists())
                .andExpect(jsonPath("$.serviceStatus").value(ServiceStatusEnum.Successful.getStatusCode()))
                .andExpect(jsonPath("$.data.customerNumber").value(validRequest.getCustomerNumber()))
                .andExpect(jsonPath("$.data.firstName").value(validRequest.getFirstName()))
                .andExpect(jsonPath("$.data.lastName").value(validRequest.getLastName()))
                .andExpect(jsonPath("$.data.nationalId").value(validRequest.getNationalId()));
    }

    @Test
    @DisplayName("Test creating a customer with validation failure")
    public void testCreateCustomerValidationFailure() throws Exception {

        mockMvc.perform(post("/customer/v1/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"customerNumber\": 12345, \"firstName\": \"\", \"lastName\": \"\", \"nationalId\": \"\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test creating a customer with CoreServiceException")
    public void testCreateCustomerCoreServiceException() throws Exception {

        // Mock the service to throw CoreServiceException
//        doThrow(new CoreServiceException("Core Service Error", new ErrorDTO(ErrorCodeEnum.InternalServiceError, "CreateCustomer")))
        doThrow(CoreServiceException.builder()
                .message("Core Service Error")
                .error(new ErrorDTO(ErrorCodeEnum.InternalServiceError, "CreateCustomer"))
                .build())
                .when(customerService).createCustomer(any(CustomerDTO.class));

        mockMvc.perform(post("/customer/v1/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"customerNumber\": 12345, \"firstName\": \"حمزه\", \"lastName\": \"مصطفیء\", \"nationalId\": \"3319977441\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    /*@Test
    @DisplayName("Test creating a customer with unexpected exception")
    public void testCreateCustomerUnexpectedException() throws Exception {

        // Mock the service to throw a generic exception
        doThrow(new RuntimeException("Unexpected Error")).when(customerService).createCustomer(any(CustomerDTO.class));

        mockMvc.perform(post("/customer/v1/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"customerNumber\": 12345, \"firstName\": \"حمزه\", \"lastName\": \"مصطفیء\", \"nationalId\": \"3319977441\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }*/
}
