package com.hamze.banking.system.data.access.repository.api;

import com.hamze.banking.system.data.access.entity.DepositEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("DepositRepository")
public interface IDepositRepository extends JpaRepository<DepositEntity, Long> {
}
