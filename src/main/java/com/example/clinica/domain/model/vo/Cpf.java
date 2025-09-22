package com.example.clinica.domain.model.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Pattern;

@Embeddable
public class Cpf {
    @Pattern(regexp = "\\d{11}")
    @Column(name = "cpf", nullable = false, unique = true, length = 11)
    private String value;

    protected Cpf() {}

    public Cpf(String value) { this.value = value; }

    public String getValue() { return value; }
}
