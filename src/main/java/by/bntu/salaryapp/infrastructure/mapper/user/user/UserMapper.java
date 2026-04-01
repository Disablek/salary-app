package by.bntu.salaryapp.infrastructure.mapper.user.user;

import by.bntu.salaryapp.application.dto.user.user.UserDtoInput;
import by.bntu.salaryapp.application.dto.user.user.UserDtoOutput;
import by.bntu.salaryapp.domain.model.user.User;
import org.mapstruct.*;
import by.bntu.salaryapp.infrastructure.mapper.MapStructConfig;

@Mapper(config = MapStructConfig.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    @Mapping(source = "updatedBy.id", target = "updatedBy")
    @Mapping(source = "createdBy.id", target = "createdBy")
    @Mapping(source = "createdDate", target = "createdAt")
    @Mapping(source = "role.id", target = "roleId")
    @Mapping(source = "role.name", target = "roleName")
    UserDtoOutput toDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "surname", ignore = true)
    User toEntity(UserDtoInput userDtoInput);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "surname", ignore = true)
    void updateFromDto(UserDtoInput dto, @MappingTarget User entity);
}
