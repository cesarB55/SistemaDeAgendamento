package modelo;

import java.time.LocalDateTime;

public class Tarefa extends Compromisso {

    private String descricao;
    private boolean concluida;

    public Tarefa(LocalDateTime dataHora,
                  String titulo,
                  String descricao) {

        super(dataHora, titulo);

        this.descricao = descricao;
        this.concluida = false;
    }

    public void concluir() {
        concluida = true;
    }

    public boolean isConcluida() {
        return concluida;
    }

    @Override
    public String getTipo() {
        return "Tarefa";
    }

    @Override
    protected String detalhes() {

        return "Descrição: "
                + descricao
                + " | Status: "
                + (concluida ? "Concluída" : "Pendente");
    }

}