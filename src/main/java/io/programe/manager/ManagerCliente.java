package io.programe.manager;

import io.programe.modelo.Cliente;
import io.programe.modelo.Endereco;
import io.programe.servico.ServicoCliente;
import io.programe.servico.ServicoEndereco;
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

@Named
@ViewScoped
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class ManagerCliente implements Serializable {

    @EJB
    ServicoCliente servicoCliente;

    @EJB
    ServicoEndereco servicoEndereco;

    private Cliente cliente;
    private List<Cliente> clientes;
    private Endereco endereco;
    private String tipoCliente;

    @PostConstruct
    public void instanciar() {

        carregarClientes();

        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String visualizar = params.get("visualizar");
        String editar = params.get("editar");

        try {
            if (visualizar != null && !visualizar.isEmpty()) {
                cliente = servicoCliente.find(Long.parseLong(visualizar));
            } else if (editar != null && !editar.isEmpty()) {
                cliente = servicoCliente.find(Long.parseLong(editar));
            } else {
                cliente = new Cliente();
                cliente.setEndereco(new Endereco());
                clientes = null; // ❗ Esvazia a lista de clientes
            }
            System.out.println("Cliente carregado: " + cliente);
        } catch (NumberFormatException e) {
            Mensagem.mensagemErro("Erro ao carregar Cliente");
        }
    }

    public void salvar() {
        try {
            if (tipoCliente == null) {
                Mensagem.mensagemErro("Tipo de cliente inválido");
                return;
            }

            switch (tipoCliente) {
                case "F":
                    if (cliente.getCPF() == null || cliente.getCPF().isEmpty()) {
                        Mensagem.mensagemErro("CPF é obrigatório para Pessoa Física");
                        return;
                    }
                    break;
                case "J":
                    if (cliente.getCNPJ() == null || cliente.getCNPJ().isEmpty()) {
                        Mensagem.mensagemErro("CNPJ é obrigatório para Pessoa Jurídica");
                        return;
                    }
                    break;
                default:
                    Mensagem.mensagemErro("Tipo de cliente inválido");
                    return;
            }

            cliente.setTipo(tipoCliente);

            if (cliente.getId() == null) {
                cliente.setAtivo(true);

                if (cliente.getEndereco() != null) {
                    cliente.getEndereco().setAtivo(true); // 🚀 Ativa o endereço antes de salvar
                }

                servicoCliente.salvar(cliente);

            } else {
                if (cliente.getEndereco() != null) {
                    cliente.getEndereco().setAtivo(true);
                    servicoEndereco.atualizar(cliente.getEndereco());
                }

                servicoCliente.atualizar(cliente);

                // 🚀 Atualiza o cliente para garantir que os dados estão corretos
                cliente = servicoCliente.buscarPorId(cliente.getId());
            }

            Mensagem.mensagemInfo("Operação realizada com sucesso");

            // 🚀 Recarregar lista de clientes para refletir a alteração
            carregarClientes();

            // 🚀 Resetar o formulário para evitar exibição de dados antigos
            cliente = new Cliente();
            cliente.setEndereco(new Endereco()); 
            tipoCliente = null;

        } catch (Exception e) {
            Mensagem.mensagemErro("Erro ao salvar o cliente: " + e.getMessage());
        }
    }

    public void pesquisar() {
        System.out.println("Nome: " + cliente.getNome());
        clientes = servicoCliente.findByAll(cliente);

        // Verificando o conteúdo da lista
        if (clientes == null || clientes.isEmpty()) {
            System.out.println("Nenhum cliente encontrado.");
        } else {
            System.out.println("Clientes encontrados: " + clientes.size());
        }
    }

    public void remover(Cliente cliente) {
        try {
            if (cliente != null) {
                servicoCliente.delete(cliente); // Método deve apenas marcar como inativo
                Mensagem.mensagemInfo("Cliente removido com sucesso.");
                carregarClientes(); // Atualiza a lista na tela
            } else {
                Mensagem.mensagemErro("Erro ao remover: Cliente inválido.");
            }
        } catch (Exception e) {
            Mensagem.mensagemErro("Erro ao remover o cliente: " + e.getMessage());
        }
    }

    private void carregarClientes() {
        clientes = servicoCliente.buscarTodos(); // Método para buscar apenas clientes ativos
    }

    public void selecionetipoCliente() {
        if (tipoCliente == null) {
            cliente.setCPF(null);
            cliente.setCNPJ(null);
            cliente.setRazaoSocial(null);
        } else {
            switch (tipoCliente) {
                case "F":
                    cliente.setCNPJ(null);
                    cliente.setRazaoSocial(null);
                    break;
                case "J":
                    cliente.setCPF(null);
                    break;
                default:
                    cliente.setCPF(null);
                    cliente.setCNPJ(null);
                    cliente.setRazaoSocial(null);
                    break;
            }
        }
    }

    public List<Cliente> autocompleteCliente(String nome) {
        return servicoCliente.findCliente(nome);

    }
}
