package de.hse.gruppe8.util.mapper;


import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

@MapperConfig(componentModel = "cdi", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MappingConfig {

}
