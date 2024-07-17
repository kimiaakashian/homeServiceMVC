package ir.home_service.mapper;

import ir.home_service.dto.customer.CustomerSaveRequest;
import ir.home_service.dto.customer.CustomerSaveResponse;
import ir.home_service.dto.subService.SubServiceSaveRequest;
import ir.home_service.dto.subService.SubServiceSaveResponse;
import ir.home_service.dto.subService.SubServiceUpdateRequest;
import ir.home_service.dto.subService.SubServiceUpdateResponse;
import ir.home_service.model.Customer;
import ir.home_service.model.SubService;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SubServiceMapper {

    SubServiceMapper INSTANCE = Mappers.getMapper(SubServiceMapper.class );


    SubService subServiceSaveRequestToModel(SubServiceSaveRequest request);
    SubServiceSaveResponse modelToSubServiceSaveResponse(SubService subService);
    SubService subServiceUpdateRequestToModel(SubServiceUpdateRequest request);
    SubServiceUpdateResponse modelToSubServiceUpdateResponse(SubService subService);
}
