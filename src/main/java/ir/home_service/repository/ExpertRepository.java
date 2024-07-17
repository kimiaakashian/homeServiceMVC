package ir.home_service.repository;

import ir.home_service.model.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface ExpertRepository extends JpaRepository<Expert,Integer> {

    Optional <Expert> findById(Integer id);

}
