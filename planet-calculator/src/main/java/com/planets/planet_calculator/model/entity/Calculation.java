package com.planets.planet_calculator.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Расчёты")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Calculation {

    @Id
    @Column(name = "Id_расчёта")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_планеты", nullable = false)
    private Planet planet;

    @Column(name = "Дата_вычисления", nullable = false)
    private LocalDateTime calculationDate;

    @Column(name = "Координата_x", nullable = false)
    private Double xCoordinate;

    @Column(name = "Координата_y", nullable = false)
    private Double yCoordinate;

    @Column(name = "Координата_z", nullable = false)
    private Double zCoordinate;

    @Column(name = "Время_создания", nullable = false)
    private LocalDateTime createdAt;
}