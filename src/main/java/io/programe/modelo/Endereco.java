/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.programe.modelo;

import io.programe.enuns.Estado;
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
public class Endereco implements Serializable{
    
    @Id
    @SequenceGenerator(name = "seq_endereco", sequenceName = "seq_endereco", initialValue = 1)
    @GeneratedValue(generator = "seq_endereco", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String logradouro;
    private String bairro;
    private String cidade;
    @Enumerated(EnumType.STRING)
    private Estado estado;
    private String numero;
    private String CEP;
    private boolean ativo; 

    public Endereco() {
    }

    public Endereco(Long id, String logradouro, String bairro, String cidade, Estado estado, String numero, String CEP, boolean ativo) {
        this.id = id;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.numero = numero;
        this.CEP = CEP;
        this.ativo = ativo = true;
    }

  

  

    
    
    
    
    
}
