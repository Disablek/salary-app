package by.bntu.salaryapp.infrastructure.mapper.user.user;

import by.bntu.salaryapp.application.dto.user.user.UserDtoInput;
import by.bntu.salaryapp.application.dto.user.user.UserDtoOutput;
import by.bntu.salaryapp.domain.model.user.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserMapper {
    @Mapping(source = "updatedBy.id", target = "updatedBy")
    @Mapping(source = "createdBy.id", target = "createdBy")
    @Mapping(target = "password", ignore = true)
    UserDtoOutput toDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toEntity(UserDtoInput userDtoInput);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateFromDto(UserDtoInput dto, @MappingTarget User entity);
}
