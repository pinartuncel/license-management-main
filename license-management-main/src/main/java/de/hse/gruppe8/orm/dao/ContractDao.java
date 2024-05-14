package de.hse.gruppe8.orm.dao;

import de.hse.gruppe8.orm.model.ContractEntity;
import de.hse.gruppe8.orm.model.UserEntity;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ContractDao {
    @Inject
    EntityManager entityManager;

    private static final Logger LOGGER = Logger.getLogger(ContractDao.class);


    public ContractEntity getContract(Long id) {
        return entityManager.find(ContractEntity.class, id);
    }

    public List<ContractEntity> getContracts () {
        Query q = entityManager.createQuery("select contracts from ContractEntity contracts where contracts.active = TRUE");
        List<ContractEntity> contracts = (List<ContractEntity>) q.getResultList();
        return contracts;
    }

    @Transactional
    public ContractEntity save(ContractEntity contract) {
        if (contract.getId() != null) {
            contract = entityManager.merge(contract);
        } else {
            entityManager.persist(contract);
        }
        return contract;
    }
    @Transactional
    public void delete(ContractEntity contract) {
        if (contract != null) {
            contract.setActive(false);
            save(contract);
        }
    }

        @Transactional
        public void removeAll() {
            Query del = entityManager.createQuery("DELETE FROM ContractEntity WHERE id >= 0");
            del.executeUpdate();
    }

    public List<ContractEntity> getContractsFromCompany(Long id) {
        Query q = entityManager.createQuery("select contracts from ContractEntity contracts where contracts.active = TRUE AND contracts.company.id =:id");
        q.setParameter("id", id);
        List<ContractEntity> contracts = (List<ContractEntity>) q.getResultList();
        return contracts;
    }

    public List<ContractEntity> getContractsForUser(UserEntity userEntity) {
        Query q = entityManager.createQuery("select contracts from ContractEntity contracts where contracts.active = TRUE AND contracts.user1.id =:id OR contracts.user2.id =:id");
        q.setParameter("id", userEntity.getId());
        List<ContractEntity> contracts = (List<ContractEntity>) q.getResultList();
        return contracts;
    }
}
