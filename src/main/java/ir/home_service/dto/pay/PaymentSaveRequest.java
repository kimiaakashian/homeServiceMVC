package ir.home_service.dto.pay;

import jakarta.validation.constraints.Size;

public record PaymentSaveRequest(
        int orderId,
        @Size(min = 16, max = 16, message = "card number must be 16 characters long.")
        String cardNumber,
        String cvv,
        String year,
        String month,
        String password) {
}
