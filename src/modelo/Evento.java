package modelo;

import java.time.LocalDateTime;

public class Evento extends Compromisso {

    private String local;
    private String descricao;

    public Evento(LocalDateTime dataHora,
                  String titulo,
                  String local,
                  String descricao) {

        super(dataHora, titulo);

        this.local = local;
        this.descricao = descricao;
    }

    public String getLocal() {
        return local;
    }

    @Override
    public String getTipo() {
        return "Evento";
    }

    @Override
    protected String detalhes() {
        return "Local: " + local + " | " + descricao;
    }

}