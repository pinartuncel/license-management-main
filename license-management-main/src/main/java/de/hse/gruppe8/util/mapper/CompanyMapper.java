package de.hse.gruppe8.util.mapper;

import de.hse.gruppe8.jaxrs.model.Company;
import de.hse.gruppe8.orm.model.CompanyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = MappingConfig.class, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CompanyMapper {

    CompanyEntity toCompanyEntity(Company company);

    Company toCompany(CompanyEntity companyEntity);

    CompanyEntity mapCompanyEntityWithCompany(@MappingTarget CompanyEntity s1, Company s2);

}
