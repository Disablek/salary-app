package by.bntu.salaryapp.infrastructure.mapper.user.role;

import by.bntu.salaryapp.application.dto.user.role.RoleDto;
import by.bntu.salaryapp.domain.model.user.Role;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-10T22:08:25+0300",
    comments = "version: 1.6.0, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class RoleListMapperImpl implements RoleListMapper {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<RoleDto> toDtoList(List<Role> role) {
        if ( role == null ) {
            return null;
        }

        List<RoleDto> list = new ArrayList<RoleDto>( role.size() );
        for ( Role role1 : role ) {
            list.add( roleMapper.toDto( role1 ) );
        }

        return list;
    }

    @Override
    public List<Role> toEntityList(List<RoleDto> roleDto) {
        if ( roleDto == null ) {
            return null;
        }

        List<Role> list = new ArrayList<Role>( roleDto.size() );
        for ( RoleDto roleDto1 : roleDto ) {
            list.add( roleMapper.toEntity( roleDto1 ) );
        }

        return list;
    }
}
