package servico;

import modelo.Compromisso;
import modelo.Evento;
import modelo.Reuniao;
import modelo.Tarefa;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Controla todos os compromissos cadastrados na agenda.
 * É responsável por criar, armazenar, remover, pesquisar
 * e gerar relatórios de reuniões, eventos e tarefas.
 */
public class Agenda {

    /**
     * Estrutura que armazena os compromissos ordenados
     * cronologicamente e sem horários duplicados.
     */
    private final Set<Compromisso> compromissos;

    /**
     * Formato utilizado para converter datas digitadas pelo usuário.
     */
    private final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    /**
     * Construtor da agenda.
     * Inicializa a coleção vazia de compromissos.
     */
    public Agenda() {
        compromissos = new TreeSet<>();
    }

    /**
     * Adiciona um compromisso já criado à agenda.
     *
     * @param compromisso compromisso que será adicionado.
     * @return true caso seja adicionado com sucesso.
     */
    public boolean adicionar(Compromisso compromisso) {

        if (compromisso == null)
            return false;

        return compromissos.add(compromisso);
    }

    /**
     * Cria uma reunião e a adiciona à agenda.
     *
     * @param data Data e horário.
     * @param titulo Título da reunião.
     * @param pessoa Pessoa participante.
     * @param descricao Descrição da reunião.
     * @return true caso a reunião seja cadastrada.
     */
    public boolean criarReuniao(String data,
                                String titulo,
                                String pessoa,
                                String descricao) {

        LocalDateTime dataHora = converterData(data);

        if (dataHora == null)
            return false;

        if (dataHora.isBefore(LocalDateTime.now()))
            return false;

        if (!horarioDisponivel(dataHora))
            return false;

        return adicionar(
                new Reuniao(
                        dataHora,
                        titulo,
                        pessoa,
                        descricao
                )
        );
    }

    /**
     * Cria um evento e o adiciona à agenda.
     *
     * @param data Data e horário.
     * @param titulo Nome do evento.
     * @param local Local do evento.
     * @param descricao Informações adicionais.
     * @return true caso o evento seja cadastrado.
     */
    public boolean criarEvento(String data,
                               String titulo,
                               String local,
                               String descricao) {

        LocalDateTime dataHora = converterData(data);

        if (dataHora == null)
            return false;

        if (dataHora.isBefore(LocalDateTime.now()))
            return false;

        if (!horarioDisponivel(dataHora))
            return false;

        return adicionar(
                new Evento(
                        dataHora,
                        titulo,
                        local,
                        descricao
                )
        );
    }

    /**
     * Cria uma tarefa e a adiciona à agenda.
     *
     * @param data Data da tarefa.
     * @param titulo Nome da tarefa.
     * @param descricao Descrição da tarefa.
     * @return true caso a tarefa seja cadastrada.
     */
    public boolean criarTarefa(String data,
                               String titulo,
                               String descricao) {

        LocalDateTime dataHora = converterData(data);

        if (dataHora == null)
            return false;

        if (dataHora.isBefore(LocalDateTime.now()))
            return false;

        if (!horarioDisponivel(dataHora))
            return false;

        return adicionar(
                new Tarefa(
                        dataHora,
                        titulo,
                        descricao
                )
        );
    }

    /**
     * Remove um compromisso pelo seu identificador.
     *
     * @param id ID do compromisso.
     * @return true caso o compromisso exista.
     */
    public boolean remover(int id) {

        return compromissos.removeIf(c -> c.getId() == id);

    }

    /**
     * Lista todos os compromissos cadastrados.
     *
     * @return Lista ordenada cronologicamente.
     */
    public List<Compromisso> listarTodos() {

        return new ArrayList<>(compromissos);

    }

    /**
     * Lista apenas as reuniões.
     *
     * @return Lista de reuniões.
     */
    public List<Reuniao> listarReunioes() {

        return compromissos.stream()
                .filter(c -> c instanceof Reuniao)
                .map(c -> (Reuniao) c)
                .collect(Collectors.toList());

    }

    /**
     * Lista apenas os eventos.
     *
     * @return Lista de eventos.
     */
    public List<Evento> listarEventos() {

        return compromissos.stream()
                .filter(c -> c instanceof Evento)
                .map(c -> (Evento) c)
                .collect(Collectors.toList());

    }

    /**
     * Lista apenas as tarefas.
     *
     * @return Lista de tarefas.
     */
    public List<Tarefa> listarTarefas() {

        return compromissos.stream()
                .filter(c -> c instanceof Tarefa)
                .map(c -> (Tarefa) c)
                .collect(Collectors.toList());

    }

