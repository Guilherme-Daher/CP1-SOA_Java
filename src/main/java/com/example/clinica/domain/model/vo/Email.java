package com.example.clinica.domain.model.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;

@Embeddable
public class Email {
    @jakarta.validation.constraints.Email @NotBlank
    @Column(name = "email", nullable = false)
    private String value;

    protected Email() {} // JPA

    public Email(String value) { this.value = value; }

    public String getValue() { return value; }
}
