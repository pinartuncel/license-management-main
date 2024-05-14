package de.hse.gruppe8.util.mapper;

import de.hse.gruppe8.jaxrs.model.User;
import de.hse.gruppe8.orm.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = MappingConfig.class, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    UserEntity toUserEntity(User user);

    User toUser(UserEntity userEntity);

    UserEntity mapUserEntityWithUser(@MappingTarget UserEntity s1, User s2);
}
