package by.bntu.salaryapp.infrastructure.mapper.employee.subject;

import by.bntu.salaryapp.application.dto.employee.subject.SubjectDto;
import by.bntu.salaryapp.domain.model.employee.Subject;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-10T22:08:06+0300",
    comments = "version: 1.6.0, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class SubjectListMapperImpl implements SubjectListMapper {

    @Autowired
    private SubjectMapper subjectMapper;

    @Override
    public List<SubjectDto> toDtoList(List<Subject> subjects) {
        if ( subjects == null ) {
            return null;
        }

        List<SubjectDto> list = new ArrayList<SubjectDto>( subjects.size() );
        for ( Subject subject : subjects ) {
            list.add( subjectMapper.toDto( subject ) );
        }

        return list;
    }

    @Override
    public List<Subject> toEntityList(List<SubjectDto> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<Subject> list = new ArrayList<Subject>( dtos.size() );
        for ( SubjectDto subjectDto : dtos ) {
            list.add( subjectMapper.toEntity( subjectDto ) );
        }

        return list;
    }
}
