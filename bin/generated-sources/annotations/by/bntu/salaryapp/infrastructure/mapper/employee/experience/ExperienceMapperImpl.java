package by.bntu.salaryapp.infrastructure.mapper.employee.experience;

import by.bntu.salaryapp.application.dto.employee.experience.ExperienceDto;
import by.bntu.salaryapp.domain.model.employee.Experience;
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
public class ExperienceMapperImpl implements ExperienceMapper {

    @Override
    public ExperienceDto toDto(Experience experience) {
        if ( experience == null ) {
            return null;
        }

        ExperienceDto experienceDto = new ExperienceDto();

        experienceDto.setCreatedBy( experienceCreatedById( experience ) );
        experienceDto.setUpdatedBy( experienceUpdatedById( experience ) );
        experienceDto.setCreatedAt( experience.getCreatedDate() );
        experienceDto.setId( experience.getId() );
        experienceDto.setTitle( experience.getTitle() );
        experienceDto.setUpdatedAt( experience.getUpdatedAt() );

        return experienceDto;
    }

    @Override
    public Experience toEntity(ExperienceDto dto) {
        if ( dto == null ) {
            return null;
        }

        Experience experience = new Experience();

        experience.setTitle( dto.getTitle() );

        return experience;
    }

    @Override
    public void updateFromDto(ExperienceDto dto, Experience entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getTitle() != null ) {
            entity.setTitle( dto.getTitle() );
        }
    }

    private UUID experienceCreatedById(Experience experience) {
        User createdBy = experience.getCreatedBy();
        if ( createdBy == null ) {
            return null;
        }
        return createdBy.getId();
    }

    private UUID experienceUpdatedById(Experience experience) {
        User updatedBy = experience.getUpdatedBy();
        if ( updatedBy == null ) {
            return null;
        }
        return updatedBy.getId();
    }
}
