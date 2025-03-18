/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.programe.modelo;

import java.io.Serializable;
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
public class Cliente implements Serializable {

    @Id
    @SequenceGenerator(name = "seq_cliente", sequenceName = "seq_cliente", initialValue = 1)
    @GeneratedValue(generator = "seq_cliente", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nome;
    private String CPF;
    private String CNPJ;
    private String telefone;
    private String email;
    private String tipo;
    private String razaoSocial;
    private boolean ativo;

    @OneToMany
    private List<Veiculo> veiculos;
    
    @ManyToOne(cascade = CascadeType.PERSIST) 
    private Endereco endereco;

    public Cliente() {
    }

    public Cliente(Long id, String nome, String CPF,
            String CNPJ, String telefone, String email,
            Endereco endereco, String tipo, String razaoSocial,
            List<Veiculo> veiculos, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.CPF = CPF;
        this.CNPJ = CNPJ;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
        this.tipo = tipo;
        this.razaoSocial = razaoSocial;
        this.veiculos = veiculos;
        this.ativo = ativo;
    }

}