    /**
     * Pesquisa compromissos de um dia específico.
     *
     * @param dia Dia desejado.
     * @param mes Mês desejado.
     * @param ano Ano desejado.
     * @return Lista dos compromissos encontrados.
     */
    public List<Compromisso> filtrarPorDia(int dia,
                                           int mes,
                                           int ano) {

        return compromissos.stream()

                .filter(c ->
                        c.getDataHora().getDayOfMonth() == dia &&
                                c.getDataHora().getMonthValue() == mes &&
                                c.getDataHora().getYear() == ano)

                .collect(Collectors.toList());

    }

    /**
     * Pesquisa compromissos de um determinado mês.
     *
     * @param mes Mês.
     * @param ano Ano.
     * @return Lista dos compromissos encontrados.
     */
    public List<Compromisso> filtrarPorMes(int mes,
                                           int ano) {

        return compromissos.stream()

                .filter(c ->
                        c.getDataHora().getMonthValue() == mes &&
                                c.getDataHora().getYear() == ano)

                .collect(Collectors.toList());

    }

    /**
     * Pesquisa compromissos de um determinado ano.
     *
     * @param ano Ano desejado.
     * @return Lista dos compromissos encontrados.
     */
    public List<Compromisso> filtrarPorAno(int ano) {

        return compromissos.stream()

                .filter(c ->
                        c.getDataHora().getYear() == ano)

                .collect(Collectors.toList());

    }

    /**
     * Pesquisa reuniões pelo nome da pessoa.
     *
     * @param pessoa Nome da pessoa.
     * @return Lista das reuniões encontradas.
     */
    public List<Reuniao> filtrarPorPessoa(String pessoa) {

        if (pessoa == null || pessoa.isBlank())
            return Collections.emptyList();

        return listarReunioes().stream()

                .filter(r ->
                        r.getPessoa().equalsIgnoreCase(pessoa.trim()))

                .collect(Collectors.toList());

    }

    /**
     * Pesquisa compromissos cujo título contenha o texto informado.
     *
     * @param titulo Texto procurado.
     * @return Lista dos compromissos encontrados.
     */
    public List<Compromisso> filtrarPorTitulo(String titulo) {

        if (titulo == null || titulo.isBlank())
            return Collections.emptyList();

        return compromissos.stream()

                .filter(c ->
                        c.getTitulo()
                                .toLowerCase()
                                .contains(titulo.toLowerCase()))

                .collect(Collectors.toList());

    }

    /**
     * Retorna apenas os compromissos de um tipo específico.
     *
     * Exemplo:
     * listarPorTipo(Reuniao.class)
     *
     * @param tipo Classe desejada.
     * @return Lista contendo apenas o tipo solicitado.
     */
    public List<Compromisso> listarPorTipo(Class<?> tipo) {

        return compromissos.stream()

                .filter(tipo::isInstance)

                .collect(Collectors.toList());

    }

    /**
     * Gera um relatório textual dos compromissos encontrados.
     *
     * @param lista Lista de compromissos.
     * @param titulo Título do relatório.
     * @return Relatório formatado.
     */
    public String gerarRelatorio(List<? extends Compromisso> lista,
                                 String titulo) {

        StringBuilder sb = new StringBuilder();

        sb.append("\n========== ")
                .append(titulo)
                .append(" ==========\n\n");

        if (lista == null || lista.isEmpty()) {

            sb.append("Nenhum compromisso encontrado.\n");

        } else {

            lista.forEach(c -> sb.append(c).append("\n"));

            sb.append("\nTotal: ")
                    .append(lista.size())
                    .append(" compromisso(s).");

        }

        sb.append("\n====================================");

        return sb.toString();

    }

    /**
     * Converte uma String para LocalDateTime.
     *
     * @param texto Data no formato dd/MM/yyyy HH:mm.
     * @return Objeto LocalDateTime ou null caso o formato seja inválido.
     */
    private LocalDateTime converterData(String texto) {

        try {

            return LocalDateTime.parse(texto, formatter);

        } catch (DateTimeParseException e) {

            return null;

        }

    }

    /**
     * Verifica se existe algum compromisso no horário informado.
     *
     * @param dataHora Data e horário desejados.
     * @return true caso o horário esteja livre.
     */
    public boolean horarioDisponivel(LocalDateTime dataHora) {

        return compromissos.stream()

                .noneMatch(c ->
                        c.getDataHora().equals(dataHora));

    }

}