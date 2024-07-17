package ir.home_service.mapper;

import ir.home_service.dto.customer.CustomerSaveRequest;
import ir.home_service.dto.customer.CustomerSaveResponse;
import ir.home_service.dto.expert.ExpertSaveRequest;
import ir.home_service.dto.expert.ExpertSaveResponse;
import ir.home_service.model.Customer;
import ir.home_service.model.Expert;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class );


    Customer customerSaveRequestToModel(CustomerSaveRequest request);
    CustomerSaveResponse modelToCustomerSaveResponse(Customer customer);
}
