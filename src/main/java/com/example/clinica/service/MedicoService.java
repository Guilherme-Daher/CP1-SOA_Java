package com.example.clinica.service;

import com.example.clinica.domain.model.Medico;
import com.example.clinica.dto.MedicoCreateDTO;
import com.example.clinica.dto.MedicoResponseDTO;
import com.example.clinica.dto.MedicoUpdateDTO;
import com.example.clinica.repository.MedicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MedicoService {

    private final MedicoRepository medicoRepository;

    @Transactional
    public MedicoResponseDTO criar(MedicoCreateDTO medicoCreateDTO) {
        medicoRepository.findByCrm(medicoCreateDTO.crm()).ifPresent(m -> {
            throw new IllegalArgumentException("CRM já cadastrado");
        });
        Medico medico = new Medico();
        medico.setNome(medicoCreateDTO.nome());
        medico.setCrm(medicoCreateDTO.crm());
        medico = medicoRepository.save(medico);
        return new MedicoResponseDTO(medico.getId(), medico.getNome(), medico.getCrm());
    }

    @Transactional(readOnly = true)
    public Page<MedicoResponseDTO> listar(Pageable pageable) {
        return medicoRepository.findAll(pageable)
                .map(medico -> new MedicoResponseDTO(medico.getId(), medico.getNome(), medico.getCrm()));
    }

    @Transactional(readOnly = true)
    public MedicoResponseDTO buscarPorId(Long id) {
        return medicoRepository.findById(id)
                .map(medico -> new MedicoResponseDTO(medico.getId(), medico.getNome(), medico.getCrm()))
                .orElseThrow(() -> new IllegalArgumentException("Médico não encontrado"));
    }

    @Transactional
    public MedicoResponseDTO atualizar(Long id, MedicoUpdateDTO medicoUpdateDTO) {
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Médico não encontrado"));

        if (medicoUpdateDTO.nome() != null) {
            medico.setNome(medicoUpdateDTO.nome());
        }
        if (medicoUpdateDTO.crm() != null) {
            medicoRepository.findByCrm(medicoUpdateDTO.crm()).ifPresent(m -> {
                if (!m.getId().equals(id)) {
                    throw new IllegalArgumentException("CRM já cadastrado");
                }
            });
            medico.setCrm(medicoUpdateDTO.crm());
        }
        medico = medicoRepository.save(medico);
        return new MedicoResponseDTO(medico.getId(), medico.getNome(), medico.getCrm());
    }

    @Transactional
    public void deletar(Long id) {
        if (!medicoRepository.existsById(id)) {
            throw new IllegalArgumentException("Médico não encontrado");
        }
        medicoRepository.deleteById(id);
    }
}