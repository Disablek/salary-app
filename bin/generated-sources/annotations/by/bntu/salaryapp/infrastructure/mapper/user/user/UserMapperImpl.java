package by.bntu.salaryapp.infrastructure.mapper.user.user;

import by.bntu.salaryapp.application.dto.user.user.UserDtoInput;
import by.bntu.salaryapp.application.dto.user.user.UserDtoOutput;
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
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDtoOutput toDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDtoOutput userDtoOutput = new UserDtoOutput();

        userDtoOutput.setUpdatedBy( userUpdatedById( user ) );
        userDtoOutput.setCreatedBy( userCreatedById( user ) );
        userDtoOutput.setCreatedAt( user.getCreatedDate() );
        userDtoOutput.setRoleId( userRoleId( user ) );
        userDtoOutput.setRoleName( userRoleName( user ) );
        userDtoOutput.setEmail( user.getEmail() );
        userDtoOutput.setFirstName( user.getFirstName() );
        userDtoOutput.setId( user.getId() );
        userDtoOutput.setLastName( user.getLastName() );
        userDtoOutput.setUpdatedAt( user.getUpdatedAt() );
        userDtoOutput.setUsername( user.getUsername() );

        return userDtoOutput;
    }

    @Override
    public User toEntity(UserDtoInput userDtoInput) {
        if ( userDtoInput == null ) {
            return null;
        }

        User user = new User();

        user.setEmail( userDtoInput.getEmail() );
        user.setFirstName( userDtoInput.getFirstName() );
        user.setLastName( userDtoInput.getLastName() );
        user.setPassword( userDtoInput.getPassword() );
        user.setUsername( userDtoInput.getUsername() );

        return user;
    }

    @Override
    public void updateFromDto(UserDtoInput dto, User entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getEmail() != null ) {
            entity.setEmail( dto.getEmail() );
        }
        if ( dto.getFirstName() != null ) {
            entity.setFirstName( dto.getFirstName() );
        }
        if ( dto.getLastName() != null ) {
            entity.setLastName( dto.getLastName() );
        }
        if ( dto.getPassword() != null ) {
            entity.setPassword( dto.getPassword() );
        }
        if ( dto.getUsername() != null ) {
            entity.setUsername( dto.getUsername() );
        }
    }

    private UUID userUpdatedById(User user) {
        User updatedBy = user.getUpdatedBy();
        if ( updatedBy == null ) {
            return null;
        }
        return updatedBy.getId();
    }

    private UUID userCreatedById(User user) {
        User createdBy = user.getCreatedBy();
        if ( createdBy == null ) {
            return null;
        }
        return createdBy.getId();
    }

    private UUID userRoleId(User user) {
        Role role = user.getRole();
        if ( role == null ) {
            return null;
        }
        return role.getId();
    }

    private String userRoleName(User user) {
        Role role = user.getRole();
        if ( role == null ) {
            return null;
        }
        return role.getName();
    }
}
