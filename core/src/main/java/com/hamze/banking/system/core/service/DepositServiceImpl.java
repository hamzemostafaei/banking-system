package com.hamze.banking.system.core.service;

import com.hamze.banking.system.core.api.criteria.DepositCriteria;
import com.hamze.banking.system.core.api.data.DepositDTO;
import com.hamze.banking.system.core.api.service.IDepositService;
import com.hamze.banking.system.data.access.entity.DepositEntity;
import com.hamze.banking.system.data.access.repository.api.IDepositRepository;
import org.springframework.stereotype.Service;

@Service("DepositService")
public class DepositServiceImpl extends ABaseCoreService<DepositDTO,
                                                         DepositEntity,
                                                         Long,
                                                         DepositCriteria,
                                                         IDepositRepository>
        implements IDepositService {
}
