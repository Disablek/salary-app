package by.bntu.salaryapp.infrastructure.mapper.user.permission;

import by.bntu.salaryapp.application.dto.user.permission.PermissionDto;
import by.bntu.salaryapp.domain.model.user.Permission;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = PermissionMapper.class)
public interface PermissionListMapper {
    List<PermissionDto> toDtoList(List<Permission> permission);

    List<Permission> toEntity(List<PermissionDto> permissionDto);
}
