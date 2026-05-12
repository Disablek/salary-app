package by.bntu.salaryapp.infrastructure.mapper.dataTable.coefficient.coefficientRule;

import by.bntu.salaryapp.application.dto.dataTable.coefficient.coefficientRule.CoefficientRuleDto;
import by.bntu.salaryapp.domain.model.dataTable.coefficient.CoefficientRule;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-11T07:52:03+0300",
    comments = "version: 1.6.0, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class CoefficientRuleListMapperImpl implements CoefficientRuleListMapper {

    @Autowired
    private CoefficientRuleMapper coefficientRuleMapper;

    @Override
    public List<CoefficientRuleDto> toDtoList(List<CoefficientRule> rules) {
        if ( rules == null ) {
            return null;
        }

        List<CoefficientRuleDto> list = new ArrayList<CoefficientRuleDto>( rules.size() );
        for ( CoefficientRule coefficientRule : rules ) {
            list.add( coefficientRuleMapper.toDto( coefficientRule ) );
        }

        return list;
    }

    @Override
    public List<CoefficientRule> toEntityList(List<CoefficientRuleDto> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<CoefficientRule> list = new ArrayList<CoefficientRule>( dtos.size() );
        for ( CoefficientRuleDto coefficientRuleDto : dtos ) {
            list.add( coefficientRuleMapper.toEntity( coefficientRuleDto ) );
        }

        return list;
    }
}
