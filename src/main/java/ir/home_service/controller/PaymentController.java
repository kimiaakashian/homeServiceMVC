package ir.home_service.controller;

import ir.home_service.dto.customer.CustomerSaveRequest;
import ir.home_service.dto.customer.CustomerSaveResponse;
import ir.home_service.dto.pay.PaymentSaveRequest;
import ir.home_service.exception.NotFoundException;
import ir.home_service.mapper.CustomerMapper;
import ir.home_service.mapper.OrderMapper;
import ir.home_service.model.Customer;
import ir.home_service.model.Expert;
import ir.home_service.service.CustomerService;
import ir.home_service.service.ExpertService;
import ir.home_service.service.OrderService;
import ir.home_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final OrderService orderService;


    @PostMapping("/payment")
    public ResponseEntity<String> register(@Valid @RequestBody PaymentSaveRequest request) {
        String msg = "";
        try {
            int orderId = Integer.valueOf(request.orderId());
            String cardNumber = request.cardNumber();
            String date = request.year() + "/" + request.month() + "/1";
            orderService.onlinePayment(orderId, cardNumber, date);
            msg = "عملیات با موفقیت انجام شد";
        } catch (NotFoundException ex) {
            msg = "سفارش یافت نشد";
        }catch (Exception ex) {
            msg = "مشکلی پیش آمده است";
        }

        return new ResponseEntity<>((msg),
                HttpStatus.OK) ;
    }

    @PostMapping("/paymentFromCredit/{orderId}")
    public String paymentFromCredit(@PathVariable int orderId) {
        orderService.paymentFromCredit(orderId);
        return "payment!";
    }


}
