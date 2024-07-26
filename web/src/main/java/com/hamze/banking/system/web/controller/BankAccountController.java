package com.hamze.banking.system.web.controller;

import com.hamze.banking.system.core.api.criteria.DepositCriteria;
import com.hamze.banking.system.core.api.data.DepositDTO;
import com.hamze.banking.system.core.api.service.IDepositService;
import com.hamze.banking.system.web.api.data.DepositEdgeDTO;
import com.hamze.banking.system.web.api.data.GetDepositDetailsEdgeRequestDTO;
import com.hamze.banking.system.web.api.data.GetDepositDetailsEdgeResponseDTO;
import com.hamze.banking.system.web.api.mapper.IDepositDTOEdgeResponseMapper;
import com.hamze.banking.system.web.api.validation.IGetDepositDetailsEdgeRequestValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/bank-account",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class BankAccountController {

    private final IDepositService depositService;
    private final IDepositDTOEdgeResponseMapper depositDTOEdgeResponseMapper;
    private final IGetDepositDetailsEdgeRequestValidator getDepositDetailsEdgeRequestValidator;

    @PostMapping(path = "/v1/open")
    public ResponseEntity<Object> open(@RequestBody Object request) {
        return ResponseEntity.ok(request);
    }

    @PostMapping(path = "/v1/close")
    public ResponseEntity<Object> close(@RequestBody Object request) {
        return ResponseEntity.ok(request);
    }

    @PostMapping(path = "/v1/deposit")
    public ResponseEntity<Object> deposit(@RequestBody Object request) {
        return ResponseEntity.ok(request);
    }

    @PostMapping(path = "/v1/withdraw")
    public ResponseEntity<Object> withdraw(@RequestBody Object request) {
        return ResponseEntity.ok(request);
    }

    @PostMapping(path = "/v1/transfer")
    public ResponseEntity<Object> transfer(@RequestBody Object request) {
        return ResponseEntity.ok(request);
    }

    @PostMapping(path = "/v1/balance")
    public ResponseEntity<Object> balance(@RequestBody Object request) {
        return ResponseEntity.ok(request);
    }

    @PostMapping(path = "/v1/bill")
    public ResponseEntity<Object> turnover(@RequestBody Object request) {
        return ResponseEntity.ok(request);
    }

    @PostMapping(path = "/v1/detail")
    public ResponseEntity<GetDepositDetailsEdgeResponseDTO> detail(@RequestBody GetDepositDetailsEdgeRequestDTO request) {

        GetDepositDetailsEdgeResponseDTO response = new GetDepositDetailsEdgeResponseDTO();
        boolean validationResult = getDepositDetailsEdgeRequestValidator.validate(request, response);

        if(!validationResult){
            return ResponseEntity.badRequest().body(response);
        }

        DepositCriteria criteria = new DepositCriteria();
        criteria.setDepositNumberEquals(request.getDepositNumber());

        DepositDTO serviceResponse = depositService.getSingleResult(criteria);
        DepositEdgeDTO responseData = depositDTOEdgeResponseMapper.objectToEdgeObject(serviceResponse);

        response.setData(responseData);

        return ResponseEntity.ok(response);
    }
}
