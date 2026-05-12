package by.bntu.salaryapp.infrastructure.mapper.dataTable.coefficient.coefficient;

import by.bntu.salaryapp.application.dto.dataTable.coefficient.coefficient.CoefficientDto;
import by.bntu.salaryapp.domain.model.dataTable.coefficient.Coefficient;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-11T07:51:55+0300",
    comments = "version: 1.6.0, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class CoefficientListMapperImpl implements CoefficientListMapper {

    @Autowired
    private CoefficientMapper coefficientMapper;

    @Override
    public List<CoefficientDto> toDtoList(List<Coefficient> coefficients) {
        if ( coefficients == null ) {
            return null;
        }

        List<CoefficientDto> list = new ArrayList<CoefficientDto>( coefficients.size() );
        for ( Coefficient coefficient : coefficients ) {
            list.add( coefficientMapper.toDto( coefficient ) );
        }

        return list;
    }

    @Override
    public List<Coefficient> toEntityList(List<CoefficientDto> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<Coefficient> list = new ArrayList<Coefficient>( dtos.size() );
        for ( CoefficientDto coefficientDto : dtos ) {
            list.add( coefficientMapper.toEntity( coefficientDto ) );
        }

        return list;
    }
}
