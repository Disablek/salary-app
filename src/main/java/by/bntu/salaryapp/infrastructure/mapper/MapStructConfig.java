package by.bntu.salaryapp.infrastructure.mapper;

import org.mapstruct.Builder;
import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

@MapperConfig(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR,
        builder = @Builder(disableBuilder = true))
public interface MapStructConfig {
}
