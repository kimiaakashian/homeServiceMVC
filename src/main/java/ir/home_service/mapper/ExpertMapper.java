package ir.home_service.mapper;

import ir.home_service.dto.expert.ExpertSaveRequest;

import ir.home_service.dto.expert.ExpertSaveResponse;
import ir.home_service.model.Expert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface ExpertMapper {
    ExpertMapper INSTANCE = Mappers.getMapper(ExpertMapper.class );


    Expert expertSaveRequestToModel(ExpertSaveRequest request);
    ExpertSaveResponse modelToExpertSaveResponse(Expert expert);

}
