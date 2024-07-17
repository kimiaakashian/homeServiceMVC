package ir.home_service.repository;


import ir.home_service.model.ExpertSuggestion;
import ir.home_service.model.Orders;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpertSuggestionRepository extends JpaRepository<ExpertSuggestion,Integer> {

    List<ExpertSuggestion> findByOrders(Orders orders, Sort sort);


    Optional<ExpertSuggestion> findById(Integer id);


}
