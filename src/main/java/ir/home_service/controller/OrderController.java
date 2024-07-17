package ir.home_service.controller;

import ir.home_service.dto.order.OrderSaveRequest;
import ir.home_service.dto.order.OrderSaveResponse;
import ir.home_service.dto.order.OrderUpdateRequest;
import ir.home_service.dto.order.OrderUpdateResponse;
import ir.home_service.dto.subService.SubServiceUpdateRequest;
import ir.home_service.dto.subService.SubServiceUpdateResponse;
import ir.home_service.mapper.OrderMapper;
import ir.home_service.mapper.SubServiceMapper;
import ir.home_service.model.Orders;
import ir.home_service.model.SubService;
import ir.home_service.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
public class OrderController {

    private final OrderService orderService;


    @PostMapping("/sendOrder/{customerId}/{subServiceId}")
    public ResponseEntity<OrderSaveResponse> sendOrder(@Valid @RequestBody OrderSaveRequest request, @PathVariable int customerId, @PathVariable int subServiceId) {
        Orders mappedOrder = OrderMapper.INSTANCE.orderSaveRequestToModel(request);
        Orders savedOrder = orderService.sendOrderByCustomer(mappedOrder,customerId,subServiceId);
        return new ResponseEntity<>(OrderMapper.INSTANCE.modelToOrderSaveResponse(savedOrder),
                HttpStatus.CREATED);
    }



    @PatchMapping("/acceptSuggestion/{suggestionId}")
    public String acceptSuggestions(  @PathVariable int suggestionId) {
       Orders orders= orderService.acceptSuggestionsToOrder(suggestionId);
        return  "پیشنهاد" + suggestionId +  "به سفارش " + orders.getId() + "اضافه گردید";
    }

    @PatchMapping("/startWork/{orderId}")
    public String startWork(@Valid@PathVariable int orderId){
       Orders orders= orderService.startWork(orderId);
       return   "سفارش با ایدی  "+ orderId +"در حالت شروع شده است";

    }

    @PatchMapping("/endWork/{orderId}")
    public String endWork(@Valid@PathVariable int orderId){
        Orders orders= orderService.endOfWork(orderId);
        return   "سفارش با ایدی  "+ orderId +"پایان یافت";

    }


    @PatchMapping("/rateOrder")
    public ResponseEntity<OrderUpdateResponse> rateOrder(@Valid @RequestBody OrderUpdateRequest request) {
        Orders mappedOrder = OrderMapper.INSTANCE.orderUpdateRequestToModel(request);
        Orders savedOrder = orderService.rateOrder(mappedOrder);
        return new ResponseEntity<>(OrderMapper.INSTANCE.modelToOrderUpdateResponse(savedOrder),
                HttpStatus.OK);
    }




}
