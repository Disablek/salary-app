package by.bntu.salaryapp.infrastructure.mapper.user.role;

import by.bntu.salaryapp.application.dto.user.role.RoleDto;
import by.bntu.salaryapp.domain.model.user.Permission;
import by.bntu.salaryapp.domain.model.user.Role;
import org.mapstruct.*;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface RoleMapper {
    @Mapping(source = "permissions", target = "permissions_id")
    @Mapping(source = "updatedBy.id", target = "updatedBy")
    @Mapping(source = "createdBy.id", target = "createdBy")
    RoleDto toDto(Role role);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "permissions", ignore = true)
    Role toEntity(RoleDto roleDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "permissions", ignore = true)
    void updateFromDto(RoleDto dto, @MappingTarget Role entity);

    default Set<UUID> permissionsToIds(Set<Permission> perms) {
        if (perms == null) return null;
        return perms.stream().map(Permission::getId).collect(Collectors.toSet());
    }
}
