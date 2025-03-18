package io.programe.manager;

import io.programe.enuns.StatusOrdemDeServico;
import io.programe.modelo.Cliente;
import io.programe.modelo.OrdemDeServico;
import io.programe.modelo.Servico;
import io.programe.servico.ServicoOrdemServico;
import io.programe.util.Mensagem;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Named
@ViewScoped
public class ManagerOrdemDeServico implements Serializable {

    @EJB
    private ServicoOrdemServico servicoOrdemServico;

    private OrdemDeServico ordemDeServico;
    private List<OrdemDeServico> listaOrdensServico;
    private StatusOrdemDeServico statusOrdemDeServico;
    private Cliente cliente;
    private Servico servico;

    @PostConstruct
    public void instanciar() {
        cliente = new Cliente();
        listaOrdensServico = new ArrayList<>();

        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String visualizar = params.get("visualizar");
        String editar = params.get("editar");

        try {
            if (visualizar != null && !visualizar.trim().isEmpty()) {
                ordemDeServico = servicoOrdemServico.find(Long.valueOf(visualizar));
            } else if (editar != null && !editar.trim().isEmpty()) {
                ordemDeServico = servicoOrdemServico.find(Long.valueOf(editar));
            } else {
                ordemDeServico = new OrdemDeServico();
            }

        } catch (NumberFormatException e) {
            Mensagem.mensagemErro("Erro ao carregar Ordem");
        }

    }

    public void adicionarServico() {
        if (ordemDeServico.getServicos() != null) {
            ordemDeServico.getServicos().add(servico);
        }
        servico = new Servico();
        calcularValorTotal();
    }

    public void salvar() {
        if (ordemDeServico.getCliente() == null || ordemDeServico.getVeiculo() == null) {
            Mensagem.mensagemAlerta("Cliente e Veículo são obrigatórios.");
            return; // Sai do método e não continua com a persistência
        }

        if (ordemDeServico.getServicos() == null || ordemDeServico.getServicos().isEmpty()) {
            Mensagem.mensagemAlerta("A OS precisa ter pelo menos um serviço.");
            return;
        }

        if (ordemDeServico.getId() == null) {
            ordemDeServico.setAtivo(true);
            servicoOrdemServico.salvar(ordemDeServico);
            Mensagem.mensagemInfo("Ordem de Serviço cadastrada com sucesso!");
        } else {
            servicoOrdemServico.atualizar(ordemDeServico);
            Mensagem.mensagemInfo("Ordem de Serviço atualizada com sucesso!");
        }

        instanciar();
    }

    public void carregarOrdensServico() {
        this.listaOrdensServico = servicoOrdemServico.listarOrdemDeServico();
    }

    public StatusOrdemDeServico[] getStatusOrdemServico() {
        return StatusOrdemDeServico.values();
    }

    public void calcularValorTotal() {
        if (ordemDeServico.getServicos() != null && !ordemDeServico.getServicos().isEmpty()) {
            BigDecimal total = ordemDeServico.getServicos().stream()
                    .map(s -> s.getValorServico() != null ? s.getValorServico() : BigDecimal.ZERO)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            ordemDeServico.setValorTotal(total);
        } else {
            ordemDeServico.setValorTotal(BigDecimal.ZERO);
        }
    }

    public void pesquisar() {
        // Chama o serviço passando a instância de ordemDeServico, que já contém os parâmetros para filtro
        listaOrdensServico = servicoOrdemServico.pesquisar(statusOrdemDeServico, cliente);

        // Verifica se encontrou resultados
        if (listaOrdensServico == null || listaOrdensServico.isEmpty()) {
            System.out.println("Nenhuma ordem de serviço encontrada.");
        } else {
            System.out.println("Ordens de Serviço encontradas: " + listaOrdensServico.size());
        }
    }

    public void remover(OrdemDeServico ordemDeServico) {
        try {
            if (ordemDeServico != null) {
                ordemDeServico.setAtivo(false); // Desativar a ordem
                servicoOrdemServico.atualizar(ordemDeServico); // Atualizar no banco

                Mensagem.mensagemInfo("Ordem de Serviço removida visualmente.");
                carregarOrdensServico();
            } else {
                Mensagem.mensagemErro("Erro ao remover: Ordem de Serviço inválida.");
            }
        } catch (Exception e) {
            Mensagem.mensagemErro("Erro ao ocultar a Ordem de Serviço: " + e.getMessage());
        }
    }

    public void excluir(Long id) {
        servicoOrdemServico.excluir(id);
        Mensagem.mensagemInfo("Ordem de Serviço excluída com sucesso!");
        instanciar();
    }

    public void removerServico(Servico servico) {
        try {
            if (servico != null) {
                ordemDeServico.getServicos().remove(servico);
                calcularValorTotal();
                Mensagem.mensagemInfo("Serviço removido da Ordem de Serviço.");
            } else {
                Mensagem.mensagemErro("Erro ao remover: Serviço inválido.");
            }
        } catch (Exception e) {
            Mensagem.mensagemErro("Erro ao remover o serviço: " + e.getMessage());
        }
    }
}
