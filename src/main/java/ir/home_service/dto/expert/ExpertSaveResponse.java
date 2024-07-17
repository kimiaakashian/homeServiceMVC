package ir.home_service.dto.expert;

public record ExpertSaveResponse (String firstName,

                                  String lastName,
                                  String email,

                                  double expertCredit,
                                  String pictureAddress,
                                  String fieldOfExpertise){
}
