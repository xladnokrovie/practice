package com.planets.planet_calculator.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Планеты")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Planet {

    @Id
    @Column(name = "Id_планеты")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Имя",nullable = false, unique = true)
    private String name;

    @Column(name = "Большая_полуось", nullable = false)
    private Double semiMajorAxis;

    @Column(name = "Эксцентриситет", nullable = false)
    private Double eccentricity;

    @Column(name = "Масса", nullable = false)
    private Double mass;

    @Column(name = "Цвет", nullable = false)
    private String color;

    @Column(name = "Наклонение", nullable = false)
    private Double inclination;

    @Column(name = "Аргумент_перигелия", nullable = false)
    private Double perihelionArgument;

    @Column(name = "Долгота_узла", nullable = false)
    private Double nodeLongitude;
}