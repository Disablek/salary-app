package by.bntu.salaryapp.infrastructure.mapper.user.role;

import by.bntu.salaryapp.infrastructure.mapper.MapStructConfig;
import by.bntu.salaryapp.application.dto.user.role.RoleDto;
import by.bntu.salaryapp.domain.model.user.Role;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapStructConfig.class, uses = RoleMapper.class)
public interface RoleListMapper {
    List<RoleDto> toDtoList(List<Role> role);

    List<Role> toEntityList(List<RoleDto> roleDto);
}
