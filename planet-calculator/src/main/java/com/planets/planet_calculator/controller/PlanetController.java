package com.planets.planet_calculator.controller;

import com.planets.planet_calculator.model.entity.Calculation;
import com.planets.planet_calculator.model.entity.Planet;
import com.planets.planet_calculator.repository.PlanetRepository;
import com.planets.planet_calculator.service.CalculatorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/planets")
public class PlanetController {

    private final PlanetRepository planetRepository;
    private final CalculatorService calculatorService;

    public PlanetController(PlanetRepository planetRepository,
                            CalculatorService calculatorService) {
        this.planetRepository = planetRepository;
        this.calculatorService = calculatorService;
    }

    @GetMapping
    public String showForm(Model model) {
        List<Planet> planets = planetRepository.findAll();
        model.addAttribute("planets", planets);
        model.addAttribute("calculation", null);
        return "index";
    }

    @PostMapping
    public String calculate(
            @RequestParam Long planetId,
            @RequestParam String dateTime,
            Model model) {

        List<Planet> planets = planetRepository.findAll();
        model.addAttribute("planets", planets);

        try {
            LocalDateTime dt = LocalDateTime.parse(dateTime);
            Calculation calculation = calculatorService.calculatePosition(planetId, dt);
            model.addAttribute("calculation", calculation);

            double distance = Math.sqrt(
                    calculation.getXCoordinate() * calculation.getXCoordinate() +
                            calculation.getYCoordinate() * calculation.getYCoordinate() +
                            calculation.getZCoordinate() * calculation.getZCoordinate());
            model.addAttribute("distance", distance);

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        return "index";
    }

    @GetMapping("/online")
    public String showOnline(Model model) {
        return "online";
    }

    @PostMapping("/online")
    public String postOnline() {
        return "online";
    }
}