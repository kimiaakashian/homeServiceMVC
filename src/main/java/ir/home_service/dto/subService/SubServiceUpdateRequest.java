package ir.home_service.dto.subService;

public record SubServiceUpdateRequest(Integer id,
                                      double basePrice,
                                      String description
                                      ) {
}
