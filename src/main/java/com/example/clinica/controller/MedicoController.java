package com.example.clinica.controller;

import com.example.clinica.dto.MedicoCreateDTO;
import com.example.clinica.dto.MedicoResponseDTO;
import com.example.clinica.dto.MedicoUpdateDTO;
import com.example.clinica.service.MedicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/medicos")
@RequiredArgsConstructor
public class MedicoController {

    private final MedicoService medicoService;

    @Operation(summary = "Cria um médico")
    @ApiResponse(responseCode = "201", description = "Criado")
    @PostMapping
    public ResponseEntity<MedicoResponseDTO> criar(@Valid @RequestBody MedicoCreateDTO medicoCreateDTO, UriComponentsBuilder uriBuilder) {
        MedicoResponseDTO medico = medicoService.criar(medicoCreateDTO);
        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.id()).toUri();
        return ResponseEntity.created(uri).body(medico);
    }

    @Operation(summary = "Lista médicos com paginação")
    @GetMapping
    public Page<MedicoResponseDTO> listar(@ParameterObject Pageable pageable) {
        return medicoService.listar(pageable);
    }

    @Operation(summary = "Busca um médico por ID")
    @GetMapping("/{id}")
    public ResponseEntity<MedicoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(medicoService.buscarPorId(id));
    }

    @Operation(summary = "Atualiza um médico")
    @PutMapping("/{id}")
    public ResponseEntity<MedicoResponseDTO> atualizar(@PathVariable Long id, @RequestBody MedicoUpdateDTO medicoUpdateDTO) {
        return ResponseEntity.ok(medicoService.atualizar(id, medicoUpdateDTO));
    }

    @Operation(summary = "Remove um médico")
    @ApiResponse(responseCode = "204", description = "Removido")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        medicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}