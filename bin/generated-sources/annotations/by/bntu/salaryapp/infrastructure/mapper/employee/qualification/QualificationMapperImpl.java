package by.bntu.salaryapp.infrastructure.mapper.employee.qualification;

import by.bntu.salaryapp.application.dto.employee.qualification.QualificationDto;
import by.bntu.salaryapp.domain.model.employee.Qualification;
import by.bntu.salaryapp.domain.model.user.User;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-10T13:02:19+0300",
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

        qualificationDto.setCreatedBy( qualificationCreatedById( qualification ) );
        qualificationDto.setUpdatedBy( qualificationUpdatedById( qualification ) );
        qualificationDto.setCreatedAt( qualification.getCreatedDate() );
        qualificationDto.setId( qualification.getId() );
        qualificationDto.setTitle( qualification.getTitle() );
        qualificationDto.setUpdatedAt( qualification.getUpdatedAt() );

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

    private UUID qualificationCreatedById(Qualification qualification) {
        User createdBy = qualification.getCreatedBy();
        if ( createdBy == null ) {
            return null;
        }
        return createdBy.getId();
    }

    private UUID qualificationUpdatedById(Qualification qualification) {
        User updatedBy = qualification.getUpdatedBy();
        if ( updatedBy == null ) {
            return null;
        }
        return updatedBy.getId();
    }
}
