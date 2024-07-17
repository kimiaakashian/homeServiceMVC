package ir.home_service.dto.customer;

import ir.home_service.dto.user.UserSaveResponse;

public record CustomerSaveResponse (String firstName,

                                    String lastName,
                                    String email,
                                   double Credit){
}
