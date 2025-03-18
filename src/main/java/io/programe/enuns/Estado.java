package io.programe.enuns;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 * Enumeração para representar os estados do Brasil. Cada enum tem um nome
 * associado que representa o nome completo do estado.
 *
 * @author Flaviana Andrade
 */
public enum Estado {

    ALAGOAS("Alagoas"),
    AMAZONAS("Amazonas"),
    AMAPA("Amapá"),
    ACRE("Acre"),
    BAHIA("Bahia"),
    CEARA("Ceará"),
    DISTRITO_FEDERAL("Distrito Federal"),
    ESPIRITO_SANTO("Espírito Santo"),
    GOIAS("Goiás"),
    MARANHAO("Maranhão"),
    MATO_GROSSO("Mato Grosso"),
    MATO_GROSSO_DO_SUL("Mato Grosso do Sul"),
    MINAS_GERAIS("Minas Gerais"),
    PARA("Pará"),
    PARANA("Paraná"),
    PIAUI("Piauí"),
    PERNAMBUCO("Pernambuco"),
    PARAIBA("Paraíba"),
    RONDONIA("Rondônia"),
    RORAIMA("Roraima"),
    RIO_DE_JANEIRO("Rio de Janeiro"),
    RIO_GRANDE_DO_NORTE("Rio Grande do Norte"),
    RIO_GRANDE_DO_SUL("Rio Grande do Sul"),
    SANTA_CATARINA("Santa Catarina"),
    SAO_PAULO("São Paulo"),
    SERGIPE("Sergipe"),
    TOCANTINS("Tocantins");

    // Nome completo do estado
    private final String nome;

    //Construtor privado para inicializar o nome do estado
    private Estado(String nome) {
        this.nome = nome;
    }

    // Retorna o nome completo do estado
    public String getNome() {
        return nome;
    }

    // Retorna a representação textual do estado
    @Override
    public String toString() {
        return nome;
    }

    //Converte uma string em um valor de Estado
    public static Estado fromString(String value) {
        for (Estado estado : Estado.values()) {
            if (estado.nome.equalsIgnoreCase(value)) {
                return estado;
            }
        }
        return null; // Retorna null se não encontrar um estado correspondente
    }
}
