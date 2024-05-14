package de.hse.gruppe8.orm.dao;

import de.hse.gruppe8.orm.model.CompanyEntity;
import de.hse.gruppe8.util.mapper.CompanyMapper;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class CompanyDao {
    @Inject
    EntityManager entityManager;

    @Inject
    CompanyMapper companyMapper;

    private static final Logger LOGGER = Logger.getLogger(CompanyDao.class);


    public CompanyEntity getCompany (Long id) {
        return entityManager.find(CompanyEntity.class, id);
    }

    public List<CompanyEntity> getCompanies () {
        Query q = entityManager.createQuery("select companies from CompanyEntity companies where companies.active = TRUE");
        List<CompanyEntity> companies = (List<CompanyEntity>) q.getResultList();
        return companies;
    }

    @Transactional
    public CompanyEntity save(CompanyEntity company) {
        if (company.getId() != null) {
            company = entityManager.merge(company);
        } else {
            entityManager.persist(company);
        }
        return company;
    }
    @Transactional
    public void delete(CompanyEntity company) {
        if (company != null) {
            company.setActive(false);
            save(company);
        }
    }

    @Transactional
    public void removeAll() {
        Query del = entityManager.createQuery("DELETE FROM CompanyEntity WHERE id >= 0");
        del.executeUpdate();
    }
}
