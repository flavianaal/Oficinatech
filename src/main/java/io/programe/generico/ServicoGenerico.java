/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.programe.generico;

import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Flaviana Andrade
 * @param <T>
 */
public class ServicoGenerico<T> {

    // Tipo da entidade para operações genéricas
    private Class<T> entidade;

    // Gerenciador de entidades para operações de persistência
    @PersistenceContext
    public EntityManager entityManager;

    // Construtor que recebe a classe da entidade
    public ServicoGenerico(Class<T> entidade) {
        this.entidade = entidade;
    }

    // Método para salvar uma nova entidade
    public void salvar(T entidade) {
        entityManager.persist(entidade);
    }

    // Método para atualizar uma entidade existente
    public void atualizar(T entidade) {
        entityManager.merge(entidade);
    }

    public void flush() {
        entityManager.flush(); // 🔹 Força a sincronização com o banco de dados
    }

    // Método para excluir uma entidade
    public void delete(T entidade) {
        // Merge a entidade para garantir que está gerenciada pelo EntityManager
        entidade = entityManager.merge(entidade);
        // Remove a entidade do banco de dados
        entityManager.remove(entidade);
    }

    // Método para encontrar uma entidade pelo seu ID
    public T find(Long id) {
        T objeto = getEntityManager().find(entidade, id);
        if (objeto != null) {
            getEntityManager().refresh(objeto);
        }
        return objeto;
    }

    // Método para encontrar todas as entidades ativas
    public List<T> findAll() {
        // Cria e executa uma consulta JPQL para encontrar todas as entidades ativas
        return entityManager.createQuery("SELECT e FROM " + entidade.getSimpleName() + " e WHERE e.ativo = true").getResultList();
    }

    // Getter para a classe da entidade
    public Class<T> getEntidade() {
        return entidade;
    }

    // Getter para o EntityManager
    public EntityManager getEntityManager() {
        return entityManager;
    }

    // Setter para o EntityManager
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // Método hashCode para comparação de objetos
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.entidade);
        hash = 23 * hash + Objects.hashCode(this.entityManager);
        return hash;
    }

    // Método equals para comparação de objetos
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ServicoGenerico<?> other = (ServicoGenerico<?>) obj;
        if (!Objects.equals(this.entidade, other.entidade)) {
            return false;
        }
        return Objects.equals(this.entityManager, other.entityManager);
    }

    // Método toString para representação textual da classe
    @Override
    public String toString() {
        return "ServicoGenerico{" + "entidade=" + entidade + ", entityManager=" + entityManager + '}';
    }
}
