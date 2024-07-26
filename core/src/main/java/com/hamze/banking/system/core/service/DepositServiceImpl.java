package com.hamze.banking.system.core.service;

import com.hamze.banking.system.core.api.criteria.DepositCriteria;
import com.hamze.banking.system.core.api.data.DepositDTO;
import com.hamze.banking.system.core.api.exception.CoreServiceException;
import com.hamze.banking.system.core.api.service.IDepositService;
import com.hamze.banking.system.data.access.entity.DepositEntity;
import com.hamze.banking.system.data.access.repository.api.IDepositRepository;
import com.hamze.banking.system.shared.data.base.dto.ErrorDTO;
import com.hamze.banking.system.shared.data.base.enumeration.ErrorCodeEnum;
import com.hamze.banking.system.shared.util.SnowFlakeUniqueIDGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service("DepositService")
public class DepositServiceImpl extends ABaseCoreService<DepositDTO,
                                                         DepositEntity,
                                                         Long,
                                                         DepositCriteria,
                                                         IDepositRepository>
        implements IDepositService {

    @Value("${com.hamze.banking.system.node-id}")
    private Long nodeId;

    @Override
    public DepositDTO openDeposit(DepositDTO deposit){

        DepositCriteria criteria = new DepositCriteria();
        criteria.setDepositNumberEquals(deposit.getDepositNumber());
        DepositDTO fetchedDeposit = getSingleResult(criteria);

        if (fetchedDeposit != null) {
            String message = String.format("Deposit [%s] already exists", fetchedDeposit.getDepositNumber());
            throw CoreServiceException.builder()
                    .message(message)
                    .error(new ErrorDTO(ErrorCodeEnum.DuplicateData, message, "deposit"))
                    .build();
        }

        long depositId = SnowFlakeUniqueIDGenerator.generateNextId(nodeId);
        deposit.setDepositId(depositId);
        deposit.setOpenDate(new Date());
        deposit.setCloseDate(null);
        deposit.setStatus(0);
        deposit.setOpenAmount(BigDecimal.ZERO);

        return save(deposit);
    }
}
