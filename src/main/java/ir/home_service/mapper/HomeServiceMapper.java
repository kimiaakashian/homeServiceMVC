package ir.home_service.mapper;

import ir.home_service.dto.homeService.HomeServiceSaveResponse;
import ir.home_service.dto.homeService.HomeServiceSaveRequest;
import ir.home_service.model.Expert;
import ir.home_service.model.HomeService;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface HomeServiceMapper {
    HomeServiceMapper INSTANCE = Mappers.getMapper(HomeServiceMapper.class );


    HomeService homeServiceSaveRequestToModel(HomeServiceSaveRequest request);
    HomeServiceSaveResponse modelToHomeServiceSaveResponse(HomeService homeService);
}
