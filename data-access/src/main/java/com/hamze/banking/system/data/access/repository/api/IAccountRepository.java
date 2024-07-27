package com.hamze.banking.system.data.access.repository.api;

import com.hamze.banking.system.data.access.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("AccountRepository")
public interface IAccountRepository extends JpaRepository<AccountEntity, String> {
}
