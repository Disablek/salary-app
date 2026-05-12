package by.bntu.salaryapp.infrastructure.mapper.dataTable.coefficient.coefficient;

import by.bntu.salaryapp.application.dto.dataTable.coefficient.coefficient.CoefficientDto;
import by.bntu.salaryapp.domain.model.dataTable.Column;
import by.bntu.salaryapp.domain.model.dataTable.coefficient.Coefficient;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-11T07:55:03+0300",
    comments = "version: 1.6.0, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class CoefficientMapperImpl implements CoefficientMapper {

    @Override
    public CoefficientDto toDto(Coefficient coefficient) {
        if ( coefficient == null ) {
            return null;
        }

        CoefficientDto coefficientDto = new CoefficientDto();

        coefficientDto.setTargetColumnId( coefficientTargetColumnId( coefficient ) );
        coefficientDto.setSourceColumnId( coefficientSourceColumnId( coefficient ) );
        coefficientDto.setBaseColumnId( coefficientBaseColumnId( coefficient ) );
        coefficientDto.setId( coefficient.getId() );
        coefficientDto.setTitle( coefficient.getTitle() );
        coefficientDto.setDescription( coefficient.getDescription() );
        coefficientDto.setIsActive( coefficient.getIsActive() );
        coefficientDto.setType( coefficient.getType() );
        coefficientDto.setUpdatedAt( coefficient.getUpdatedAt() );

        coefficientDto.setCreatedBy( coefficient.getCreatedBy() != null ? coefficient.getCreatedBy().getId() : null );
        coefficientDto.setUpdatedBy( coefficient.getUpdatedBy() != null ? coefficient.getUpdatedBy().getId() : null );
        coefficientDto.setCreatedAt( coefficient.getCreatedDate() );
        coefficientDto.setCoefficientRulesIds( mapRulesToIds(coefficient.getCoefficientRules()) );
        coefficientDto.setSummationColumnsIds( mapColumnsToIds(coefficient.getSummationColumns()) );

        return coefficientDto;
    }

    @Override
    public Coefficient toEntity(CoefficientDto dto) {
        if ( dto == null ) {
            return null;
        }

        Coefficient coefficient = new Coefficient();

        coefficient.setDescription( dto.getDescription() );
        coefficient.setIsActive( dto.getIsActive() );
        coefficient.setTitle( dto.getTitle() );
        coefficient.setType( dto.getType() );

        return coefficient;
    }

    @Override
    public void updateFromDto(CoefficientDto dto, Coefficient entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
        if ( dto.getIsActive() != null ) {
            entity.setIsActive( dto.getIsActive() );
        }
        if ( dto.getTitle() != null ) {
            entity.setTitle( dto.getTitle() );
        }
        if ( dto.getType() != null ) {
            entity.setType( dto.getType() );
        }
    }

    private UUID coefficientTargetColumnId(Coefficient coefficient) {
        Column targetColumn = coefficient.getTargetColumn();
        if ( targetColumn == null ) {
            return null;
        }
        return targetColumn.getId();
    }

    private UUID coefficientSourceColumnId(Coefficient coefficient) {
        Column sourceColumn = coefficient.getSourceColumn();
        if ( sourceColumn == null ) {
            return null;
        }
        return sourceColumn.getId();
    }

    private UUID coefficientBaseColumnId(Coefficient coefficient) {
        Column baseColumn = coefficient.getBaseColumn();
        if ( baseColumn == null ) {
            return null;
        }
        return baseColumn.getId();
    }
}
