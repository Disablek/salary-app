package by.bntu.salaryapp.infrastructure.mapper.employee.subject;

import by.bntu.salaryapp.application.dto.employee.subject.SubjectDto;
import by.bntu.salaryapp.domain.model.employee.Subject;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-10T22:08:06+0300",
    comments = "version: 1.6.0, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class SubjectMapperImpl implements SubjectMapper {

    @Override
    public SubjectDto toDto(Subject subject) {
        if ( subject == null ) {
            return null;
        }

        SubjectDto subjectDto = new SubjectDto();

        subjectDto.setId( subject.getId() );
        subjectDto.setTitle( subject.getTitle() );

        return subjectDto;
    }

    @Override
    public Subject toEntity(SubjectDto dto) {
        if ( dto == null ) {
            return null;
        }

        Subject subject = new Subject();

        subject.setId( dto.getId() );
        subject.setTitle( dto.getTitle() );

        return subject;
    }

    @Override
    public void updateFromDto(SubjectDto dto, Subject entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getTitle() != null ) {
            entity.setTitle( dto.getTitle() );
        }
    }
}
