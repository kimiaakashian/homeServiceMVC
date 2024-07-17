package ir.home_service.model;

import ir.home_service.model.enums.ExpertStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class Expert extends Users{
    @Enumerated(EnumType.STRING)
    private ExpertStatus expertStatus;
    private Double expertCredit;
    private Double averageScore;
    private byte[] picture;
    private String fieldOfExpertise;
}
