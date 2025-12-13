package by.bntu.salaryapp.application.service.implementations.user;

import by.bntu.salaryapp.application.dto.user.role.RoleDto;
import by.bntu.salaryapp.application.service.interfaces.user.RoleService;
import by.bntu.salaryapp.domain.model.user.Permission;
import by.bntu.salaryapp.domain.model.user.Role;
import by.bntu.salaryapp.infrastructure.mapper.user.role.RoleListMapper;
import by.bntu.salaryapp.infrastructure.mapper.user.role.RoleMapper;
import by.bntu.salaryapp.infrastructure.persistence.repository.user.PermissionRepository;
import by.bntu.salaryapp.infrastructure.persistence.repository.user.RoleRepository;
import by.bntu.salaryapp.infrastructure.persistence.repository.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RoleMapper roleMapper;
    private final UserRepository userRepository;
    private final RoleListMapper roleListMapper;

    @Override
    @Transactional
    @PreAuthorize("hasRole('SUPERUSER')")
    public RoleDto create(RoleDto dto) {
        String name = normalizeRoleName(dto.getName());

        if (roleRepository.existsByName(name)) {
            throw new IllegalArgumentException("Role with name '" + name + "' already exists");
        }

        Role role = roleMapper.toEntity(dto);
        role.setName(name);

        assignPermissions(role, dto.getPermissionIds());

        Role savedRole = roleRepository.save(role);
        return roleMapper.toDto(savedRole);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('SUPERUSER')")
    public RoleDto update(UUID id, RoleDto roleDto) {
        Role existingRole = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + id));

        String newName = normalizeRoleName(roleDto.getName());

        if (!existingRole.getName().equals(newName) && roleRepository.existsByName(newName)) {
            throw new IllegalArgumentException("Role with name '" + newName + "' already exists");
        }

        roleMapper.updateFromDto(roleDto, existingRole);

        existingRole.setName(newName);

        assignPermissions(existingRole, roleDto.getPermissionIds());

        Role savedRole = roleRepository.save(existingRole);
        return roleMapper.toDto(savedRole);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('SUPERUSER')")
    public void delete(UUID id) {
        if (!roleRepository.existsById(id)) {
            throw new EntityNotFoundException("Role not found with id: " + id);
        }

        if (userRepository.existsByRoleId(id)) {
            throw new IllegalStateException("Cannot delete role assigned to users. Unassign it first.");
        }

        roleRepository.deleteById(id);
    }

    private void assignPermissions(Role role, Set<UUID> permissionIds) {
        if (permissionIds == null || permissionIds.isEmpty()) {
            role.setPermissions(new HashSet<>());
        } else {
            List<Permission> permissions = permissionRepository.findAllById(permissionIds);
            if (permissions.size() != permissionIds.size()) {
                throw new EntityNotFoundException("One or more permissions not found");
            }
            role.setPermissions(new HashSet<>(permissions));
        }
    }

    private String normalizeRoleName(String rawName) {
        if (rawName == null) throw new IllegalArgumentException("Name cannot be null");
        String trimmed = rawName.trim().toUpperCase();
        if (!trimmed.startsWith("ROLE_")) {
            return "ROLE_" + trimmed;
        }
        return trimmed;
    }

    @Override
    @PreAuthorize("hasRole('SUPERUSER')")
    public RoleDto getById(UUID id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + id));
        return roleMapper.toDto(role);
    }

    @Override
    @PreAuthorize("hasRole('SUPERUSER')")
    public List<RoleDto> getAll() {
        List<Role> roles = roleRepository.findAll();
        return roleListMapper.toDtoList(roles);
    }
}
