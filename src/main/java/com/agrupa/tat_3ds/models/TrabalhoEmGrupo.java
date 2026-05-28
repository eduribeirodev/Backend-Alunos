package com.agrupa.tat_3ds.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "trabalho_em_grupo")
public class TrabalhoEmGrupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_trabalho")
    private Integer idTrabalho;

    @Column(length = 1000)
    private String descricao;

    @Column(name = "hash_para_acesso", length = 200)
    private String hashParaAcesso;

    private Integer quantidadePessoas;

    private Integer quantidadeGrupos;

}