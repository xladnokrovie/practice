package com.planets.planet_calculator.repository;

import com.planets.planet_calculator.model.entity.Planet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanetRepository extends JpaRepository<Planet, Long> {
    Planet findByName(String name);
}