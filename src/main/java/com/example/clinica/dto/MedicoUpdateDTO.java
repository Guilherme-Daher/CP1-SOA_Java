package com.example.clinica.dto;

import jakarta.validation.constraints.Pattern;

public record MedicoUpdateDTO(
        String nome,
        @Pattern(regexp = "\\d{4,6}") String crm
) {}