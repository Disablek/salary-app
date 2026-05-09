package by.bntu.salaryapp.infrastructure.mapper.employee.qualification;

import by.bntu.salaryapp.infrastructure.mapper.MapStructConfig;
import by.bntu.salaryapp.application.dto.employee.qualification.QualificationDto;
import by.bntu.salaryapp.domain.model.employee.Qualification;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapStructConfig.class, uses = QualificationMapper.class)
public interface QualificationListMapper {

    List<QualificationDto> toDtoList(List<Qualification> qualifications);

    List<Qualification> toEntityList(List<QualificationDto> dtos);
}
