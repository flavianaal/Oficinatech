/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.programe.manager;

import io.programe.modelo.Cliente;
import io.programe.modelo.Veiculo;
import io.programe.servico.ServicoCliente;
import io.programe.servico.ServicoVeiculo;
import io.programe.util.Mensagem;
import java.io.Serializable;
import java.util.ArrayList;
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
public class ManagerVeiculo implements Serializable {

    @EJB
    private ServicoVeiculo servicoVeiculo;

    @EJB
    private ServicoCliente servicoCliente;

    private Veiculo veiculo;
    private List<Veiculo> veiculos;
    private Cliente cliente;

    @PostConstruct
    public void instanciar() {
        
        carregarVeiculos();
     
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String visualizar = params.get("visualizar");
        String editar = params.get("editar");

        // Se o parâmetro visualizar ou editar estiver presente, carrega o veículo e o cliente correspondentes
        if (visualizar != null || editar != null) {
            Long veiculoId = visualizar != null ? Long.parseLong(visualizar) : Long.parseLong(editar);
            veiculo = servicoVeiculo.find(veiculoId);
            if (veiculo != null) {
                cliente = servicoCliente.find(veiculo.getCliente().getId());
            }
        } else {
            // Caso contrário, inicializa um novo veículo e cliente
            veiculo = new Veiculo();
            cliente = new Cliente();
        }

        // Associa o cliente ao veículo
        veiculo.setCliente(cliente);

        // Inicializa a lista de veículos (caso precise para outros fins)
        veiculos = new ArrayList<>();
    }

    public void salvar() {
        if (veiculo.getId() == null) {
            if (veiculo.getCliente() != null && veiculo.getCliente().getId() != null) {
                veiculo.setAtivo(true);
                servicoVeiculo.salvar(veiculo);
            } else {
                servicoCliente.salvar(veiculo.getCliente()); // Persiste o cliente

                servicoVeiculo.salvar(veiculo); // Persiste o veículo
            }
            veiculo = new Veiculo();
        } else {
            servicoVeiculo.atualizar(veiculo);
        }
        Mensagem.mensagemInfo("Operação Realizada com sucesso");
    }

    public void pesquisar() {
        System.out.println("Modelo: " + veiculo.getModelo());
        System.out.println("Placa: " + veiculo.getPlaca());
        veiculos = servicoVeiculo.findByAll(veiculo);

    }

    public void remover(Veiculo veiculo) {
        try {
            if (veiculo != null) {
                servicoVeiculo.delete(veiculo); // Método deve apenas marcar como inativo
                Mensagem.mensagemInfo("Veiculo removido com sucesso.");
                carregarVeiculos(); // Atualiza a lista na tela
            } else {
                Mensagem.mensagemErro("Erro ao remover: Veiculo inválido.");
            }
        } catch (Exception e) {
            Mensagem.mensagemErro("Erro ao remover o veiculo: " + e.getMessage());
        }
    }

    private void carregarVeiculos() {
        veiculos = servicoVeiculo.buscarTodoVeiculo(); // Método para buscar apenas clientes ativos
    }
    
     public List<Veiculo> autocompleteVeiculo(String modelo) {
        return servicoVeiculo.findVeiculo(modelo);

    }

}
