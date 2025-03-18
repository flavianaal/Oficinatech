/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.programe.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Date;
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
public class Servico implements Serializable {

    @Id
    @SequenceGenerator(name = "seq_servico", sequenceName = "seq_servico", initialValue = 1)
    @GeneratedValue(generator = "seq_servico", strategy = GenerationType.SEQUENCE)
    private Long id;
    
    private String nome;
    
    private BigDecimal valorServico;
    private LocalTime tempoEstimado; 
   
    private boolean ativo; 

    public Servico() {
    }

    public Servico(Long id, String nome, BigDecimal valorServico, LocalTime tempoEstimado, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.valorServico = valorServico;
        this.tempoEstimado = tempoEstimado;
        this.ativo = ativo;
    }

   
    

    

   


}
