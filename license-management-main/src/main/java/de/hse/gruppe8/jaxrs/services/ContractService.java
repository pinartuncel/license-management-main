package de.hse.gruppe8.jaxrs.services;

import de.hse.gruppe8.jaxrs.model.Contract;
import de.hse.gruppe8.jaxrs.model.User;
import de.hse.gruppe8.orm.dao.ContractDao;
import de.hse.gruppe8.orm.model.ContractEntity;
import de.hse.gruppe8.util.mapper.ContractMapper;
import de.hse.gruppe8.util.mapper.UserMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ContractService {

    @Inject
    ContractDao contractDao;

    @Inject
    ContractMapper contractMapper;

    @Inject
    UserMapper userMapper;

    public List<Contract> getContracts(User currentUser) {
        List<Contract> contracts = new ArrayList<>();
        if (currentUser.getIsAdmin()) {
            List<ContractEntity> contractEntities = contractDao.getContracts();
            for (ContractEntity contractEntity : contractEntities) {
                contracts.add(contractMapper.toContract(contractEntity));
            }
        } else {
            List<ContractEntity> contractEntities = contractDao.getContractsForUser(userMapper.toUserEntity(currentUser));
            for (ContractEntity contractEntity : contractEntities) {
                contracts.add(contractMapper.toContract(contractEntity));
            }
        }
        return contracts;
    }


    public Contract getContract(User currentUser, Long id) {
        Contract contract = null;
        List<ContractEntity> contractEntities = contractDao.getContractsForUser(userMapper.toUserEntity(currentUser));
        if (currentUser.getIsAdmin() || contractEntities.stream().filter(contractEntity -> id.equals(contractEntity.getId())).findAny().orElse(null) != null) {
            contract = contractMapper.toContract(contractDao.getContract(id));
        }
        return contract;
    }


    public Contract createContract(User currentUser, Contract contract) {
        if (currentUser.getIsAdmin()) {
            ContractEntity contractEntity = contractDao.save(contractMapper.toContractEntity(contract));
            return contractMapper.toContract(contractEntity);
        }
        return null;
    }

    public boolean deleteContract(User currentUser, Long id) {
        if (currentUser.getIsAdmin()) {
            contractDao.delete(contractDao.getContract(id));
            return true;
        }
        return false;
    }

    public Contract updateContract(User currentUser, Contract contract) {
        if (currentUser.getIsAdmin()) {
            ContractEntity contractEntity = contractDao.getContract(contract.getId());
            contractEntity = contractMapper.mapContractEntityWithContract(contractEntity, contract);
            contractEntity = contractDao.save(contractEntity);
            return contractMapper.toContract(contractEntity);
        } else {
            return null;
        }
    }
}
