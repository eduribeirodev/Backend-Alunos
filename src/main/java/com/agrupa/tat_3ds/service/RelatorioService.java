package com.agrupa.tat_3ds.service;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agrupa.tat_3ds.dto.GrupoRelatorioDTO;
import com.agrupa.tat_3ds.dto.RelatorioTrabalhoDTO;
import com.agrupa.tat_3ds.models.Grupo;
import com.agrupa.tat_3ds.repository.GrupoRepository;
import com.agrupa.tat_3ds.repository.GrupoUsuarioRepository;   
import com.agrupa.tat_3ds.repository.TrabalhoRepository;

@Service
public class RelatorioService {
    @Autowired private TrabalhoRepository trabalhoRepo;
    @Autowired private GrupoRepository grupoRepo;
    @Autowired private GrupoUsuarioRepository grupoUsuarioRepo;

    public RelatorioTrabalhoDTO buscarDadosConsolidados(Integer idTrabalho) {
        var trabalho = trabalhoRepo.findById(idTrabalho)
                .orElseThrow(() -> new RuntimeException("Trabalho não encontrado"));

        List<Grupo> listaGrupos = grupoRepo.findByTrabalhoEmGrupo(trabalho);

        List<GrupoRelatorioDTO> gruposDTO = listaGrupos.stream().map(g -> {
            List<String> nomesAlunos = grupoUsuarioRepo.findByGrupo(g)
                    .stream()
                    .map(gu -> gu.getUsuario().getNome())
                    .toList();

            return new GrupoRelatorioDTO(
                    g.getDescricao(),
                    nomesAlunos.size(),
                    trabalho.getQuantidadePessoas(),
                    nomesAlunos
            );
        }).toList();

        String dataFormatada = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm:ss"));

        return new RelatorioTrabalhoDTO(
                trabalho.getDescricao(),
                dataFormatada,
                trabalho.getQuantidadeGrupos(),
                trabalho.getQuantidadePessoas(),
                gruposDTO
        );
    }
}
