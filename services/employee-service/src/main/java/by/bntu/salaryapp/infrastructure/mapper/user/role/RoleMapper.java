package by.bntu.salaryapp.infrastructure.mapper.user.role;

import by.bntu.salaryapp.infrastructure.mapper.MapStructConfig;
import by.bntu.salaryapp.application.dto.user.role.RoleDto;
import by.bntu.salaryapp.domain.model.user.Role;
import org.mapstruct.*;


@Mapper(config = MapStructConfig.class)
public interface RoleMapper {
    // map auditing fields: entity has `createdBy(User)` and `createdDate`, DTO expects `createdBy(UUID)` and `createdAt`
    @Mapping(source = "updatedBy.id", target = "updatedBy")
    @Mapping(source = "createdBy.id", target = "createdBy")
    @Mapping(source = "createdDate", target = "createdAt")
    @Mapping(target = "permissionIds", ignore = true)
    RoleDto toDto(Role role);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    // entity uses createdDate field name
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Role toEntity(RoleDto roleDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateFromDto(RoleDto dto, @MappingTarget Role entity);

}
