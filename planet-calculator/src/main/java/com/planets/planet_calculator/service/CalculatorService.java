package com.planets.planet_calculator.service;

import com.planets.planet_calculator.model.entity.Calculation;
import com.planets.planet_calculator.model.entity.Planet;
import com.planets.planet_calculator.repository.CalculationRepository;
import com.planets.planet_calculator.repository.PlanetRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CalculatorService {

    private final PlanetRepository planetRepository;
    private final CalculationRepository calculationRepository;

    public CalculatorService(PlanetRepository planetRepository,
                             CalculationRepository calculationRepository) {
        this.planetRepository = planetRepository;
        this.calculationRepository = calculationRepository;
    }

    public Calculation calculatePosition(Long planetId, LocalDateTime dateTime) {
        Planet planet = planetRepository.findById(planetId)
                .orElseThrow(() -> new RuntimeException("Планета не найдена"));

        double meanAnomaly = calculateMeanAnomaly(planet, dateTime);

        double eccentricAnomaly = solveKepler(meanAnomaly, planet.getEccentricity());

        double[] coordinates = calculateCoordinates(planet, eccentricAnomaly);

        Calculation calculation = new Calculation();
        calculation.setPlanet(planet);
        calculation.setCalculationDate(dateTime);
        calculation.setXCoordinate(coordinates[0]);
        calculation.setYCoordinate(coordinates[1]);
        calculation.setZCoordinate(coordinates[2]);
        calculation.setCreatedAt(LocalDateTime.now());

        return calculationRepository.save(calculation);
    }

    private double calculateMeanAnomaly(Planet planet, LocalDateTime dateTime) {
        double period = Math.pow(planet.getSemiMajorAxis(), 1.5);
        double meanMotion = 2 * Math.PI / period;
        LocalDateTime epoch = LocalDateTime.of(2000, 1, 1, 12, 0, 0);
        long days = java.time.Duration.between(epoch, dateTime).toDays(); //эпохи в года
        double years = days / 365.25;
        return meanMotion * years;
    }

    private double solveKepler(double meanAnomaly, double eccentricity) {
        double E = meanAnomaly;
        double delta;

        for (int i = 0; i < 10; i++) {
            delta = E - eccentricity * Math.sin(E) - meanAnomaly;
            E = E - delta / (1 - eccentricity * Math.cos(E));

            if (Math.abs(delta) < 1e-10) {
                break;
            }
        }

        return E;
    }

    private double[] calculateCoordinates(Planet planet, double eccentricAnomaly) {
        double a = planet.getSemiMajorAxis();
        double e = planet.getEccentricity();

        double xOrbital = a * (Math.cos(eccentricAnomaly) - e);
        double yOrbital = a * Math.sqrt(1 - e * e) * Math.sin(eccentricAnomaly);

        double incl = Math.toRadians(planet.getInclination());
        double omega = Math.toRadians(planet.getPerihelionArgument());
        double Omega = Math.toRadians(planet.getNodeLongitude());

        double x1 = xOrbital * Math.cos(omega) - yOrbital * Math.sin(omega);
        double y1 = xOrbital * Math.sin(omega) + yOrbital * Math.cos(omega);

        double x2 = x1;
        double y2 = y1 * Math.cos(incl);
        double z2 = y1 * Math.sin(incl);

        double x = x2 * Math.cos(Omega) - y2 * Math.sin(Omega);
        double y = x2 * Math.sin(Omega) + y2 * Math.cos(Omega);
        double z = z2;

        return new double[]{x, y, z};
    }
}