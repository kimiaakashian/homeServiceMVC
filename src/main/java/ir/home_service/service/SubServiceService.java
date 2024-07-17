package ir.home_service.service;


import ir.home_service.exception.InformationDuplicateException;
import ir.home_service.exception.InvalidBasePriceException;
import ir.home_service.exception.NotFoundException;
import ir.home_service.mapper.SubServiceMapper;
import ir.home_service.model.Customer;
import ir.home_service.model.HomeService;
import ir.home_service.model.SubService;
import ir.home_service.repository.SubServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubServiceService {
    private final SubServiceRepository subServiceRepository;
    private final HomeServiceService homeServiceService;

    public SubService findById(int subServiceId) {
        return subServiceRepository.findById(subServiceId).orElseThrow(
                () -> new NotFoundException("زیر خدمت پیدا نشد"));
    }


    public SubService addSubService(SubService subService,int homeServiceId) {
        subService.setHomeServices(homeServiceService.findById(homeServiceId));
        if (subServiceRepository.findBySubServiceName(subService.getSubServiceName()).isPresent())
            throw new InformationDuplicateException( "نام زیر خدمت تکراری است");
        else
          return   subServiceRepository.save(subService);
    }


    public SubService updateSubService(SubService subService) {
        SubService updateSubService = findById(subService.getId());
        Optional.ofNullable(subService.getBasePrice()).ifPresent(updateSubService::setBasePrice);
        Optional.ofNullable(subService.getDescription()).ifPresent(updateSubService::setDescription);


        return subServiceRepository.save(updateSubService);


    }

    public List<SubService> displayAllSubServicesByHomeServiceId(int homeServiceId) {
        return subServiceRepository.findAllByHomeServices(homeServiceService.findById(homeServiceId));
    }



}
