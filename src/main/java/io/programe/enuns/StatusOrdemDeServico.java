/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package io.programe.enuns;

/**
 *
 * @author Flaviana Andrade
 */


public enum StatusOrdemDeServico {

    ABERTO("Aberto"),
    EM_ANDAMENTO("Em andamento"),
    CONCLUIDO("Concluído"),
    CANCELADO("Cancelado");

    private final String statusOrdem;

    StatusOrdemDeServico(String statusOrdem) {
        this.statusOrdem = statusOrdem;
    }

    @Override
    public String toString() {
        return statusOrdem; // Retorna diretamente o nome amigável
    }

    public String getStatusOrdem() {
        return statusOrdem;
    }

    public static StatusOrdemDeServico fromString(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        for (StatusOrdemDeServico status : StatusOrdemDeServico.values()) {
            if (status.statusOrdem.equalsIgnoreCase(value)) {
                return status;
            }
        }
        return null; // Retorna null se não encontrar um estado correspondente
    }
}
