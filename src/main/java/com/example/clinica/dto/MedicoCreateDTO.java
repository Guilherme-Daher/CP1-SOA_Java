package com.example.clinica.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record MedicoCreateDTO(
        @NotBlank String nome,
        @NotBlank @Pattern(regexp = "\\d{4,6}") String crm
) {}