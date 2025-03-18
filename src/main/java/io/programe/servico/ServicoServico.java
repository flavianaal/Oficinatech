/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.programe.servico;

import io.programe.generico.ServicoGenerico;
import io.programe.modelo.Servico;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Flaviana Andrade
 */
@Stateless
public class ServicoServico extends ServicoGenerico<Servico> {

    public ServicoServico() {
        super(Servico.class);
    }

    public List<Servico> listar() {
        return entityManager.createQuery("SELECT s FROM Servico s", Servico.class).getResultList();
    }
    
   
    public List<Servico> buscarTodos() {
        return entityManager.createQuery("SELECT c FROM Cliente c WHERE c.ativo = true", Servico.class)
                .getResultList();
    }

}
