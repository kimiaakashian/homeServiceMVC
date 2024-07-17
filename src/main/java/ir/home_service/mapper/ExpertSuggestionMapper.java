package ir.home_service.mapper;

import ir.home_service.dto.expertSuggestion.ExpertSuggestionSaveRequest;
import ir.home_service.dto.expertSuggestion.ExpertSuggestionSaveResponse;
import ir.home_service.model.ExpertSuggestion;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface ExpertSuggestionMapper {
    ExpertSuggestionMapper INSTANCE = Mappers.getMapper(ExpertSuggestionMapper.class );


    ExpertSuggestion expertSuggestionSaveRequestToModel(ExpertSuggestionSaveRequest request);
    ExpertSuggestionSaveResponse modelToExpertSuggestionSaveResponse(ExpertSuggestion expertSuggestion);
}
