package ir.home_service.dto.subService;

public record SubServiceUpdateResponse(Integer id,
                                       String subServiceName,
                                       double basePrice,
                                       String description
                                       ) {
}
