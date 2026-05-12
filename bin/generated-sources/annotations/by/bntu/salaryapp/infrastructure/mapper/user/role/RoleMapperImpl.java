package by.bntu.salaryapp.infrastructure.mapper.user.role;

import by.bntu.salaryapp.application.dto.user.role.RoleDto;
import by.bntu.salaryapp.domain.model.user.Role;
import by.bntu.salaryapp.domain.model.user.User;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-10T13:02:19+0300",
    comments = "version: 1.6.0, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class RoleMapperImpl implements RoleMapper {

    @Override
    public RoleDto toDto(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleDto roleDto = new RoleDto();

        roleDto.setUpdatedBy( roleUpdatedById( role ) );
        roleDto.setCreatedBy( roleCreatedById( role ) );
        roleDto.setCreatedAt( role.getCreatedDate() );
        roleDto.setId( role.getId() );
        roleDto.setName( role.getName() );
        roleDto.setUpdatedAt( role.getUpdatedAt() );

        return roleDto;
    }

    @Override
    public Role toEntity(RoleDto roleDto) {
        if ( roleDto == null ) {
            return null;
        }

        Role role = new Role();

        role.setName( roleDto.getName() );

        return role;
    }

    @Override
    public void updateFromDto(RoleDto dto, Role entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
    }

    private UUID roleUpdatedById(Role role) {
        User updatedBy = role.getUpdatedBy();
        if ( updatedBy == null ) {
            return null;
        }
        return updatedBy.getId();
    }

    private UUID roleCreatedById(Role role) {
        User createdBy = role.getCreatedBy();
        if ( createdBy == null ) {
            return null;
        }
        return createdBy.getId();
    }
}
