package io.programe.manager;

import io.programe.modelo.Servico;
import io.programe.servico.ServicoServico;
import io.programe.util.Mensagem;
import java.io.Serializable;
import java.time.Duration;
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

@Named
@ViewScoped
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ManagerServico implements Serializable {

    // ======= DEPENDÊNCIAS =======
    @EJB
    private ServicoServico servicoServico;

    // ======= ENTIDADES E LISTAS =======
    private Servico servico;
    private List<Servico> servicos;

    // ======= CAMPOS AUXILIARES PARA TEMPO ESTIMADO =======
    private int dias;
    private int horas;
    private int minutos;

    // ======= MÉTODO POSTCONSTRUCT =======
    @PostConstruct
    public void instanciar() {
        carregarListaServicos();
        inicializarServico();
    }

    private void carregarListaServicos() {
        servicos = servicoServico.listar();
    }

    private void inicializarServico() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String visualizar = params.get("visualizar");
        String editar = params.get("editar");

        if (visualizar != null) {
            servico = servicoServico.find(Long.parseLong(visualizar));
            carregarTempoEstimado();
        } else if (editar != null) {
            servico = servicoServico.find(Long.parseLong(editar));
            carregarTempoEstimado();
        } else {
            servico = new Servico();
            resetarTempoAuxiliar();
        }
    }

    // ======= MÉTODOS AUXILIARES DE TEMPO =======

    private void carregarTempoEstimado() {
        Duration d = servico.getTempoEstimadoDuration();
        if (!d.isZero()) {
            dias = (int) d.toDays();
            horas = (int) (d.toHours() % 24);
            minutos = (int) (d.toMinutes() % 60);
        } else {
            resetarTempoAuxiliar();
        }
    }

    private void resetarTempoAuxiliar() {
        dias = 0;
        horas = 0;
        minutos = 0;
    }

    private void atualizarTempoEstimado() {
        Duration d = Duration.ofDays(dias).plusHours(horas).plusMinutes(minutos);
        servico.setTempoEstimado(d);
    }

    // ======= MÉTODOS DE AÇÃO =======

    public void salvar() {
        atualizarTempoEstimado();

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

   
}
