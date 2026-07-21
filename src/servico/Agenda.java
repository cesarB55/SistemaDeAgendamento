package servico;

import modelo.Compromisso;
//import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Agenda {
    private List<Compromisso> compromissos;

    public Agenda() {
        this.compromissos = new ArrayList<>();
    }

    public boolean adicionar(Compromisso novo) {
        for (Compromisso c : compromissos) {
            if (c.getDataHora().equals(novo.getDataHora())) {
                return false;
            }
        }
        compromissos.add(novo);
        Collections.sort(compromissos);
        return true;
    }

    public boolean remover(int id) {
        return compromissos.removeIf(c -> c.getId() == id);
    }

    public List<Compromisso> listarTodos() {
        return new ArrayList<>(compromissos);
    }

    public List<Compromisso> filtrarPorDia(int dia, int mes, int ano) {
        return compromissos.stream()
                .filter(c -> c.getDataHora().getDayOfMonth() == dia &&
                        c.getDataHora().getMonthValue() == mes &&
                        c.getDataHora().getYear() == ano)
                .sorted()
                .collect(Collectors.toList());
    }

    public List<Compromisso> filtrarPorMes(int mes, int ano) {
        return compromissos.stream()
                .filter(c -> c.getDataHora().getMonthValue() == mes &&
                        c.getDataHora().getYear() == ano)
                .sorted()
                .collect(Collectors.toList());
    }

    public List<Compromisso> filtrarPorAno(int ano) {
        return compromissos.stream()
                .filter(c -> c.getDataHora().getYear() == ano)
                .sorted()
                .collect(Collectors.toList());
    }

    public List<Compromisso> filtrarPorPessoa(String pessoa) {
        return compromissos.stream()
                .filter(c -> c.getPessoa().equalsIgnoreCase(pessoa))
                .sorted()
                .collect(Collectors.toList());
    }

    public List<Compromisso> filtrarPorAssunto(String assunto) {
        return compromissos.stream()
                .filter(c -> c.getAssunto().toLowerCase().contains(assunto.toLowerCase()))
                .sorted()
                .collect(Collectors.toList());
    }

    public String gerarRelatorio(List<Compromisso> lista, String titulo) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n========== RELATÓRIO: ").append(titulo).append(" ==========\n");
        if (lista.isEmpty()) {
            sb.append("Nenhum compromisso encontrado.\n");
        } else {
            for (Compromisso c : lista) {
                sb.append(c).append("\n");
            }
            sb.append("Total: ").append(lista.size()).append(" compromisso(s).\n");
        }
        sb.append("=========================================\n");
        return sb.toString();
    }
}
