package com.planets.planet_calculator.repository;

import com.planets.planet_calculator.model.entity.Calculation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CalculationRepository extends JpaRepository<Calculation, Long> {
    List<Calculation> findByPlanetId(Long planetId);
}