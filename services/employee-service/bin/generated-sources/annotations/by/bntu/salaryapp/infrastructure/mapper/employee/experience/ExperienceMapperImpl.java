package by.bntu.salaryapp.infrastructure.mapper.employee.experience;

import by.bntu.salaryapp.application.dto.employee.experience.ExperienceDto;
import by.bntu.salaryapp.domain.model.employee.Experience;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-11T07:51:22+0300",
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

        experienceDto.setId( experience.getId() );
        experienceDto.setTitle( experience.getTitle() );
        experienceDto.setUpdatedAt( experience.updatedAt );

        experienceDto.setCreatedBy( experience.getCreatedBy() != null ? experience.getCreatedBy().getId() : null );
        experienceDto.setUpdatedBy( experience.getUpdatedBy() != null ? experience.getUpdatedBy().getId() : null );
        experienceDto.setCreatedAt( experience.getCreatedDate() );

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
}
