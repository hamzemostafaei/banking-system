package com.hamze.banking.system.data.access.repository.api;

import com.hamze.banking.system.data.access.entity.AccountTurnoverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("AccountTurnoverRepository")
public interface IAccountTurnoverRepository extends JpaRepository<AccountTurnoverEntity, Long> {
}
