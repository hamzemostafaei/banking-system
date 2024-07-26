package com.hamze.banking.system.data.access.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CUSTOMER")
public class CustomerEntity extends ABaseEntity {

    @Id
    @Basic
    @Column(name = "CUSTOMER_NUMBER", length = 8, unique = true)
    private Integer customerNumber;

    @Basic
    @Column(name = "FIRST_NAME", length = 128)
    private String firstName;

    @Basic
    @Column(name = "LAST_NAME", length = 128)
    private String lastName;

    @Basic
    @Column(name = "NATIONAL_ID", length = 11)
    private String nationalId;
}
