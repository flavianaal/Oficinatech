package io.programe.servico;

import io.programe.enuns.StatusOrdemDeServico;
import io.programe.generico.ServicoGenerico;
import io.programe.modelo.Cliente;
import io.programe.modelo.OrdemDeServico;
import io.programe.modelo.Servico;
import io.programe.util.Mensagem;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Flaviana Andrade
 */
@Stateless
public class ServicoOrdemServico extends ServicoGenerico<OrdemDeServico> {

    @PersistenceContext
    private EntityManager em;

    public ServicoOrdemServico() {
        super(OrdemDeServico.class);
    }

    public List<OrdemDeServico> pesquisar(StatusOrdemDeServico status, Cliente cliente) {
        StringBuilder jpql = new StringBuilder("SELECT o FROM OrdemDeServico o WHERE o.ativo = true");

        // Adiciona a condição do status apenas se não for nulo
        if (status != null) {
            jpql.append(" AND o.status = :status");
        }

        // Adiciona a condição do cliente apenas se não for nulo
        if (cliente != null && cliente.getNome() != null && !cliente.getNome().isEmpty()) {
            jpql.append(" AND LOWER(o.cliente.nome) LIKE LOWER(:nome)");
        }

        TypedQuery<OrdemDeServico> query = em.createQuery(jpql.toString(), OrdemDeServico.class);

        // Define os parâmetros apenas se os valores não forem nulos
        if (status != null) {
            query.setParameter("status", status);
        }
        if (cliente != null && cliente.getNome() != null && !cliente.getNome().trim().isEmpty()) {
            query.setParameter("nome", "%" + cliente.getNome().trim().toLowerCase() + "%");
        }

        // Executa a consulta e retorna os resultados
        return query.getResultList();
    }

    public void salvarOrdemServico(OrdemDeServico ordemDeServico) {
        if (ordemDeServico.getCliente() == null) {
            Mensagem.mensagemAlerta("Erro: Cliente é obrigatório.");
            return;
        }
        if (ordemDeServico.getVeiculo() == null) {
            Mensagem.mensagemAlerta("Erro: Veículo é obrigatório.");
            return;
        }
        if (ordemDeServico.getServicos() == null || ordemDeServico.getServicos().isEmpty()) {
            Mensagem.mensagemAlerta("Erro: A OS precisa ter pelo menos um serviço.");
            return;
        }

        // Adicione prints para depuração
        System.out.println("Salvando OS para Cliente: " + ordemDeServico.getCliente().getNome());
        System.out.println("Veículo: " + ordemDeServico.getVeiculo().getPlaca());

        if (ordemDeServico.getId() == null) {
            em.persist(ordemDeServico);
        } else {
            em.merge(ordemDeServico);
        }
    }

    public List<Servico> listarServicos() {
        List<Servico> servicos = em.createQuery("SELECT s FROM Servico s", Servico.class).getResultList();

        if (servicos.isEmpty()) {
            Mensagem.mensagemInfo("Nenhum serviço encontrado no banco.");
        }

        return servicos;
    }

    public List<OrdemDeServico> listarOrdemDeServico() {
        return em.createQuery("SELECT o FROM OrdemDeServico o", OrdemDeServico.class)
                .getResultList();
    }

    public OrdemDeServico buscarPorId(Long id) {
        return em.find(OrdemDeServico.class, id);
    }

    public void excluir(Long id) {
        OrdemDeServico ordemDeServico = find(id);
        if (ordemDeServico != null) {
            ordemDeServico.setAtivo(false);
            em.merge(ordemDeServico);
        }
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

}
