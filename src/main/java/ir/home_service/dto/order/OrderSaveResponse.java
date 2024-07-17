package ir.home_service.dto.order;

import ir.home_service.dto.subService.SubServiceSaveResponse;
import ir.home_service.model.enums.OrderStatus;

public record OrderSaveResponse(int id,
                                int customerId,
                                SubServiceSaveResponse subService,
                                double customerSuggestedPrice,
                                String orderDescription,
                                OrderStatus orderStatus,
                                String time,
                                String Date,
                                String address
                                ) {
}
