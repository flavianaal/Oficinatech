/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.programe.servico;

import io.programe.generico.ServicoGenerico;
import io.programe.modelo.Veiculo;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Flaviana Andrade
 */
@Stateless
public class ServicoVeiculo extends ServicoGenerico<Veiculo> {

    public ServicoVeiculo() {
        super(Veiculo.class);
    }

    public List<Veiculo> findByAll(Veiculo veiculo) {

        //INICIANDO A QUERY DO BANCO DE DADOS
        String sql = "select v from Veiculo v where ";

        //CASO O NOME DO CLIENTE TENHA UM VALOR INSERIDO ELE ENTRA NO IF
        if (veiculo.getModelo() != null && !veiculo.getModelo().equals("")) {
            sql += "lower(v.modelo) like lower(:modelo) and ";
        }

        //CASO O CPF DO CLIENTE TENHA UM VALOL INSERIDO ELE ENTRA NO IF
        if (veiculo.getPlaca() != null && !veiculo.getPlaca().equals("")) {
            sql += "v.placa like :placa and ";
        }

        //A CONSULTA VAI SEMPRE TRAZER OS CLIENTES ATIVOS NO BANCO DE DADOS
        sql += "v.ativo = true";

        //LENDO A QUERY
        Query query = getEntityManager().createQuery(sql, Veiculo.class);
        //PASSANDO OS PARAMETROS

        if (veiculo.getModelo() != null && !veiculo.getModelo().equals("")) {
            query.setParameter("modelo", "%" + veiculo.getModelo() + "%");
        }

        if (veiculo.getPlaca() != null && !veiculo.getPlaca().equals("")) {
            query.setParameter("placa", "%" + veiculo.getPlaca() + "%");
        }
        return query.getResultList();
    }

    public void delete(Veiculo veiculo) {
        veiculo = entityManager.merge(veiculo); //garante que a entidade está gerenciada
        veiculo.setAtivo(false); // Apenas marca como inativo
        entityManager.merge(veiculo); // Atualiza no banco
    }

    public List<Veiculo> buscarTodoVeiculo() {
        return entityManager.createQuery("SELECT v FROM Veiculo v WHERE v.ativo = true", Veiculo.class)
                .getResultList();
    }
    
    public List<Veiculo> findVeiculo(String modelo) {
        // Define a base da consulta
        String sql = "SELECT v FROM Veiculo v WHERE v.ativo = true";

        // Se um nome foi informado, adiciona a cláusula de busca
        if (modelo != null && !modelo.trim().isEmpty()) {
            sql += " AND LOWER(v.modelo) LIKE LOWER(:modelo)";
        }

        // Adiciona a ordenação
        sql += " ORDER BY v.modelo ASC";

        // Cria a query
        Query query = getEntityManager().createQuery(sql);

        // Se o nome foi informado, define o parâmetro na consulta
        if (modelo != null && !modelo.trim().isEmpty()) {
            query.setParameter("modelo", "%" + modelo.trim() + "%");
        }

        // Retorna os clientes encontrados
        return query.getResultList();
    }

}
