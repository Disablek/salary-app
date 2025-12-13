package by.bntu.salaryapp.infrastructure.mapper.employee.subject;

import by.bntu.salaryapp.application.dto.employee.subject.SubjectDto;
import by.bntu.salaryapp.domain.model.employee.Subject;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = SubjectMapper.class)
public interface SubjectListMapper {

    List<SubjectDto> toDtoList(List<Subject> subjects);

    List<Subject> toEntityList(List<SubjectDto> dtos);
}