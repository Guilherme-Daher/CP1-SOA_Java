package com.example.clinica.controller;

import com.example.clinica.dto.PacienteCreateDTO;
import com.example.clinica.dto.PacienteResponseDTO;
import com.example.clinica.service.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService service;

    @Operation(summary = "Cria um paciente")
    @ApiResponse(responseCode = "201", description = "Criado")
    @PostMapping
    public ResponseEntity<Void> criar(@Valid @RequestBody PacienteCreateDTO dto) {
        Long id = service.criar(dto);
        return ResponseEntity.created(URI.create("/pacientes/" + id)).build();
    }

    @Operation(summary = "Lista pacientes com paginação")
    @GetMapping
    public Page<PacienteResponseDTO> listar(@ParameterObject Pageable pageable) {
        return service.listar(pageable);
    }
}
