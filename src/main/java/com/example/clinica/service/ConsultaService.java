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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;

    @Transactional
    public ConsultaResponseDTO agendar(ConsultaCreateDTO consultaCreateDTO) {
        Paciente paciente = pacienteRepository.findById(consultaCreateDTO.pacienteId())
                .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado"));
        Medico medico = medicoRepository.findById(consultaCreateDTO.medicoId())
                .orElseThrow(() -> new IllegalArgumentException("Médico não encontrado"));

        // Melhoria implementada: Validação de agendamento
        if (consultaRepository.existsByMedicoIdAndDataHora(medico.getId(), consultaCreateDTO.dataHora())) {
            throw new IllegalArgumentException("Médico já possui uma consulta agendada para este horário");
        }

        Consulta consulta = new Consulta();
        consulta.setPaciente(paciente);
        consulta.setMedico(medico);
        consulta.setDataHora(consultaCreateDTO.dataHora());
        consulta.setStatus(StatusConsulta.AGENDADA);
        consulta = consultaRepository.save(consulta);

        return new ConsultaResponseDTO(consulta.getId(), consulta.getPaciente().getId(), consulta.getMedico().getId(), consulta.getDataHora(), consulta.getStatus());
    }

    @Transactional
    public void cancelar(Long id) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Consulta não encontrada"));
        consulta.setStatus(StatusConsulta.CANCELADA);
        consultaRepository.save(consulta);
    }
}