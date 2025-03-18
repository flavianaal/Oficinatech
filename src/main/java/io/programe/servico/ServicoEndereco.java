/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.programe.servico;

import io.programe.generico.ServicoGenerico;
import io.programe.modelo.Endereco;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;


/**
 *
 * @author Flaviana Andrade
 */
@Stateless
public class ServicoEndereco extends ServicoGenerico<Endereco> {

    public ServicoEndereco() {
        super(Endereco.class);
    }

    public List<Endereco> pesquisarEndereco(Endereco endereco) {
        // Inicia uma string SQL para buscar endereços com base nas condições fornecidas.
        String sql = "select e from Endereco e where  ";

        // Se o logradouro do endereço não for null e não estiver vazio, adiciona uma condição de busca para o nome da rua
        if (endereco.getLogradouro() != null && !endereco.getLogradouro().equals("")) {

            // Utiliza o operador LIKE para buscar ruas com base no logradouro fornecido
            sql += "lower(e.nomeRua) like lower(:nomeRua) and";
        }

        // Se o número do endereço não for null e não estiver vazio, adiciona uma condição de busca para o número
        if (endereco.getNumero() != null && !endereco.getNumero().equals("")) {

            // Utiliza o operador LIKE para buscar endereços com base no número fornecido
            sql += " e.numero like :numero and ";
        }

        // Adiciona a condição final que garante que o endereço deve estar ativo
        sql += " e.ativo = true";

        // Cria a query com base na string SQL e define o tipo de resultado como Endereco
        Query query = getEntityManager().createQuery(sql, Endereco.class);

        // Se o logradouro foi fornecido, define o parâmetro da consulta para o nome da rua
        if (endereco.getLogradouro() != null && !endereco.getLogradouro().equals("")) {

            // Adiciona curingas "%" para buscar qualquer ocorrência que contenha o logradouro
            query.setParameter("nomeRua", "%" + endereco.getLogradouro() + "%");
        }

        // Se o número foi fornecido, define o parâmetro da consulta para o número do endereço.
        if (endereco.getNumero() != null && !endereco.getNumero().equals("")) {

            // Adiciona curingas "%" para buscar qualquer ocorrência que contenha o número
            query.setParameter("numero", "%" + endereco.getNumero() + "%");
        }

        // Retorna a lista de endereços que atendem às condições da consulta
        return query.getResultList();
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    

}
