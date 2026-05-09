package by.bntu.salaryapp.infrastructure.mapper.employee.subject;

import by.bntu.salaryapp.application.dto.employee.subject.SubjectDto;
import by.bntu.salaryapp.domain.model.employee.Subject;
import org.mapstruct.Mapper;
import by.bntu.salaryapp.infrastructure.mapper.MapStructConfig;

import java.util.List;

@Mapper(config = MapStructConfig.class, uses = SubjectMapper.class)
public interface SubjectListMapper {

    List<SubjectDto> toDtoList(List<Subject> subjects);

    List<Subject> toEntityList(List<SubjectDto> dtos);
}