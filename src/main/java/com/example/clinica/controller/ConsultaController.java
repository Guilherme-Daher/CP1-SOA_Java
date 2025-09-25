package com.example.clinica.controller;

import com.example.clinica.dto.ConsultaCreateDTO;
import com.example.clinica.dto.ConsultaResponseDTO;
import com.example.clinica.service.ConsultaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/consultas")
@RequiredArgsConstructor
public class ConsultaController {

    private final ConsultaService consultaService;

    @Operation(summary = "Agenda uma consulta")
    @ApiResponse(responseCode = "201", description = "Criado")
    @PostMapping
    public ResponseEntity<ConsultaResponseDTO> agendar(@Valid @RequestBody ConsultaCreateDTO consultaCreateDTO, UriComponentsBuilder uriBuilder) {
        ConsultaResponseDTO consulta = consultaService.agendar(consultaCreateDTO);
        var uri = uriBuilder.path("/consultas/{id}").buildAndExpand(consulta.id()).toUri();
        return ResponseEntity.created(uri).body(consulta);
    }

    @Operation(summary = "Cancela uma consulta")
    @ApiResponse(responseCode = "204", description = "Cancelada")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        consultaService.cancelar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Lista todas as consultas com paginação")
@GetMapping
public ResponseEntity<Page<ConsultaResponseDTO>> listar(Pageable pageable) {
    return ResponseEntity.ok(consultaService.listar(pageable));
}


@Operation(summary = "Lista consultas por médico")
@GetMapping("/medico/{id}")
public ResponseEntity<Page<ConsultaResponseDTO>> listarPorMedico(@PathVariable Long id, Pageable pageable) {
    return ResponseEntity.ok(consultaService.listarPorMedico(id, pageable));
}

}