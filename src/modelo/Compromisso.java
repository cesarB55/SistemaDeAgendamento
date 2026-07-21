package modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Compromisso implements Comparable<Compromisso>{
    private static int contadorId = 1;
    private int id;
    private LocalDateTime dataHora;
    private String pessoa;
    private String assunto;
    private String descricao;

    public Compromisso(LocalDateTime dataHora, String pessoa, String assunto, String descricao) {
        this.id = contadorId++;
        this.dataHora = dataHora;
        this.pessoa = pessoa;
        this.assunto = assunto;
        this.descricao = descricao;
    }

    public int getId() { return id; }
    public LocalDateTime getDataHora() { return dataHora; }
    public String getPessoa() { return pessoa; }
    public String getAssunto() { return assunto; }
    public String getDescricao() { return descricao; }

    public String getDiaDaSemana() {
        switch (dataHora.getDayOfWeek()) {
            case MONDAY:    return "Segunda-feira";
            case TUESDAY:   return "Terça-feira";
            case WEDNESDAY: return "Quarta-feira";
            case THURSDAY:  return "Quinta-feira";
            case FRIDAY:    return "Sexta-feira";
            case SATURDAY:  return "Sábado";
            default:        return "Domingo";
        }
    }

    public String getDataHoraFormatada() {
        return dataHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    @Override
    public String toString() {
        return String.format("ID: %d | %s | %s | Pessoa: %s | Assunto: %s | Descrição: %s",
                id, getDataHoraFormatada(), getDiaDaSemana(), pessoa, assunto, descricao);
    }

    @Override
    public int compareTo(Compromisso outro) {
        return this.dataHora.compareTo(outro.dataHora);
    }
}
