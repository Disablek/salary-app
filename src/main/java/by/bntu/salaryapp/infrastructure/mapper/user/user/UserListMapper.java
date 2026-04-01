package by.bntu.salaryapp.infrastructure.mapper.user.user;

import by.bntu.salaryapp.infrastructure.mapper.MapStructConfig;
import by.bntu.salaryapp.application.dto.user.user.UserDtoInput;
import by.bntu.salaryapp.application.dto.user.user.UserDtoOutput;
import by.bntu.salaryapp.domain.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(config = MapStructConfig.class, uses = UserMapper.class)
public interface UserListMapper {
    List<UserDtoOutput> toDtoList(List<User> users);

    List<User> toEntityList(List<UserDtoInput> userDtoInputs);
}
