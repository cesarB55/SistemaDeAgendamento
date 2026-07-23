package modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Compromisso implements Comparable<Compromisso> {

    private static int contadorId = 1;

    protected int id;
    protected LocalDateTime dataHora;
    protected String titulo;

    public Compromisso(LocalDateTime dataHora, String titulo) {
        this.id = contadorId++;
        this.dataHora = dataHora;
        this.titulo = titulo;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDiaDaSemana() {

        switch (dataHora.getDayOfWeek()) {

            case MONDAY:
                return "Segunda-feira";

            case TUESDAY:
                return "Terça-feira";

            case WEDNESDAY:
                return "Quarta-feira";

            case THURSDAY:
                return "Quinta-feira";

            case FRIDAY:
                return "Sexta-feira";

            case SATURDAY:
                return "Sábado";

            default:
                return "Domingo";
        }
    }

    public String getDataHoraFormatada() {
        return dataHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    /**
     * Cada filho informa seu próprio tipo.
     */
    public abstract String getTipo();

    /**
     * Cada filho monta sua descrição.
     */
    protected abstract String detalhes();

    @Override
    public String toString() {

        return String.format(
                "[%s] ID:%d | %s | %s | %s",
                getTipo(),
                id,
                getDataHoraFormatada(),
                titulo,
                detalhes()
        );
    }

    @Override
    public int compareTo(Compromisso outro) {
        return this.dataHora.compareTo(outro.dataHora);
    }

}