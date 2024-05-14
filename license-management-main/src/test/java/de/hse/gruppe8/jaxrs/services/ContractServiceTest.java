package de.hse.gruppe8.jaxrs.services;


import de.hse.gruppe8.jaxrs.model.Company;
import de.hse.gruppe8.jaxrs.model.Contract;
import de.hse.gruppe8.jaxrs.model.User;
import de.hse.gruppe8.orm.dao.CompanyDao;
import de.hse.gruppe8.orm.dao.ContractDao;
import de.hse.gruppe8.orm.dao.UserDao;
import de.hse.gruppe8.orm.model.CompanyEntity;
import de.hse.gruppe8.orm.model.ContractEntity;
import de.hse.gruppe8.orm.model.UserEntity;
import de.hse.gruppe8.util.mapper.CompanyMapper;
import de.hse.gruppe8.util.mapper.ContractMapper;
import de.hse.gruppe8.util.mapper.UserMapper;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class ContractServiceTest {

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @Inject
    ContractService contractService;

    @Inject
    UserDao userDao;

    @Inject
    ContractDao contractDao;

    @Inject
    CompanyDao companyDao;

    @Inject
    CompanyMapper companyMapper;

    @Inject
    UserMapper userMapper;

    @Inject
    ContractMapper contractMapper;

    @BeforeEach
    @AfterEach
    void InitDatabase() {
        contractDao.removeAll();
        userDao.removeAll();
        companyDao.removeAll();
    }

    // checkGetContractsForUser -> find in class ContractToUserDaoTest

    @Test
    void checkGetContractsAsAdmin() throws ParseException {
        //Given
        CompanyEntity companyEntity1 = companyDao.save(new CompanyEntity(null, "name 1", "department 1", "street 1", "73732", "esslingen", "Germany", true));
        CompanyEntity companyEntity2 = companyDao.save(new CompanyEntity(null, "name 2", "department 2", "street 2", "73732", "esslingen", "Germany", true));

        UserEntity userEntity_1 = userDao.save(new UserEntity(null, "username_01", "password", true, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));
        UserEntity userEntity_2 = userDao.save(new UserEntity(null, "username_02", "password", false, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));
        UserEntity userEntity_3 = userDao.save(new UserEntity(null, "username_03", "password", false, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));


        ContractEntity contractEntity_c1_1 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2021-12-30"), "01", "123456", 1, 2, 3, null, null, null, true, companyEntity1, userEntity_1, null));
        ContractEntity contractEntity_c1_2 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2021-12-30"), "02", "654321", 1, 2, 3, null, null, null, true, companyEntity1, userEntity_1, userEntity_2));
        ContractEntity contractEntity_c2_1 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2021-12-30"), "02", "654321", 1, 2, 3, null, null, null, true, companyEntity2, userEntity_2, null));
        ContractEntity contractEntity_c2_2 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2021-12-30"), "02", "654321", 1, 2, 3, null, null, null, true, companyEntity2, null, null));

        User user = userMapper.toUser(userEntity_1);

        //When
        List<Contract> contracts = contractService.getContracts(user);

        //Then
        assertEquals(4, contracts.size());
    }

    @Test
    void checkGetContractsAsUserFromCompany_1() throws ParseException {
        //Given
        CompanyEntity companyEntity1 = companyDao.save(new CompanyEntity(null, "name 1", "department 1", "street 1", "73732", "esslingen", "Germany", true));
        CompanyEntity companyEntity2 = companyDao.save(new CompanyEntity(null, "name 2", "department 2", "street 2", "73732", "esslingen", "Germany", true));

        UserEntity userEntity_1 = userDao.save(new UserEntity(null, "username_01", "password", false, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));
        UserEntity userEntity_2 = userDao.save(new UserEntity(null, "username_02", "password", false, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));
        UserEntity userEntity_3 = userDao.save(new UserEntity(null, "username_03", "password", false, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));


        ContractEntity contractEntity_c1_1 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2021-12-30"), "01", "123456", 1, 2, 3, null, null, null, true, companyEntity1, userEntity_1, null));
        ContractEntity contractEntity_c1_2 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2021-12-30"), "02", "654321", 1, 2, 3, null, null, null, true, companyEntity1, userEntity_1, userEntity_2));
        ContractEntity contractEntity_c2_1 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2021-12-30"), "02", "654321", 1, 2, 3, null, null, null, true, companyEntity2, userEntity_2, null));
        ContractEntity contractEntity_c2_2 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2021-12-30"), "02", "654321", 1, 2, 3, null, null, null, true, companyEntity2, null, null));

        User user = userMapper.toUser(userEntity_1);

        //When
        List<Contract> contracts = contractService.getContracts(user);

        //Then
        assertEquals(2, contracts.size());

    }

    @Test
    void checkGetContractsAsUserFromCompany_2() throws ParseException {
        //Given
        CompanyEntity companyEntity1 = companyDao.save(new CompanyEntity(null, "name 1", "department 1", "street 1", "73732", "esslingen", "Germany", true));
        CompanyEntity companyEntity2 = companyDao.save(new CompanyEntity(null, "name 2", "department 2", "street 2", "73732", "esslingen", "Germany", true));

        UserEntity userEntity_1 = userDao.save(new UserEntity(null, "username_01", "password", false, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));
        UserEntity userEntity_2 = userDao.save(new UserEntity(null, "username_02", "password", false, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));
        UserEntity userEntity_3 = userDao.save(new UserEntity(null, "username_03", "password", false, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));


        ContractEntity contractEntity_c1_1 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2021-12-30"), "01", "123456", 1, 2, 3, null, null, null, true, companyEntity1, userEntity_1, null));
        ContractEntity contractEntity_c1_2 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2021-12-30"), "02", "654321", 1, 2, 3, null, null, null, true, companyEntity1, userEntity_1, userEntity_2));
        ContractEntity contractEntity_c2_1 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2021-12-30"), "02", "654321", 1, 2, 3, null, null, null, true, companyEntity2, userEntity_2, null));
        ContractEntity contractEntity_c2_2 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2021-12-30"), "02", "654321", 1, 2, 3, null, null, null, true, companyEntity2, null, null));

        User user = userMapper.toUser(userEntity_2);

        //When
        List<Contract> contracts = contractService.getContracts(user);

        //Then
        assertEquals(2, contracts.size());
    }

    @Test
    void checkGetContractsAsUserFromCompany_3() throws ParseException {
        //Given
        CompanyEntity companyEntity1 = companyDao.save(new CompanyEntity(null, "name 1", "department 1", "street 1", "73732", "esslingen", "Germany", true));
        CompanyEntity companyEntity2 = companyDao.save(new CompanyEntity(null, "name 2", "department 2", "street 2", "73732", "esslingen", "Germany", true));

        UserEntity userEntity_1 = userDao.save(new UserEntity(null, "username_01", "password", false, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));
        UserEntity userEntity_2 = userDao.save(new UserEntity(null, "username_02", "password", false, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));
        UserEntity userEntity_3 = userDao.save(new UserEntity(null, "username_03", "password", false, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));


        ContractEntity contractEntity_c1_1 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2021-12-30"), "01", "123456", 1, 2, 3, null, null, null, true, companyEntity1, userEntity_1, null));
        ContractEntity contractEntity_c1_2 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2021-12-30"), "02", "654321", 1, 2, 3, null, null, null, true, companyEntity1, userEntity_1, userEntity_2));
        ContractEntity contractEntity_c2_1 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2021-12-30"), "02", "654321", 1, 2, 3, null, null, null, true, companyEntity2, userEntity_2, null));
        ContractEntity contractEntity_c2_2 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2021-12-30"), "02", "654321", 1, 2, 3, null, null, null, true, companyEntity2, null, null));
        ContractEntity contractEntity_c2_3 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2021-12-30"), "02", "654321", 1, 2, 3, null, null, null, true, companyEntity2, userEntity_3, null));

        User user = userMapper.toUser(userEntity_3);

        //When
        List<Contract> contracts = contractService.getContracts(user);

        //Then
        assertEquals(1, contracts.size());
    }

    @Test
    void checkGetContractsAsUserFromCompany_4() throws ParseException {
        //Given
        CompanyEntity companyEntity1 = companyDao.save(new CompanyEntity(null, "name 1", "department 1", "street 1", "73732", "esslingen", "Germany", true));
        CompanyEntity companyEntity2 = companyDao.save(new CompanyEntity(null, "name 2", "department 2", "street 2", "73732", "esslingen", "Germany", true));

        UserEntity userEntity_1 = userDao.save(new UserEntity(null, "username_01", "password", false, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));
        UserEntity userEntity_2 = userDao.save(new UserEntity(null, "username_02", "password", false, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));
        UserEntity userEntity_3 = userDao.save(new UserEntity(null, "username_03", "password", false, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));


        ContractEntity contractEntity_c1_1 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2021-12-30"), "01", "123456", 1, 2, 3, null, null, null, true, companyEntity1, userEntity_1, null));
        ContractEntity contractEntity_c1_2 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2021-12-30"), "02", "654321", 1, 2, 3, null, null, null, true, companyEntity1, userEntity_1, userEntity_2));
        ContractEntity contractEntity_c2_1 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2021-12-30"), "02", "654321", 1, 2, 3, null, null, null, true, companyEntity2, userEntity_2, null));
        ContractEntity contractEntity_c2_2 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2021-12-30"), "02", "654321", 1, 2, 3, null, null, null, true, companyEntity2, null, null));

        User user = userMapper.toUser(userEntity_3);

        //When
        List<Contract> contracts = contractService.getContracts(user);

        //Then
        assertEquals(0, contracts.size());
    }

    @Test
    void checkGetContractAsUserInCompany() throws ParseException {
        //Given
        CompanyEntity companyEntity1 = companyDao.save(new CompanyEntity(null, "name 1", "department 1", "street 1", "73732", "esslingen", "Germany", true));
        CompanyEntity companyEntity2 = companyDao.save(new CompanyEntity(null, "name 2", "department 2", "street 2", "73732", "esslingen", "Germany", true));

        UserEntity userEntity_1 = userDao.save(new UserEntity(null, "username_01", "password", false, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));
        UserEntity userEntity_2 = userDao.save(new UserEntity(null, "username_02", "password", false, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));
        UserEntity userEntity_3 = userDao.save(new UserEntity(null, "username_03", "password", false, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));


        ContractEntity contractEntity_c1_1 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2021-12-30"), "01", "123456", 1, 2, 3, null, null, null, true, companyEntity1, userEntity_1, null));
        ContractEntity contractEntity_c1_2 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2021-12-30"), "02", "654321", 1, 2, 3, null, null, null, true, companyEntity1, userEntity_1, userEntity_2));
        ContractEntity contractEntity_c2_1 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2021-12-30"), "02", "654321", 1, 2, 3, null, null, null, true, companyEntity2, userEntity_2, null));
        ContractEntity contractEntity_c2_2 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2021-12-30"), "02", "654321", 1, 2, 3, null, null, null, true, companyEntity2, null, null));

        User user = userMapper.toUser(userEntity_1);

        //When
        Contract contracts = contractService.getContract(user, contractEntity_c1_1.getId());

        //Then
        assertEquals(contractEntity_c1_1.getId(), contracts.getId());
    }

    @Test
    void checkGetContractAsUserNotInCompany() throws ParseException {
        //Given
        CompanyEntity companyEntity1 = companyDao.save(new CompanyEntity(null, "name 1", "department 1", "street 1", "73732", "esslingen", "Germany", true));
        CompanyEntity companyEntity2 = companyDao.save(new CompanyEntity(null, "name 2", "department 2", "street 2", "73732", "esslingen", "Germany", true));

        UserEntity userEntity_1 = userDao.save(new UserEntity(null, "username_01", "password", false, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));
        UserEntity userEntity_2 = userDao.save(new UserEntity(null, "username_02", "password", false, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));
        UserEntity userEntity_3 = userDao.save(new UserEntity(null, "username_03", "password", false, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));


        ContractEntity contractEntity_c1_1 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2021-12-30"), "01", "123456", 1, 2, 3, null, null, null, true, companyEntity1, userEntity_1, null));
        ContractEntity contractEntity_c1_2 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2021-12-30"), "02", "654321", 1, 2, 3, null, null, null, true, companyEntity1, userEntity_1, userEntity_2));
        ContractEntity contractEntity_c2_1 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2021-12-30"), "02", "654321", 1, 2, 3, null, null, null, true, companyEntity2, userEntity_2, null));
        ContractEntity contractEntity_c2_2 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2021-12-30"), "02", "654321", 1, 2, 3, null, null, null, true, companyEntity2, null, null));

        User user = userMapper.toUser(userEntity_3);

        //When
        Contract contracts = contractService.getContract(user, contractEntity_c2_2.getId());

        //Then
        assertNull(contracts);
    }

    @Test
    void checkGetContractAsAdmin() throws ParseException {
        //Given
        CompanyEntity companyEntity1 = companyDao.save(new CompanyEntity(null, "name 1", "department 1", "street 1", "73732", "esslingen", "Germany", true));
        CompanyEntity companyEntity2 = companyDao.save(new CompanyEntity(null, "name 2", "department 2", "street 2", "73732", "esslingen", "Germany", true));

        UserEntity userEntity_1 = userDao.save(new UserEntity(null, "username_01", "password", true, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));
        UserEntity userEntity_2 = userDao.save(new UserEntity(null, "username_02", "password", false, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));
        UserEntity userEntity_3 = userDao.save(new UserEntity(null, "username_03", "password", false, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));


        ContractEntity contractEntity_c1_1 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2021-12-30"), "01", "123456", 1, 2, 3, null, null, null, true, companyEntity1, userEntity_1, null));
        ContractEntity contractEntity_c1_2 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2021-12-30"), "02", "654321", 1, 2, 3, null, null, null, true, companyEntity1, userEntity_1, userEntity_2));
        ContractEntity contractEntity_c2_1 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2021-12-30"), "02", "654321", 1, 2, 3, null, null, null, true, companyEntity2, userEntity_2, null));
        ContractEntity contractEntity_c2_2 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2021-12-30"), "02", "654321", 1, 2, 3, null, null, null, true, companyEntity2, null, null));

        User user = userMapper.toUser(userEntity_3);

        //When
        Contract contracts = contractService.getContract(user, contractEntity_c2_2.getId());

        //Then
        assertNull(contracts);
    }

    @Test
    void checkCreateContractAsAdmin_1() throws ParseException {
        //Given
        CompanyEntity companyEntity1 = companyDao.save(new CompanyEntity(null, "name 1", "department 1", "street 1", "73732", "esslingen", "Germany", true));

        UserEntity userEntity_1 = userDao.save(new UserEntity(null, "username_01", "password", true, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));


        User user = userMapper.toUser(userEntity_1);

        Company company = companyMapper.toCompany(companyEntity1);

        Contract newContract = new Contract(null, formatter.parse("2021-10-20"), formatter.parse("2021-10-20"), "EXAMPLE", "1-1-1-1", company, "", "", "", 0, 0, 0, null, null);

        //When
        Contract contracts = contractService.createContract(user, newContract);

        //Then
        assertNotNull(contracts);
        assertEquals("EXAMPLE", contracts.getVersion());
    }

    @Test
    void checkCreateContractAsAdmin_2() {
        //Given
        CompanyEntity companyEntity1 = companyDao.save(new CompanyEntity(null, "name 1", "department 1", "street 1", "73732", "esslingen", "Germany", true));

        UserEntity userEntity_1 = userDao.save(new UserEntity(null, "username_01", "password", true, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));


        User user = userMapper.toUser(userEntity_1);

        Company company = companyMapper.toCompany(companyEntity1);

        Contract newContract = new Contract(null, null, null, "EXAMPLE", "1-1-1-1", company, "", "", "", 0, 0, 0, null, null);

        //When
        Contract contracts = contractService.createContract(user, newContract);

        //Then
        assertNotNull(contracts);
        assertEquals("EXAMPLE", contracts.getVersion());
        assertNull(contracts.getDateStart());
    }

    @Test
    void checkCreateContractAsAdmin_3() {
        //Given
        CompanyEntity companyEntity1 = companyDao.save(new CompanyEntity(null, "name 1", "department 1", "street 1", "73732", "esslingen", "Germany", true));

        UserEntity userEntity_1 = userDao.save(new UserEntity(null, "username_01", "password", true, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));


        User user = userMapper.toUser(userEntity_1);

        Company company = companyMapper.toCompany(companyEntity1);

        Contract newContract = new Contract(null, null, null, "EXAMPLE", "1-1-1-1", company, "", "", "", 0, 0, 0, user, null);

        //When
        Contract contracts = contractService.createContract(user, newContract);

        //Then
        assertNotNull(contracts);
        assertEquals("EXAMPLE", contracts.getVersion());
        assertNull(contracts.getDateStart());
    }

    @Test
    void checkCreateContractAsAdmin_4() {
        //Given
        CompanyEntity companyEntity1 = companyDao.save(new CompanyEntity(null, "name 1", "department 1", "street 1", "73732", "esslingen", "Germany", true));

        UserEntity userEntity_1 = userDao.save(new UserEntity(null, "username_01", "password", true, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));
        UserEntity userEntity_2 = userDao.save(new UserEntity(null, "username_02", "password", false, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));


        User user_1 = userMapper.toUser(userEntity_1);
        User user_2 = userMapper.toUser(userEntity_1);

        Company company = companyMapper.toCompany(companyEntity1);

        Contract newContract = new Contract(null, null, null, "EXAMPLE", "1-1-1-1", company, "101.102.103.103", "101.102.103.103", "101.102.103.103", 10, 20, 100, user_1, user_2);

        //When
        Contract contracts = contractService.createContract(user_1, newContract);

        //Then
        assertNotNull(contracts);
        assertEquals("EXAMPLE", contracts.getVersion());
        assertNull(contracts.getDateStart());
    }

    @Test
    void checkCreateContractAsUser() throws ParseException {
        //Given
        CompanyEntity companyEntity1 = companyDao.save(new CompanyEntity(null, "name 1", "department 1", "street 1", "73732", "esslingen", "Germany", true));

        UserEntity userEntity_1 = userDao.save(new UserEntity(null, "username_01", "password", false, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));


        User user = userMapper.toUser(userEntity_1);

        Company company = companyMapper.toCompany(companyEntity1);

        Contract newContract = new Contract(null, formatter.parse("2021-10-20"), formatter.parse("2021-10-20"), "EXAMPLE", "1-1-1-1", company, "", "", "", 0, 0, 0, null, null);

        //When
        Contract contracts = contractService.createContract(user, newContract);

        //Then
        assertNull(contracts);
    }

    @Test
    void checkDeleteContractAsUser() {
        //Given
        CompanyEntity companyEntity1 = companyDao.save(new CompanyEntity(null, "name 1", "department 1", "street 1", "73732", "esslingen", "Germany", true));

        UserEntity userEntity_1 = userDao.save(new UserEntity(null, "username_01", "password", false, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));

        ContractEntity contractEntity_c1_1 = contractDao.save(new ContractEntity(null, null, null, "01", "123456", 1, 2, 3, null, null, null, true, companyEntity1, null, null));

        User user = userMapper.toUser(userEntity_1);


        //When
        boolean isDeleted = contractService.deleteContract(user, contractEntity_c1_1.getId());


        //Then
        assertFalse(isDeleted);
        assertEquals(1, contractDao.getContracts().size());
    }

    @Test
    void checkDeleteContractAsAdmin() {
        //Given
        CompanyEntity companyEntity1 = companyDao.save(new CompanyEntity(null, "name 1", "department 1", "street 1", "73732", "esslingen", "Germany", true));

        UserEntity userEntity_1 = userDao.save(new UserEntity(null, "username_01", "password", true, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));

        ContractEntity contractEntity_c1_1 = contractDao.save(new ContractEntity(null, null, null, "01", "123456", 1, 2, 3, null, null, null, true, companyEntity1, null, null));

        User user = userMapper.toUser(userEntity_1);


        //When
        assertEquals(1, contractDao.getContracts().size());

        boolean isDeleted = contractService.deleteContract(user, contractEntity_c1_1.getId());


        //Then
        assertTrue(isDeleted);
        assertEquals(0, contractDao.getContracts().size());
    }

    @Test
    void checkUpdateContractAsUser() {
        //Given
        CompanyEntity companyEntity1 = companyDao.save(new CompanyEntity(null, "name 1", "department 1", "street 1", "73732", "esslingen", "Germany", true));

        UserEntity userEntity_1 = userDao.save(new UserEntity(null, "username_01", "password", false, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));

        ContractEntity contractEntity_c1_1 = contractDao.save(new ContractEntity(null, null, null, "01", "123456", 1, 2, 3, null, null, null, true, companyEntity1, null, null));

        User user = userMapper.toUser(userEntity_1);
        Contract contract = contractMapper.toContract(contractEntity_c1_1);

        contract.setIpaddress1("1.1.1.1");
        contract.setIpaddress2("1.1.1.1");
        contract.setIpaddress3("1.1.1.1");


        //When
        Contract updateContract = contractService.updateContract(user, contract);


        //Then
        assertNull(updateContract);
    }

    @Test
    void checkUpdateContractAsAdmin_1() {
        //Given
        CompanyEntity companyEntity1 = companyDao.save(new CompanyEntity(null, "name 1", "department 1", "street 1", "73732", "esslingen", "Germany", true));

        UserEntity userEntity_1 = userDao.save(new UserEntity(null, "username_01", "password", true, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));

        ContractEntity contractEntity_c1_1 = contractDao.save(new ContractEntity(null, null, null, "01", "123456", 1, 2, 3, null, null, null, true, companyEntity1, null, null));

        User user = userMapper.toUser(userEntity_1);
        Contract contract = new Contract();
        contract.setId(contractEntity_c1_1.getId());
        contract.setIpaddress1("1.1.1.1");
        contract.setIpaddress2("1.1.1.1");
        contract.setIpaddress3("1.1.1.1");


        //When
        Contract updateContract = contractService.updateContract(user, contract);


        //Then
        assertEquals("1.1.1.1", updateContract.getIpaddress1());
        assertEquals("1.1.1.1", updateContract.getIpaddress2());
        assertEquals("1.1.1.1", updateContract.getIpaddress3());
    }

    @Test
    void checkUpdateContractAsAdmin_2() {
        //Given
        CompanyEntity companyEntity1 = companyDao.save(new CompanyEntity(null, "name 1", "department 1", "street 1", "73732", "esslingen", "Germany", true));

        UserEntity userEntity_1 = userDao.save(new UserEntity(null, "username_01", "password", true, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));

        ContractEntity contractEntity_c1_1 = contractDao.save(new ContractEntity(null, null, null, "01", "123456", 1, 2, 3, null, null, null, true, companyEntity1, null, null));

        User user = userMapper.toUser(userEntity_1);
        Contract contract = new Contract();
        contract.setId(contractEntity_c1_1.getId());
        contract.setIpaddress1("1.1.1.1");
        contract.setIpaddress2("1.1.1.1");
        contract.setIpaddress3("1.1.1.1");


        //When
        Contract updateContract = contractService.updateContract(user, contract);


        //Then
        assertEquals("1.1.1.1", updateContract.getIpaddress1());
        assertEquals("1.1.1.1", updateContract.getIpaddress2());
        assertEquals("1.1.1.1", updateContract.getIpaddress3());
        assertEquals("01", updateContract.getVersion());
    }

    @Test
    void checkUpdateContractAsAdmin_3() throws ParseException {
        //Given
        CompanyEntity companyEntity1 = companyDao.save(new CompanyEntity(null, "name 1", "department 1", "street 1", "73732", "esslingen", "Germany", true));

        UserEntity userEntity_1 = userDao.save(new UserEntity(null, "username_01", "password", true, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));

        ContractEntity contractEntity_c1_1 = contractDao.save(new ContractEntity(null, null, null, "01", "123456", 1, 2, 3, null, null, null, true, companyEntity1, null, null));

        Date date = formatter.parse("2021-10-20");

        User user = userMapper.toUser(userEntity_1);
        Contract contract = new Contract();
        contract.setId(contractEntity_c1_1.getId());
        contract.setVersion("02");
        contract.setDateStart(date);
        contract.setDateStop(date);


        //When
        Contract updateContract = contractService.updateContract(user, contract);


        //Then
        assertEquals("02", updateContract.getVersion());
        assertEquals(date, updateContract.getDateStart());
        assertEquals(date, updateContract.getDateStop());
    }
}