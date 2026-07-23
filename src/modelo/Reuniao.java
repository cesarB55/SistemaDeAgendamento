package modelo;

import java.time.LocalDateTime;

public class Reuniao extends Compromisso {

    private String pessoa;
    private String descricao;

    public Reuniao(LocalDateTime dataHora,
                   String titulo,
                   String pessoa,
                   String descricao) {

        super(dataHora, titulo);
        this.pessoa = pessoa;
        this.descricao = descricao;
    }

    public String getPessoa() {
        return pessoa;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String getTipo() {
        return "Reunião";
    }

    @Override
    protected String detalhes() {
        return "Pessoa: " + pessoa + " | Descrição: " + descricao;
    }

}