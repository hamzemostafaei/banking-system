package com.hamze.banking.system.core.api.service;

import com.hamze.banking.system.core.api.criteria.DepositCriteria;
import com.hamze.banking.system.core.api.data.DepositDTO;
import com.hamze.banking.system.data.access.entity.DepositEntity;
import com.hamze.banking.system.data.access.repository.api.IDepositRepository;

public interface IDepositService extends ICoreService<DepositDTO,
                                                      DepositEntity,
                                                      Long,
                                                      DepositCriteria,
                                                      IDepositRepository> {

    DepositDTO openDeposit(DepositDTO deposit);
}
