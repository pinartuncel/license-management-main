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
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class CompanyServiceTest {

    @Inject
    CompanyService companyService;

    @Inject
    UserDao userDao;

    @Inject
    CompanyDao companyDao;

    @Inject
    ContractDao contractDao;

    @BeforeEach
    @AfterEach
    void InitDatabase() {
        contractDao.removeAll();
        userDao.removeAll();
        companyDao.removeAll();
    }

    @Test
    void checkGetCompaniesAsAdmin() {
        //Given
        CompanyEntity companyEntity1 = new CompanyEntity(1L, "name 1", "department 1", "street 1", "73732", "esslingen", "Germany", true);
        CompanyEntity companyEntity2 = new CompanyEntity(2L, "name 2", "department 2", "street 2", "73732", "esslingen", "Germany", true);
        companyDao.save(companyEntity1);
        companyDao.save(companyEntity2);

        Company company = new Company(2L, "name 2", "department 2", "street 2", "73732", "esslingen", "Germany");

        User user = new User(1L, "username", true, "firstName", "lastName", "lala@lala.de", null, null, "", company);
        //When
        List<Company> companies = companyService.getCompanies(user);

        //Then
        assertEquals(2, companies.size());
    }

    @Test
    void checkGetCompaniesAsUser() {
        //Given
        CompanyEntity companyEntity1 = new CompanyEntity(1L, "name 1", "department 1", "street 1", "73732", "esslingen", "Germany", true);
        CompanyEntity companyEntity2 = new CompanyEntity(2L, "name 2", "department 2", "street 2", "73732", "esslingen", "Germany", true);
        companyDao.save(companyEntity1);
        companyDao.save(companyEntity2);

        Company company = new Company(2L, "name 2", "department 2", "street 2", "73732", "esslingen", "Germany");

        User user = new User(1L, "username", false, "firstName", "lastName", "lala@lala.de", null, null, "", company);
        //When
        List<Company> companies = companyService.getCompanies(user);

        //Then
        assertEquals(1, companies.size());
        assertEquals(2L, company.getId());
        assertEquals("name 2", company.getName());
    }

    @Test
    void checkGetCompanyByIdAsAdmin_1() {
        //Given
        CompanyEntity companyEntity1 = new CompanyEntity(1L, "name 1", "department 1", "street 1", "73732", "esslingen", "Germany", true);
        CompanyEntity companyEntity2 = new CompanyEntity(2L, "name 2", "department 2", "street 2", "73732", "esslingen", "Germany", true);
        companyEntity1 = companyDao.save(companyEntity1);
        companyEntity2 = companyDao.save(companyEntity2);

        Company userCompany = new Company(companyEntity2.getId(), "name 2", "department 2", "street 2", "73732", "esslingen", "Germany");

        User user = new User(1L, "username", true, "firstName", "lastName", "lala@lala.de", null, null, "", userCompany);
        //When
        Company company = companyService.getCompany(user, companyEntity1.getId());

        //Then
        assertEquals(companyEntity1.getId(), company.getId());
        assertEquals("name 1", company.getName());
    }

    @Test
    void checkGetCompanyByIdAsAdmin_2() {
        //Given
        CompanyEntity companyEntity1 = new CompanyEntity(1L, "name 1", "department 1", "street 1", "73732", "esslingen", "Germany", true);
        CompanyEntity companyEntity2 = new CompanyEntity(2L, "name 2", "department 2", "street 2", "73732", "esslingen", "Germany", true);
        companyEntity1 = companyDao.save(companyEntity1);
        companyEntity2 = companyDao.save(companyEntity2);

        Company userCompany = new Company(companyEntity2.getId(), "name 2", "department 2", "street 2", "73732", "esslingen", "Germany");

        User user = new User(1L, "username", true, "firstName", "lastName", "lala@lala.de", null, null, "", userCompany);
        //When
        Company company = companyService.getCompany(user, companyEntity2.getId());

        //Then
        assertEquals(companyEntity2.getId(), company.getId());
        assertEquals("name 2", company.getName());
    }


    @Test
    void checkGetCompanyByIdAsUser_1() {
        //Given
        CompanyEntity companyEntity1 = new CompanyEntity(1L, "name 1", "department 1", "street 1", "73732", "esslingen", "Germany", true);
        CompanyEntity companyEntity2 = new CompanyEntity(2L, "name 2", "department 2", "street 2", "73732", "esslingen", "Germany", true);
        companyDao.save(companyEntity1);
        companyDao.save(companyEntity2);

        Company userCompany = new Company(2L, "name 2", "department 2", "street 2", "73732", "esslingen", "Germany");

        User user = new User(1L, "username", false, "firstName", "lastName", "lala@lala.de", null, null, "", userCompany);
        //When
        Company company = companyService.getCompany(user, 1L);

        //Then
        assertNull(company);
    }

    @Test
    void checkGetCompanyByIdAsUser_2() {
        //Given
        CompanyEntity companyEntity1 = new CompanyEntity(1L, "name 1", "department 1", "street 1", "73732", "esslingen", "Germany", true);
        CompanyEntity companyEntity2 = new CompanyEntity(2L, "name 2", "department 2", "street 2", "73732", "esslingen", "Germany", true);
        companyDao.save(companyEntity1);
        companyDao.save(companyEntity2);

        Company userCompany = new Company(2L, "name 2", "department 2", "street 2", "73732", "esslingen", "Germany");

        User user = new User(1L, "username", false, "firstName", "lastName", "lala@lala.de", null, null, "", userCompany);
        //When
        Company company = companyService.getCompany(user, 2L);

        //Then
        assertEquals(2L, company.getId());
        assertEquals("name 2", company.getName());
    }

    @Test
    void checkCreateCompanyAsAdmin() {
        //Given
        Company userCompany = new Company(null, "name 2", "department 2", "street 2", "73732", "esslingen", "Germany");

        User user = new User(1L, "username", true, "firstName", "lastName", "lala@lala.de", null, null, "", null);
        //When
        Company company = companyService.createCompany(user, userCompany);

        //Then
        assertEquals(1, companyDao.getCompanies().size());
        assertNotNull(company);
    }

    @Test
    void checkCreateCompanyAsUser() {
        //Given
        Company userCompany = new Company(null, "name 2", "department 2", "street 2", "73732", "esslingen", "Germany");

        User user = new User(1L, "username", false, "firstName", "lastName", "lala@lala.de", null, null, "", null);
        //When
        Company company = companyService.createCompany(user, userCompany);

        //Then
        assertEquals(0, companyDao.getCompanies().size());
        assertNull(company);
    }

    @Test
    void checkDeleteCompanyAsAdmin() {
        //Given
        CompanyEntity companyEntity1 = new CompanyEntity(1L, "name 1", "department 1", "street 1", "73732", "esslingen", "Germany", true);
        CompanyEntity companyEntity2 = new CompanyEntity(2L, "name 2", "department 2", "street 2", "73732", "esslingen", "Germany", true);
        companyEntity1 = companyDao.save(companyEntity1);
        companyDao.save(companyEntity2);

        User user = new User(1L, "username", true, "firstName", "lastName", "lala@lala.de", null, null, "", null);
        //When
        boolean isSuccess = companyService.deleteCompany(user, companyEntity1.getId());

        //Then
        assertEquals(1, companyDao.getCompanies().size());
        assertTrue(isSuccess);
    }


    @Test
    void checkDeleteCompanyAsUser() {
        //Given
        CompanyEntity companyEntity1 = new CompanyEntity(1L, "name 1", "department 1", "street 1", "73732", "esslingen", "Germany", true);
        CompanyEntity companyEntity2 = new CompanyEntity(2L, "name 2", "department 2", "street 2", "73732", "esslingen", "Germany", true);
        companyEntity1 = companyDao.save(companyEntity1);
        companyDao.save(companyEntity2);

        User user = new User(1L, "username", false, "firstName", "lastName", "lala@lala.de", null, null, "", null);
        //When
        boolean isSuccess = companyService.deleteCompany(user, companyEntity1.getId());

        //Then
        assertEquals(2, companyDao.getCompanies().size());
        assertFalse(isSuccess);
    }


    @Test
    void checkUpdateCompanyAsAdmin_1() {
        //Given
        CompanyEntity companyEntity1 = new CompanyEntity(1L, "name 1", "department 1", "street 1", "73732", "esslingen", "Germany", true);
        companyEntity1 = companyDao.save(companyEntity1);

        Company company = new Company();
        company.setId(companyEntity1.getId());
        company.setDepartment("new_Value_1");
        company.setCity("new_Value_2");

        User user = new User(1L, "username", true, "firstName", "lastName", "lala@lala.de", null, null, "", null);
        //When
        Company newCompany = companyService.updateCompany(user, company);

        //Then
        assertEquals("name 1", newCompany.getName());
        assertEquals("new_Value_1", newCompany.getDepartment());
        assertEquals("street 1", newCompany.getStreet());
        assertEquals("73732", newCompany.getZipCode());
        assertEquals("new_Value_2", newCompany.getCity());
        assertEquals("Germany", newCompany.getCountry());
    }

    @Test
    void checkUpdateCompanyAsAdmin_2() {
        //Given
        CompanyEntity companyEntity1 = companyDao.save(new CompanyEntity(1L, "name 1", null, "street 1", "73732", null, "Germany", true));

        Company company = new Company();
        company.setId(companyEntity1.getId());
        company.setDepartment("new_Value_1");
        company.setCity("new_Value_2");

        User user = new User(1L, "username", true, "firstName", "lastName", "lala@lala.de", null, null, "", null);
        //When
        Company newCompany = companyService.updateCompany(user, company);

        //Then
        assertEquals("name 1", newCompany.getName());
        assertEquals("new_Value_1", newCompany.getDepartment());
        assertEquals("street 1", newCompany.getStreet());
        assertEquals("73732", newCompany.getZipCode());
        assertEquals("new_Value_2", newCompany.getCity());
        assertEquals("Germany", newCompany.getCountry());
    }

    @Test
    void checkUpdateCompanyAsUser() {
        //Given
        User user = new User(1L, "username", false, "firstName", "lastName", "lala@lala.de", null, null, "", null);

        //When
        Company company = companyService.updateCompany(user, null);

        //Then
        assertNull(company);
    }

    @Test
    void checkGetUsersFromCompanyAsAdmin_1() {
        //Given
        CompanyEntity companyEntity1 = companyDao.save(new CompanyEntity(null, "name 1", "department 1", "street 1", "73732", "esslingen", "Germany", true));
        CompanyEntity companyEntity2 = companyDao.save(new CompanyEntity(null, "name 2", "department 2", "street 2", "73732", "esslingen", "Germany", true));

        UserEntity userEntity1 = userDao.save(new UserEntity(null, "username1", "password", true, "firstname", "lastname", "dd@dd.de", "+4911", "+49222", true, companyEntity1));
        UserEntity userEntity2 = userDao.save(new UserEntity(null, "username2", "password", true, "firstname", "lastname", "dd@dd.de", "+4911", "+49222", true, companyEntity1));
        UserEntity userEntity3 = userDao.save(new UserEntity(null, "username3", "password", true, "firstname", "lastname", "dd@dd.de", "+4911", "+49222", true, companyEntity1));

        UserEntity userEntity4 = userDao.save(new UserEntity(null, "username4", "password", true, "firstname", "lastname", "dd@dd.de", "+4911", "+49222", true, companyEntity2));
        UserEntity userEntity5 = userDao.save(new UserEntity(null, "username5", "password", true, "firstname", "lastname", "dd@dd.de", "+4911", "+49222", true, companyEntity2));

        Company company = new Company(companyEntity1.getId(), "name 2", "department 2", "street 2", "73732", "esslingen", "Germany");
        User user = new User(1L, "username", true, "firstName", "lastName", "lala@lala.de", null, null, "", company);
        //When
        List<User> users = companyService.getUsersFromCompany(user, companyEntity1.getId());

        //Then
        assertEquals(3, users.size());
    }

    @Test
    void checkGetUsersFromCompanyAsAdmin_2() {
        //Given
        CompanyEntity companyEntity1 = companyDao.save(new CompanyEntity(null, "name 1", "department 1", "street 1", "73732", "esslingen", "Germany", true));
        CompanyEntity companyEntity2 = companyDao.save(new CompanyEntity(null, "name 2", "department 2", "street 2", "73732", "esslingen", "Germany", true));

        UserEntity userEntity1 = userDao.save(new UserEntity(null, "username1", "password", true, "firstname", "lastname", "dd@dd.de", "+4911", "+49222", true, companyEntity1));
        UserEntity userEntity2 = userDao.save(new UserEntity(null, "username2", "password", true, "firstname", "lastname", "dd@dd.de", "+4911", "+49222", true, companyEntity1));
        UserEntity userEntity3 = userDao.save(new UserEntity(null, "username3", "password", true, "firstname", "lastname", "dd@dd.de", "+4911", "+49222", true, companyEntity1));

        UserEntity userEntity4 = userDao.save(new UserEntity(null, "username4", "password", true, "firstname", "lastname", "dd@dd.de", "+4911", "+49222", true, companyEntity2));
        UserEntity userEntity5 = userDao.save(new UserEntity(null, "username5", "password", true, "firstname", "lastname", "dd@dd.de", "+4911", "+49222", true, companyEntity2));

        Company company = new Company(companyEntity1.getId(), "name 2", "department 2", "street 2", "73732", "esslingen", "Germany");
        User user = new User(1L, "username", true, "firstName", "lastName", "lala@lala.de", null, null, "", company);
        //When
        List<User> users = companyService.getUsersFromCompany(user, companyEntity2.getId());

        //Then
        assertEquals(2, users.size());
    }

    @Test
    void checkGetUsersFromCompanyAsUser() {
        //Given
        CompanyEntity companyEntity1 = companyDao.save(new CompanyEntity(null, "name 1", "department 1", "street 1", "73732", "esslingen", "Germany", true));
        CompanyEntity companyEntity2 = companyDao.save(new CompanyEntity(null, "name 2", "department 2", "street 2", "73732", "esslingen", "Germany", true));

        UserEntity userEntity1 = userDao.save(new UserEntity(null, "username1", "password", true, "firstname", "lastname", "dd@dd.de", "+4911", "+49222", true, companyEntity1));
        UserEntity userEntity2 = userDao.save(new UserEntity(null, "username2", "password", true, "firstname", "lastname", "dd@dd.de", "+4911", "+49222", true, companyEntity1));
        UserEntity userEntity3 = userDao.save(new UserEntity(null, "username3", "password", true, "firstname", "lastname", "dd@dd.de", "+4911", "+49222", true, companyEntity1));

        UserEntity userEntity4 = userDao.save(new UserEntity(null, "username4", "password", true, "firstname", "lastname", "dd@dd.de", "+4911", "+49222", true, companyEntity2));
        UserEntity userEntity5 = userDao.save(new UserEntity(null, "username5", "password", true, "firstname", "lastname", "dd@dd.de", "+4911", "+49222", true, companyEntity2));

        Company company = new Company(companyEntity1.getId(), "name 2", "department 2", "street 2", "73732", "esslingen", "Germany");
        User user = new User(1L, "username", false, "firstName", "lastName", "lala@lala.de", null, null, "", company);
        //When
        List<User> users = companyService.getUsersFromCompany(user, companyEntity1.getId());

        //Then
        assertEquals(3, users.size());
    }

    @Test
    void checkGetUsersFromCompanyAsUserFromOtherCompany() {
        //Given
        CompanyEntity companyEntity1 = companyDao.save(new CompanyEntity(null, "name 1", "department 1", "street 1", "73732", "esslingen", "Germany", true));
        CompanyEntity companyEntity2 = companyDao.save(new CompanyEntity(null, "name 2", "department 2", "street 2", "73732", "esslingen", "Germany", true));

        UserEntity userEntity1 = userDao.save(new UserEntity(null, "username1", "password", true, "firstname", "lastname", "dd@dd.de", "+4911", "+49222", true, companyEntity1));
        UserEntity userEntity2 = userDao.save(new UserEntity(null, "username2", "password", true, "firstname", "lastname", "dd@dd.de", "+4911", "+49222", true, companyEntity1));
        UserEntity userEntity3 = userDao.save(new UserEntity(null, "username3", "password", true, "firstname", "lastname", "dd@dd.de", "+4911", "+49222", true, companyEntity1));

        UserEntity userEntity4 = userDao.save(new UserEntity(null, "username4", "password", true, "firstname", "lastname", "dd@dd.de", "+4911", "+49222", true, companyEntity2));
        UserEntity userEntity5 = userDao.save(new UserEntity(null, "username5", "password", true, "firstname", "lastname", "dd@dd.de", "+4911", "+49222", true, companyEntity2));

        Company company = new Company(companyEntity1.getId(), "name 2", "department 2", "street 2", "73732", "esslingen", "Germany");
        User user = new User(1L, "username", false, "firstName", "lastName", "lala@lala.de", null, null, "", company);
        //When
        List<User> users = companyService.getUsersFromCompany(user, companyEntity2.getId());

        //Then
        assertEquals(0, users.size());
    }

    @Test
    void checkGetContractsFromCompanyAsAdmin_1() throws ParseException {
        //Given
        CompanyEntity companyEntity1 = companyDao.save(new CompanyEntity(null, "name 1", "department 1", "street 1", "73732", "esslingen", "Germany", true));
        CompanyEntity companyEntity2 = companyDao.save(new CompanyEntity(null, "name 2", "department 2", "street 2", "73732", "esslingen", "Germany", true));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ContractEntity userEntity1 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2022-10-20"), "1.0.0", "dd-d-dd-d-", 0, 0, 0, "0.0.0.0", "", "", true, companyEntity1, null, null));
        ContractEntity userEntity2 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2022-10-20"), "1.0.0", "dd-d-dd-d-", 0, 0, 0, "0.0.0.0", "", "", true, companyEntity1, null, null));
        ContractEntity userEntity3 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2023-10-20"), "1.0.0", "dd-d-dd-d-", 0, 0, 0, "0.0.0.0", "", "", true, companyEntity1, null, null));

        ContractEntity userEntity4 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2023-10-20"), "1.0.0", "dd-d-dd-d-", 0, 0, 0, "0.0.0.0", "", "", true, companyEntity2, null, null));
        ContractEntity userEntity5 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2023-10-20"), "1.0.0", "dd-d-dd-d-", 0, 0, 0, "0.0.0.0", "", "", true, companyEntity2, null, null));

        Company company = new Company(companyEntity1.getId(), "name 2", "department 2", "street 2", "73732", "esslingen", "Germany");
        User user = new User(1L, "username", true, "firstName", "lastName", "lala@lala.de", null, null, "", company);
        //When
        List<Contract> users = companyService.getContractsFromCompany(user, companyEntity1.getId());

        //Then
        assertEquals(3, users.size());
    }


    @Test
    void checkGetContractsFromCompanyAsAdmin_2() throws ParseException {
        //Given
        CompanyEntity companyEntity1 = companyDao.save(new CompanyEntity(null, "name 1", "department 1", "street 1", "73732", "esslingen", "Germany", true));
        CompanyEntity companyEntity2 = companyDao.save(new CompanyEntity(null, "name 2", "department 2", "street 2", "73732", "esslingen", "Germany", true));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ContractEntity userEntity1 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2022-10-20"), "1.0.0", "dd-d-dd-d-", 0, 0, 0, "0.0.0.0", "", "", true, companyEntity1, null, null));
        ContractEntity userEntity2 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2022-10-20"), "1.0.0", "dd-d-dd-d-", 0, 0, 0, "0.0.0.0", "", "", true, companyEntity1, null, null));
        ContractEntity userEntity3 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2023-10-20"), "1.0.0", "dd-d-dd-d-", 0, 0, 0, "0.0.0.0", "", "", true, companyEntity1, null, null));

        ContractEntity userEntity4 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2023-10-20"), "1.0.0", "dd-d-dd-d-", 0, 0, 0, "0.0.0.0", "", "", true, companyEntity2, null, null));
        ContractEntity userEntity5 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2023-10-20"), "1.0.0", "dd-d-dd-d-", 0, 0, 0, "0.0.0.0", "", "", true, companyEntity2, null, null));

        Company company = new Company(companyEntity1.getId(), "name 2", "department 2", "street 2", "73732", "esslingen", "Germany");
        User user = new User(1L, "username", true, "firstName", "lastName", "lala@lala.de", null, null, "", company);
        //When
        List<Contract> users = companyService.getContractsFromCompany(user, companyEntity2.getId());

        //Then
        assertEquals(2, users.size());
    }

    @Test
    void checkGetContractsFromCompanyAsUser_1() throws ParseException {
        //Given
        CompanyEntity companyEntity1 = companyDao.save(new CompanyEntity(null, "name 1", "department 1", "street 1", "73732", "esslingen", "Germany", true));
        CompanyEntity companyEntity2 = companyDao.save(new CompanyEntity(null, "name 2", "department 2", "street 2", "73732", "esslingen", "Germany", true));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ContractEntity userEntity1 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2022-10-20"), "1.0.0", "dd-d-dd-d-", 0, 0, 0, "0.0.0.0", "", "", true, companyEntity1, null, null));
        ContractEntity userEntity2 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2022-10-20"), "1.0.0", "dd-d-dd-d-", 0, 0, 0, "0.0.0.0", "", "", true, companyEntity1, null, null));
        ContractEntity userEntity3 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2023-10-20"), "1.0.0", "dd-d-dd-d-", 0, 0, 0, "0.0.0.0", "", "", true, companyEntity1, null, null));

        ContractEntity userEntity4 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2023-10-20"), "1.0.0", "dd-d-dd-d-", 0, 0, 0, "0.0.0.0", "", "", true, companyEntity2, null, null));
        ContractEntity userEntity5 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2023-10-20"), "1.0.0", "dd-d-dd-d-", 0, 0, 0, "0.0.0.0", "", "", true, companyEntity2, null, null));

        Company company = new Company(companyEntity1.getId(), "name 2", "department 2", "street 2", "73732", "esslingen", "Germany");
        User user = new User(1L, "username", false, "firstName", "lastName", "lala@lala.de", null, null, "", company);
        //When
        List<Contract> users = companyService.getContractsFromCompany(user, companyEntity1.getId());

        //Then
        assertEquals(3, users.size());
    }

    @Test
    void checkGetContractsFromCompanyAsUser_2() throws ParseException {
        //Given
        CompanyEntity companyEntity1 = companyDao.save(new CompanyEntity(null, "name 1", "department 1", "street 1", "73732", "esslingen", "Germany", true));
        CompanyEntity companyEntity2 = companyDao.save(new CompanyEntity(null, "name 2", "department 2", "street 2", "73732", "esslingen", "Germany", true));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ContractEntity userEntity1 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2022-10-20"), "1.0.0", "dd-d-dd-d-", 0, 0, 0, "0.0.0.0", "", "", true, companyEntity1, null, null));
        ContractEntity userEntity2 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2022-10-20"), "1.0.0", "dd-d-dd-d-", 0, 0, 0, "0.0.0.0", "", "", true, companyEntity1, null, null));
        ContractEntity userEntity3 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2023-10-20"), "1.0.0", "dd-d-dd-d-", 0, 0, 0, "0.0.0.0", "", "", true, companyEntity1, null, null));

        ContractEntity userEntity4 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2023-10-20"), "1.0.0", "dd-d-dd-d-", 0, 0, 0, "0.0.0.0", "", "", true, companyEntity2, null, null));
        ContractEntity userEntity5 = contractDao.save(new ContractEntity(null, formatter.parse("2021-10-20"), formatter.parse("2023-10-20"), "1.0.0", "dd-d-dd-d-", 0, 0, 0, "0.0.0.0", "", "", true, companyEntity2, null, null));

        Company company = new Company(companyEntity1.getId(), "name 2", "department 2", "street 2", "73732", "esslingen", "Germany");
        User user = new User(1L, "username", false, "firstName", "lastName", "lala@lala.de", null, null, "", company);
        //When
        List<Contract> users = companyService.getContractsFromCompany(user, companyEntity2.getId());

        //Then
        assertEquals(0, users.size());
    }
}