package servico;

import modelo.Compromisso;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Gerencia o conjunto de compromissos mantendo-os ordenados por data/hora,
 * permitindo adição, remoção, pesquisas personalizadas e geração de relatórios.
 */
public class Agenda {

    // Conjunto que armazena compromissos ordenados cronologicamente sem duplicidade
    private Set<Compromisso> compromissos;

    /**
     * Construtor da agenda (inicializa a estrutura TreeSet vazia).
     */
    public Agenda() {
        this.compromissos = new TreeSet<>();
    }

    /**
     * Adiciona um novo compromisso à agenda.
     * O TreeSet impede automaticamente cadastros no mesmo horário
     * (baseado no compareTo da classe Compromisso).
     */
    public boolean adicionar(Compromisso novo) {
        if (novo == null) return false;
        return compromissos.add(novo);
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
                .collect(Collectors.toList());
    }

    public List<Compromisso> filtrarPorMes(int mes, int ano) {
        return compromissos.stream()
                .filter(c -> c.getDataHora().getMonthValue() == mes &&
                        c.getDataHora().getYear() == ano)
                .collect(Collectors.toList());
    }

    public List<Compromisso> filtrarPorAno(int ano) {
        return compromissos.stream()
                .filter(c -> c.getDataHora().getYear() == ano)
                .collect(Collectors.toList());
    }

    public List<Compromisso> filtrarPorPessoa(String pessoa) {
        if (pessoa == null || pessoa.isBlank()) return Collections.emptyList();

        return compromissos.stream()
                .filter(c -> c.getPessoa() != null && c.getPessoa().equalsIgnoreCase(pessoa.trim()))
                .collect(Collectors.toList());
    }

    public List<Compromisso> filtrarPorAssunto(String assunto) {
        if (assunto == null || assunto.isBlank()) return Collections.emptyList();

        return compromissos.stream()
                .filter(c -> c.getAssunto() != null &&
                        c.getAssunto().toLowerCase().contains(assunto.trim().toLowerCase()))
                .collect(Collectors.toList());
    }

    public String gerarRelatorio(List<Compromisso> lista, String titulo) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n========== RELATÓRIO: ").append(titulo).append(" ==========\n");

        if (lista == null || lista.isEmpty()) {
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
