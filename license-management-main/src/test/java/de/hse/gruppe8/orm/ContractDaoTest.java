package de.hse.gruppe8.orm;

import de.hse.gruppe8.orm.dao.CompanyDao;
import de.hse.gruppe8.orm.dao.ContractDao;
import de.hse.gruppe8.orm.model.CompanyEntity;
import de.hse.gruppe8.orm.model.ContractEntity;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class ContractDaoTest {

    @Inject
    ContractDao contractDao;
    @Inject
    CompanyDao companyDao;

    @BeforeEach
    @AfterEach
    void InitDatabase() {
        contractDao.removeAll();
        companyDao.removeAll();
    }


    @Test
    void checkAddContract() throws ParseException {
        //Given
        CompanyEntity companyEntity = new CompanyEntity(null, "Test Corp.", "Development", "Peace street 9", "73728", "Esslingen", "Germany", true);
        companyDao.save(companyEntity);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String begin = "2021-10-20";
        Date dateBegin = formatter.parse(begin);
        String end = "2021-12-30";
        Date dateEnd = formatter.parse(end);

        ContractEntity contractEntity = new ContractEntity(null, dateBegin, dateEnd, "01", "123456", 1, 2, 3, null, null, null, true, companyEntity, null, null);

        //When
        contractDao.save(contractEntity);

        //Then
        assertEquals(1, contractDao.getContracts().size());
    }

    @Test
    void checkUpdateContract() throws ParseException {
        //Given
        CompanyEntity companyEntity = new CompanyEntity(null, "Test Corp.", "Development", "Peace street 9", "73728", "Esslingen", "Germany", true);
        companyDao.save(companyEntity);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String begin = "2021-10-20";
        Date dateBegin = formatter.parse(begin);
        String end = "2021-12-30";
        Date dateEnd = formatter.parse(end);

        ContractEntity contractEntity = new ContractEntity(null, dateBegin, dateEnd, "01", "123456", 1, 2, 3, null, null, null, true, companyEntity, null, null);
        contractDao.save(contractEntity);

        ContractEntity contract = contractDao.getContracts().get(0);
        final String newVersion = "02";
        //When

        contract.setVersion(newVersion);
        contractDao.save(contract);

        //Then
        assertEquals(1, contractDao.getContracts().size());
        assertEquals(newVersion, contractDao.getContract(contract.getId()).getVersion());
    }

    @Test
    void checkDeleteContract() throws ParseException {
        //Given
        CompanyEntity companyEntity = new CompanyEntity(null, "Test Corp.", "Development", "Peace street 9", "73728", "Esslingen", "Germany", true);
        companyDao.save(companyEntity);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String begin = "2021-10-20";
        Date dateBegin = formatter.parse(begin);
        String end = "2021-12-30";
        Date dateEnd = formatter.parse(end);

        ContractEntity contractEntity = new ContractEntity(null, dateBegin, dateEnd, "01", "123456", 1, 2, 3, null, null, null, true, companyEntity, null, null);
        contractDao.save(contractEntity);

        ContractEntity contract = contractDao.getContracts().get(0);
        //When
        contractDao.delete(contract);

        //Then
        assertEquals(0, contractDao.getContracts().size());
    }


    @Test
    void checkDeleteContractWithNoneContract() throws ParseException {
        //Given
        CompanyEntity companyEntity = new CompanyEntity(null, "Test Corp.", "Development", "Peace street 9", "73728", "Esslingen", "Germany", true);
        companyDao.save(companyEntity);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String begin = "2021-10-20";
        Date dateBegin = formatter.parse(begin);
        String end = "2021-12-30";
        Date dateEnd = formatter.parse(end);

        ContractEntity contractEntity = new ContractEntity(null, dateBegin, dateEnd, "01", "123456", 1, 2, 3, null, null, null, true, companyEntity, null, null);
        contractDao.save(contractEntity);

        //When
        contractDao.delete(null);

        //Then
        assertEquals(1, contractDao.getContracts().size());
    }

}
