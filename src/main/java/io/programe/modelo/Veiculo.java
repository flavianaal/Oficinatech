/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.programe.modelo;

import java.io.Serializable;
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
public class Veiculo implements Serializable{
    
    @Id
    @SequenceGenerator(name = "seq_veiculo", sequenceName = "seq_veiculo", initialValue = 1)
    @GeneratedValue(generator = "seq_veiculo", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String placa;
    private String modelo;
    private String anoCarro;
    @ManyToOne
    private Cliente cliente;
    private boolean ativo; 

    public Veiculo() {
    }

    public Veiculo(Long id, String placa, String modelo, String anoCarro, Cliente cliente, boolean ativo) {
        this.id = id;
        this.placa = placa;
        this.modelo = modelo;
        this.anoCarro = anoCarro;
        this.cliente = cliente;
        this.ativo = ativo;
    }

  

}
