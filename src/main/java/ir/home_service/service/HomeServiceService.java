package ir.home_service.service;

import ir.home_service.exception.InformationDuplicateException;
import ir.home_service.exception.NotFoundException;
import ir.home_service.model.HomeService;
import ir.home_service.repository.HomeServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeServiceService {
    private final HomeServiceRepository homeServiceRepository;

    public HomeService addHomeService(HomeService homeService) {
        if (homeServiceRepository.findByName(homeService.getName()).isPresent())
            throw new InformationDuplicateException( "نام خدمت تکراری است");
        else
          return   homeServiceRepository.save(homeService);
    }

    public HomeService findById(int homeServiceId){
        return homeServiceRepository.findById(homeServiceId).orElseThrow(() -> new NotFoundException("خدمت یافت نشد"));
    }

    public List<HomeService> findAllHomeService(){
        return homeServiceRepository.findAll();

    }

}
