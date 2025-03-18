/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.programe.modelo;

import io.programe.enuns.StatusOrdemDeServico;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Flaviana Andrade
 */
@ToString
@EqualsAndHashCode
@Getter
@Setter
@Entity
public class OrdemDeServico implements Serializable {

    @Id
    @SequenceGenerator(name = "seq_ordem", sequenceName = "seq_ordem", initialValue = 1)
    @GeneratedValue(generator = "seq_ordem", strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    private Cliente cliente;
    @ManyToOne
    private Veiculo veiculo;
    private LocalDate dataEntrada;
    private LocalDate dataprevistaEntrega;
    @Enumerated(EnumType.STRING)
    private StatusOrdemDeServico status;
    private String observacoes;
    private BigDecimal valorTotal;
    private boolean ativo;
    @OneToMany
    private List<Servico> servicos;

    public OrdemDeServico() {
    }

    public OrdemDeServico(Long id, Cliente cliente, Veiculo veiculo, LocalDate dataEntrada, LocalDate dataprevistaEntrega, StatusOrdemDeServico status, String observacoes, BigDecimal valorTotal, boolean ativo, List<Servico> servicos) {
        this.id = id;
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.dataEntrada = dataEntrada;
        this.dataprevistaEntrega = dataprevistaEntrega;
        this.status = status;
        this.observacoes = observacoes;
        this.valorTotal = valorTotal;
        this.ativo = ativo;
        this.servicos = servicos;
    }
    
    

}
