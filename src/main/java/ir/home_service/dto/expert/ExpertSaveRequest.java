package ir.home_service.dto.expert;

public record ExpertSaveRequest(String firstName,

                                String lastName,
                                String email,

                                String password,
                                double expertCredit,
                                String pictureAddress,
                                String fieldOfExpertise) {
}
