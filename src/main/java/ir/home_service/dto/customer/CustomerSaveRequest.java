package ir.home_service.dto.customer;

import ir.home_service.dto.user.UserSaveRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record CustomerSaveRequest(
        String firstName,

        String lastName,
        String email,

        String password,
        double customerCredit
        ) {
}
