package com.example.clinica.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "consulta")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Consulta {

    @Id
    @SequenceGenerator(name = "consulta_seq", sequenceName = "CONSULTA_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "consulta_seq")
    private Long id;

    @ManyToOne(optional = false) @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @ManyToOne(optional = false) @JoinColumn(name = "medico_id")
    private Medico medico;

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private StatusConsulta status;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;
}
