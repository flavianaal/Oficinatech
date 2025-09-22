package io.programe.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import javax.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
@Setter
@Entity
public class Servico implements Serializable {

    // ======= IDENTIFICADOR =======
    
    @Id
    @SequenceGenerator(name = "seq_ordem", sequenceName = "seq_ordem", initialValue = 1)
    @GeneratedValue(generator = "seq_ordem", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal valorServico;
    private boolean ativo;

    // ======= TEMPO ESTIMADO (armazenado em minutos no banco) =======
    private Long tempoEstimadoMinutos;

    public Servico() {}

    public Servico(Long id, String nome, String descricao, BigDecimal valorServico, boolean ativo, Long tempoEstimadoMinutos) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.valorServico = valorServico;
        this.ativo = ativo;
        this.tempoEstimadoMinutos = tempoEstimadoMinutos;
    }

    // ======= MÉTODOS AUXILIARES =======

    /**
     * Retorna tempo estimado como Duration
     */
    @Transient
    public Duration getTempoEstimadoDuration() {
        return tempoEstimadoMinutos == null ? Duration.ZERO : Duration.ofMinutes(tempoEstimadoMinutos);
    }

    /**
     * Define o tempo estimado a partir de uma Duration
     */
    @Transient
    public void setTempoEstimado(Duration duration) {
        this.tempoEstimadoMinutos = (duration == null ? 0 : duration.toMinutes());
    }

    /**
     * Define o tempo estimado a partir de dias, horas e minutos
     */
    @Transient
    public void setTempoEstimado(long dias, long horas, long minutos) {
        Duration d = Duration.ofDays(dias).plusHours(horas).plusMinutes(minutos);
        this.tempoEstimadoMinutos = d.toMinutes();
    }

    /**
     * Retorna tempo estimado formatado como "Xd Yh Zm"
     */
    @Transient
    public String getTempoEstimadoFormatado() {
        if (tempoEstimadoMinutos == null || tempoEstimadoMinutos <= 0) return "";

        long dias = tempoEstimadoMinutos / (24 * 60);
        long horas = (tempoEstimadoMinutos % (24 * 60)) / 60;
        long minutos = tempoEstimadoMinutos % 60;

        StringBuilder sb = new StringBuilder();
        if (dias > 0) sb.append(dias).append("d ");
        if (horas > 0) sb.append(horas).append("h ");
        if (minutos > 0) sb.append(minutos).append("m");
        return sb.toString().trim();
    }
}
