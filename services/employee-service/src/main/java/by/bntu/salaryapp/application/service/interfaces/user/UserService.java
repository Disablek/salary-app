package by.bntu.salaryapp.application.service.interfaces.user;

import by.bntu.salaryapp.application.dto.user.user.UserDtoInput;
import by.bntu.salaryapp.application.dto.user.user.UserDtoOutput;
import by.bntu.salaryapp.application.dto.user.user.UserFilterDto;
import by.bntu.salaryapp.infrastructure.persistence.specifications.user.user.UserFilterBySpecification;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserDtoOutput create(UserDtoInput dto);

    UserDtoOutput update(UUID id, UserDtoInput dto);

    void delete(UUID id);

    UserDtoOutput getById(UUID id);

    List<UserDtoOutput> getAll();

    List<UserDtoOutput> getUsersByFilter(UserFilterDto filter);

    UserDtoOutput changePassword(UUID userId, String oldPassword, String newPassword);

    UserDtoOutput getByEmailAsDto(String email);

    UserDtoOutput getByUsernameAsDto(String username);
}