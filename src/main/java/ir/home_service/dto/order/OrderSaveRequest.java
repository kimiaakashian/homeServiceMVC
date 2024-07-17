package ir.home_service.dto.order;

public record OrderSaveRequest (double customerSuggestedPrice,
                                String orderDescription,
                                String time,
                                String Date,
                                String address){
}
