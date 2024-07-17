package ir.home_service.repository;


import ir.home_service.model.HomeService;
import ir.home_service.model.SubService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface SubServiceRepository extends JpaRepository<SubService,Integer> {

    Optional<SubService> findBySubServiceName(String name);
    Optional<SubService> findById(Integer id);
    List<SubService> findAllByHomeServices(HomeService homeService);

}
