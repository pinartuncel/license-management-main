package de.hse.gruppe8.util.mapper;

import de.hse.gruppe8.jaxrs.model.Contract;
import de.hse.gruppe8.orm.model.ContractEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = MappingConfig.class, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ContractMapper {

    ContractEntity toContractEntity(Contract contract);

    Contract toContract(ContractEntity contractEntity);

    ContractEntity mapContractEntityWithContract(@MappingTarget ContractEntity s1, Contract s2);
}
