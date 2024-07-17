package ir.home_service.mapper;

import ir.home_service.dto.expert.ExpertSaveRequest;
import ir.home_service.dto.expert.ExpertSaveResponse;
import ir.home_service.dto.order.OrderSaveRequest;
import ir.home_service.dto.order.OrderSaveResponse;
import ir.home_service.dto.order.OrderUpdateRequest;
import ir.home_service.dto.order.OrderUpdateResponse;
import ir.home_service.dto.subService.SubServiceUpdateRequest;
import ir.home_service.dto.subService.SubServiceUpdateResponse;
import ir.home_service.model.Expert;
import ir.home_service.model.Orders;
import ir.home_service.model.SubService;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class );


    Orders orderSaveRequestToModel(OrderSaveRequest request);
    OrderSaveResponse modelToOrderSaveResponse(Orders orders);

    Orders orderUpdateRequestToModel(OrderUpdateRequest request);
    OrderUpdateResponse modelToOrderUpdateResponse(Orders orders);

}
