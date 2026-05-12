package by.bntu.salaryapp.infrastructure.mapper.employee.qualification;

import by.bntu.salaryapp.application.dto.employee.qualification.QualificationDto;
import by.bntu.salaryapp.domain.model.employee.Qualification;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-10T13:02:19+0300",
    comments = "version: 1.6.0, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class QualificationListMapperImpl implements QualificationListMapper {

    @Autowired
    private QualificationMapper qualificationMapper;

    @Override
    public List<QualificationDto> toDtoList(List<Qualification> qualifications) {
        if ( qualifications == null ) {
            return null;
        }

        List<QualificationDto> list = new ArrayList<QualificationDto>( qualifications.size() );
        for ( Qualification qualification : qualifications ) {
            list.add( qualificationMapper.toDto( qualification ) );
        }

        return list;
    }

    @Override
    public List<Qualification> toEntityList(List<QualificationDto> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<Qualification> list = new ArrayList<Qualification>( dtos.size() );
        for ( QualificationDto qualificationDto : dtos ) {
            list.add( qualificationMapper.toEntity( qualificationDto ) );
        }

        return list;
    }
}
