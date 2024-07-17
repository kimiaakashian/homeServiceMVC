package ir.home_service.dto.subService;

import ir.home_service.dto.homeService.HomeServiceSaveRequest;
import ir.home_service.dto.homeService.HomeServiceSaveResponse;
import ir.home_service.model.HomeService;

public record SubServiceSaveRequest(
                                    String subServiceName,
                                    double basePrice,
                                    String description
                                    ) {
}
