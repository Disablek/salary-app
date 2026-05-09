package by.bntu.salaryapp.application.service.interfaces.user;

import by.bntu.salaryapp.application.dto.user.role.RoleDto;

import java.util.List;
import java.util.UUID;

public interface RoleService {
    RoleDto create(RoleDto roleDto);

    RoleDto update(UUID id, RoleDto roleDto);

    void delete(UUID id);

    RoleDto getById(UUID id);

    List<RoleDto> getAll();
}
