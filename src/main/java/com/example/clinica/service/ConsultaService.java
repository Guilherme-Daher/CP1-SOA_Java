package com.example.clinica.service;

import com.example.clinica.domain.model.Consulta;
import com.example.clinica.domain.model.Medico;
import com.example.clinica.domain.model.Paciente;
import com.example.clinica.domain.model.StatusConsulta;
import com.example.clinica.dto.ConsultaCreateDTO;
import com.example.clinica.dto.ConsultaResponseDTO;
import com.example.clinica.repository.ConsultaRepository;
import com.example.clinica.repository.MedicoRepository;
import com.example.clinica.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;

    @Transactional
    public ConsultaResponseDTO agendar(ConsultaCreateDTO dto) {
        Paciente paciente = pacienteRepository.findById(dto.pacienteId())
                .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado"));

        Medico medico = medicoRepository.findById(dto.medicoId())
                .orElseThrow(() -> new IllegalArgumentException("Médico não encontrado"));

        if (consultaRepository.existsByMedicoIdAndDataHora(medico.getId(), dto.dataHora())) {
            throw new IllegalArgumentException("Médico já possui uma consulta agendada para este horário");
        }

        Consulta consulta = Consulta.builder()
                .paciente(paciente)
                .medico(medico)
                .dataHora(dto.dataHora())
                .status(StatusConsulta.AGENDADA)
                .build();

        consulta = consultaRepository.save(consulta);

        return new ConsultaResponseDTO(
                consulta.getId(),
                consulta.getPaciente().getId(),
                consulta.getMedico().getId(),
                consulta.getDataHora(),
                consulta.getStatus()
        );
    }

    @Transactional
    public void cancelar(Long id) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Consulta não encontrada"));

        consulta.setStatus(StatusConsulta.CANCELADA);
        consultaRepository.save(consulta);
    }

    @Transactional(readOnly = true)
    public Page<ConsultaResponseDTO> listar(Pageable pageable) {
        return consultaRepository.findAll(pageable)
                .map(c -> new ConsultaResponseDTO(
                        c.getId(),
                        c.getPaciente().getId(),
                        c.getMedico().getId(),
                        c.getDataHora(),
                        c.getStatus()
                ));
    }

    @Transactional(readOnly = true)
    public Page<ConsultaResponseDTO> listarPorMedico(Long medicoId, Pageable pageable) {
        return consultaRepository.findByMedicoId(medicoId, pageable)
                .map(c -> new ConsultaResponseDTO(
                        c.getId(),
                        c.getPaciente().getId(),
                        c.getMedico().getId(),
                        c.getDataHora(),
                        c.getStatus()
                ));
    }
}