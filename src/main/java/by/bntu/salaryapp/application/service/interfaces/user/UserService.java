package by.bntu.salaryapp.application.service.interfaces.user;

import by.bntu.salaryapp.application.dto.user.user.UserDtoInput;
import by.bntu.salaryapp.application.dto.user.user.UserDtoOutput;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserDtoOutput create(UserDtoInput dto);

    UserDtoOutput update(UUID id, UserDtoInput dto);

    void delete(UUID id);

    UserDtoOutput getById(UUID id);

    List<UserDtoOutput> getAll();
}
