package ir.home_service.dto.subService;

import ir.home_service.dto.homeService.HomeServiceSaveResponse;

public record SubServiceSaveResponse(Integer id,
                                     String subServiceName,
                                     double basePrice,
                                     String description,
                                     HomeServiceSaveResponse homeService
                                     ) {
}
