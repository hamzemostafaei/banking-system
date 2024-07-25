package com.hamze.banking.system.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/bank-account",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class BankAccountController {

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
    public ResponseEntity<Object> detail(@RequestBody Object request) {
        return ResponseEntity.ok(request);
    }
}
