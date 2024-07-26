package com.hamze.banking.system.data.access.repository.api;

import com.hamze.banking.system.data.access.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("CustomerRepository")
public interface ICustomerRepository extends JpaRepository<CustomerEntity, Integer> {
}
