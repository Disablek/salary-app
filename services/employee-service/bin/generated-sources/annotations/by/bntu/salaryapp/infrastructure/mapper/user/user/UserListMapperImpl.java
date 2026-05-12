package by.bntu.salaryapp.infrastructure.mapper.user.user;

import by.bntu.salaryapp.application.dto.user.user.UserDtoInput;
import by.bntu.salaryapp.application.dto.user.user.UserDtoOutput;
import by.bntu.salaryapp.domain.model.user.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-10T22:08:39+0300",
    comments = "version: 1.6.0, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class UserListMapperImpl implements UserListMapper {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserDtoOutput> toDtoList(List<User> users) {
        if ( users == null ) {
            return null;
        }

        List<UserDtoOutput> list = new ArrayList<UserDtoOutput>( users.size() );
        for ( User user : users ) {
            list.add( userMapper.toDto( user ) );
        }

        return list;
    }

    @Override
    public List<User> toEntityList(List<UserDtoInput> userDtoInputs) {
        if ( userDtoInputs == null ) {
            return null;
        }

        List<User> list = new ArrayList<User>( userDtoInputs.size() );
        for ( UserDtoInput userDtoInput : userDtoInputs ) {
            list.add( userMapper.toEntity( userDtoInput ) );
        }

        return list;
    }
}
