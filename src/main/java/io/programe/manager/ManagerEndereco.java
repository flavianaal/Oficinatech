/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.programe.manager;

import io.programe.enuns.Estado;
import io.programe.modelo.Endereco;
import io.programe.servico.ServicoEndereco;
import io.programe.util.Mensagem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
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
@Named
@ViewScoped
public class ManagerEndereco implements Serializable{
    
    @EJB
    ServicoEndereco servicoEndereco;

    private Endereco endereco;
    private Estado estados;
    
    

    @PostConstruct
    public void instanciar() {
        endereco = new Endereco();

    }

    public void salvar() {
        if (endereco.getEstado() == null) {
            System.out.println("Estado não foi selecionado!");
        } else {
            System.out.println("Estado selecionado: " + endereco.getEstado());
        }

        servicoEndereco.salvar(endereco);
        instanciar();
        Mensagem.mensagemInfo("Operação Realizada com Sucesso");
    }
    
    public List<SelectItem> getEstados() {
        List<SelectItem> items = new ArrayList<>();
        for (Estado item : Estado.values()) {
            items.add(new SelectItem(item, item.getNome()));
        }
        return items;
    }
    
}
