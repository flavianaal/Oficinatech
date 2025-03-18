/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.programe.manager;

import io.programe.modelo.Servico;
import io.programe.servico.ServicoServico;
import io.programe.util.Mensagem;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
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
@Named
@ViewScoped
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class ManagerServico implements Serializable {

    @EJB
    ServicoServico servicoServico;

    private Servico servico;
    private List<Servico> servicos;
  

    @PostConstruct
    public void instanciar() {
        servicos = servicoServico.listar();

        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String visualizar = params.get("visualizar");
        String editar = params.get("editar");

        if (visualizar != null) {
            servico = servicoServico.find(Long.parseLong(visualizar));
        } else if (editar != null) {
            servico = servicoServico.find(Long.parseLong(editar));
        } else {
            servico = new Servico();
        }

    }

    public void salvar() {
       if (servico.getId() == null) {
            servico.setAtivo(true);
            servicoServico.salvar(servico);
            Mensagem.mensagemInfo("Serviço cadastrado com sucesso!");
        } else {
            servicoServico.atualizar(servico);
            Mensagem.mensagemInfo("Serviço atualizado com sucesso!");
        }
        instanciar();
    }

    
    public void pesquisar() {
        System.out.println("Nome Do Serviço: " + servico.getNome());
        servicos = servicoServico.findAll();

        // Verificando o conteúdo da lista
        if (servicos == null || servicos.isEmpty()) {
            System.out.println("Nenhum serviço encontrado.");
        } else {
            System.out.println("Serviços encontrados: " + servicos.size());
        }
    }
    
  
    
    
    
    
    
    

    

}
