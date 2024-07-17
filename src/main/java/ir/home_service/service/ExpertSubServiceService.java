package ir.home_service.service;

import ir.home_service.exception.InvalidStatusException;
import ir.home_service.exception.NotFoundException;
import ir.home_service.model.Expert;
import ir.home_service.model.ExpertSubService;
import ir.home_service.model.SubService;
import ir.home_service.model.enums.ExpertStatus;
import ir.home_service.repository.ExpertRepository;
import ir.home_service.repository.ExpertSubServiceRepository;
import ir.home_service.repository.SubServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpertSubServiceService {
    private final ExpertSubServiceRepository expertSubServiceRepository;
    private final ExpertRepository expertRepository;
    private final SubServiceRepository subServiceRepository;

    public ExpertSubService addExpertToSubService(Integer expertId, Integer subServiceId) {
        Expert expert = expertRepository.findById(expertId).orElseThrow(() -> new NotFoundException("کاربر با این شناسه موجود نیست"));
        if (expert.getExpertStatus().equals(ExpertStatus.جدید))
            throw new InvalidStatusException("متخصص تایید نشده است");

        SubService subService = subServiceRepository.findById(subServiceId).orElseThrow(() -> new NotFoundException("زیر خدمت با این شناسه موجود نیست"));

        ExpertSubService expertSubService = new ExpertSubService();
        expertSubService.setExperts(expert);
        expertSubService.setSubServices(subService);

        expertSubServiceRepository.save(expertSubService);
        return expertSubService;

    }

    public void removeExpertFromSubService(Integer expertId, Integer subServiceId) {

        ExpertSubService expertSubService = expertSubServiceRepository.findByExperts_IdAndAndSubServices_Id(expertId, subServiceId);

        if (expertSubService != null) {

            expertSubServiceRepository.delete(expertSubService);

        }

    }

}
