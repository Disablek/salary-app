package by.bntu.salaryapp.infrastructure.mapper.employee.experience;

import by.bntu.salaryapp.application.dto.employee.experience.ExperienceDto;
import by.bntu.salaryapp.domain.model.employee.Experience;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-11T07:51:22+0300",
    comments = "version: 1.6.0, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class ExperienceListMapperImpl implements ExperienceListMapper {

    @Autowired
    private ExperienceMapper experienceMapper;

    @Override
    public List<ExperienceDto> toDtoList(List<Experience> experiences) {
        if ( experiences == null ) {
            return null;
        }

        List<ExperienceDto> list = new ArrayList<ExperienceDto>( experiences.size() );
        for ( Experience experience : experiences ) {
            list.add( experienceMapper.toDto( experience ) );
        }

        return list;
    }

    @Override
    public List<Experience> toEntityList(List<ExperienceDto> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<Experience> list = new ArrayList<Experience>( dtos.size() );
        for ( ExperienceDto experienceDto : dtos ) {
            list.add( experienceMapper.toEntity( experienceDto ) );
        }

        return list;
    }
}
