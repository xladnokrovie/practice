package com.planets.planet_calculator.controller;

import com.planets.planet_calculator.model.entity.Planet;
import com.planets.planet_calculator.repository.PlanetRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/test")
public class TestController {

    private final PlanetRepository planetRepository;

    public TestController(PlanetRepository planetRepository) {
        this.planetRepository = planetRepository;
    }

    @GetMapping
    public List<Planet> getAllPlanets() {
        return planetRepository.findAll();
    }
}