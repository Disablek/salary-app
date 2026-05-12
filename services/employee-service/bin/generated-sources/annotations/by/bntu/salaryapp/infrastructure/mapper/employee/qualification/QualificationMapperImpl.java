package by.bntu.salaryapp.infrastructure.mapper.employee.qualification;

import by.bntu.salaryapp.application.dto.employee.qualification.QualificationDto;
import by.bntu.salaryapp.domain.model.employee.Qualification;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-11T07:51:16+0300",
    comments = "version: 1.6.0, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class QualificationMapperImpl implements QualificationMapper {

    @Override
    public QualificationDto toDto(Qualification qualification) {
        if ( qualification == null ) {
            return null;
        }

        QualificationDto qualificationDto = new QualificationDto();

        qualificationDto.setId( qualification.getId() );
        qualificationDto.setTitle( qualification.getTitle() );
        qualificationDto.setUpdatedAt( qualification.updatedAt );

        qualificationDto.setCreatedBy( qualification.getCreatedBy() != null ? qualification.getCreatedBy().getId() : null );
        qualificationDto.setUpdatedBy( qualification.getUpdatedBy() != null ? qualification.getUpdatedBy().getId() : null );
        qualificationDto.setCreatedAt( qualification.getCreatedDate() );

        return qualificationDto;
    }

    @Override
    public Qualification toEntity(QualificationDto dto) {
        if ( dto == null ) {
            return null;
        }

        Qualification qualification = new Qualification();

        qualification.setTitle( dto.getTitle() );

        return qualification;
    }

    @Override
    public void updateFromDto(QualificationDto dto, Qualification entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getTitle() != null ) {
            entity.setTitle( dto.getTitle() );
        }
    }
}
