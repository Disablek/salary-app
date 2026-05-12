package by.bntu.salaryapp.infrastructure.mapper.dataTable.coefficient.coefficientRule;

import by.bntu.salaryapp.application.dto.dataTable.coefficient.coefficientRule.CoefficientRuleDto;
import by.bntu.salaryapp.domain.model.dataTable.coefficient.Coefficient;
import by.bntu.salaryapp.domain.model.dataTable.coefficient.CoefficientRule;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-11T07:52:02+0300",
    comments = "version: 1.6.0, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class CoefficientRuleMapperImpl implements CoefficientRuleMapper {

    @Override
    public CoefficientRuleDto toDto(CoefficientRule entity) {
        if ( entity == null ) {
            return null;
        }

        CoefficientRuleDto coefficientRuleDto = new CoefficientRuleDto();

        coefficientRuleDto.setCoefficientId( entityCoefficientId( entity ) );
        coefficientRuleDto.setId( entity.getId() );
        coefficientRuleDto.setMatchValue( entity.getMatchValue() );
        coefficientRuleDto.setMultiplier( entity.getMultiplier() );
        coefficientRuleDto.setUpdatedAt( entity.updatedAt );

        coefficientRuleDto.setCreatedBy( entity.getCreatedBy() != null ? entity.getCreatedBy().getId() : null );
        coefficientRuleDto.setUpdatedBy( entity.getUpdatedBy() != null ? entity.getUpdatedBy().getId() : null );
        coefficientRuleDto.setCreatedAt( entity.getCreatedDate() );

        return coefficientRuleDto;
    }

    @Override
    public CoefficientRule toEntity(CoefficientRuleDto dto) {
        if ( dto == null ) {
            return null;
        }

        CoefficientRule coefficientRule = new CoefficientRule();

        coefficientRule.setMatchValue( dto.getMatchValue() );
        coefficientRule.setMultiplier( dto.getMultiplier() );

        return coefficientRule;
    }

    @Override
    public void updateFromDto(CoefficientRuleDto dto, CoefficientRule entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getMatchValue() != null ) {
            entity.setMatchValue( dto.getMatchValue() );
        }
        if ( dto.getMultiplier() != null ) {
            entity.setMultiplier( dto.getMultiplier() );
        }
    }

    private UUID entityCoefficientId(CoefficientRule coefficientRule) {
        Coefficient coefficient = coefficientRule.getCoefficient();
        if ( coefficient == null ) {
            return null;
        }
        return coefficient.getId();
    }
}
