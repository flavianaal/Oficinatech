/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.programe.servico;

import io.programe.generico.ServicoGenerico;
import io.programe.modelo.Cliente;
import io.programe.modelo.Endereco;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Flaviana Andrade
 */
@Stateless
public class ServicoCliente extends ServicoGenerico<Cliente> {

    public ServicoCliente() {
        super(Cliente.class);
    }

    public List<Cliente> findByAll(Cliente cliente) {
        // Construindo a JPQL para buscar funcionarios ativos
        StringBuilder jpql = new StringBuilder("select c from Cliente c where c.ativo = true");
        List<String> conditions = new ArrayList<>();

        // Adicionando condições para buscar pelo nome do funcionario
        if (cliente.getNome() != null && !cliente.getNome().isEmpty()) {
            conditions.add("lower(c.nome) like lower(:nome)");
        }

        // Adicionando as condições à query
        if (!conditions.isEmpty()) {
            jpql.append(" and ");
            jpql.append(String.join(" and ", conditions));
        }

        // Criando a query baseado nas consições
        TypedQuery<Cliente> query = entityManager.createQuery(jpql.toString(), Cliente.class);

        // Configurando parâmetros
        if (cliente.getNome() != null && !cliente.getNome().isEmpty()) {
            query.setParameter("nome", "%" + cliente.getNome() + "%");
        }

        //Executando a consulta retornando a lista de funcionario
        return query.getResultList();
    }

    //Metodo para buscar o endereço utilizando os parametros CEP, LOGRADOUURO e  NUMERO
    public Endereco findEndereco(Endereco endereco) {

        String query = "SELECT e FROM Endereco e WHERE e.cep = :cep AND e.logradouro = :logradouro AND e.numero = :numero";
        TypedQuery<Endereco> typedQuery = entityManager.createQuery(query, Endereco.class);

        //Define os parametros
        typedQuery.setParameter("cep", endereco.getCEP());
        typedQuery.setParameter("logradouro", endereco.getLogradouro());
        typedQuery.setParameter("numero", endereco.getNumero());

        //Executa e retorna a consulta do endereço encontrado ou caso seja nulo
        List<Endereco> result = typedQuery.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public void delete(Cliente cliente) {
        cliente = entityManager.merge(cliente); // Garante que a entidade está gerenciada
        cliente.setAtivo(false); // Apenas marca como inativo
        entityManager.merge(cliente); // Atualiza no banco
    }

    public List<Cliente> buscarTodos() {
        return entityManager.createQuery("SELECT c FROM Cliente c WHERE c.ativo = true", Cliente.class)
                .getResultList();
    }

    public List<Cliente> findCliente(String nome) {
        // Define a base da consulta
        String sql = "SELECT c FROM Cliente c WHERE c.ativo = true";

        // Se um nome foi informado, adiciona a cláusula de busca
        if (nome != null && !nome.trim().isEmpty()) {
            sql += " AND LOWER(c.nome) LIKE LOWER(:nome)";
        }

        // Adiciona a ordenação
        sql += " ORDER BY c.nome ASC";

        // Cria a query
        Query query = getEntityManager().createQuery(sql);

        // Se o nome foi informado, define o parâmetro na consulta
        if (nome != null && !nome.trim().isEmpty()) {
            query.setParameter("nome", "%" + nome.trim() + "%");
        }

        // Retorna os clientes encontrados
        return query.getResultList();
    }

    public Cliente buscarPorId(Long id) {
        return entityManager.find(Cliente.class, id);
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
