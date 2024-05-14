package de.hse.gruppe8.orm;

import de.hse.gruppe8.orm.dao.CompanyDao;
import de.hse.gruppe8.orm.model.CompanyEntity;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class CompanyDaoTest {

    @Inject
    CompanyDao companyDao;

    @BeforeEach
    void clearAllFromDatabase() {
        companyDao.removeAll();
    }

    @Test
    void checkAddCompany() {
        //Given
        CompanyEntity companyEntity = new CompanyEntity(null, "Test Corp.", "Development", "Peace street 9", "73728", "Esslingen", "Germany", true);

        //When
        companyDao.save(companyEntity);

        //Then
        assertEquals(1, companyDao.getCompanies().size());
    }

    @Test
    void checkUpdateCompany() {
        //Given
        CompanyEntity companyEntity = new CompanyEntity(null, "Test Corp.", "Development", "Peace street 9", "73728", "Esslingen", "Germany", true);
        companyDao.save(companyEntity);
        CompanyEntity company = companyDao.getCompanies().get(0);
        final String newName = "Other Inc.";
        //When

        company.setName(newName);
        companyDao.save(company);

        //Then
        assertEquals(1, companyDao.getCompanies().size());
        assertEquals(newName, companyDao.getCompany(company.getId()).getName());
    }

    @Test
    void checkDeleteCompany() {
        //Given
        CompanyEntity companyEntity = new CompanyEntity(null, "Test Corp.", "Development", "Peace street 9", "73728", "Esslingen", "Germany", true);
        companyDao.save(companyEntity);
        CompanyEntity company = companyDao.getCompanies().get(0);
        //When
        companyDao.delete(company);

        //Then
        assertEquals(0, companyDao.getCompanies().size());
    }


    @Test
    void checkDeleteCompanyWithNoneCompany() {
        //Given
        CompanyEntity companyEntity = new CompanyEntity(null, "Test Corp.", "Development", "Peace street 9", "73728", "Esslingen", "Germany", true);
        companyDao.save(companyEntity);
        //When
        companyDao.delete(null);

        //Then
        assertEquals(1, companyDao.getCompanies().size());
    }

}
