package servico;

import modelo.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


public class Agenda {


    private Set<Compromisso> compromissos;


    public Agenda() {

        compromissos = new TreeSet<>();

    }



    /*
     * ===============================
     * CRIAÇÃO DOS COMPROMISSOS
     * ===============================
     */


    public boolean criarReuniao(
            LocalDateTime dataHora,
            String titulo,
            String pessoa,
            String descricao) {


        Reuniao reuniao =
                new Reuniao(
                        dataHora,
                        titulo,
                        pessoa,
                        descricao
                );


        return adicionar(reuniao);
    }



    public boolean criarEvento(
            LocalDateTime dataHora,
            String titulo,
            String local,
            String descricao) {


        Evento evento =
                new Evento(
                        dataHora,
                        titulo,
                        local,
                        descricao
                );


        return adicionar(evento);
    }



    public boolean criarTarefa(
            LocalDateTime dataHora,
            String titulo,
            String descricao) {


        Tarefa tarefa =
                new Tarefa(
                        dataHora,
                        titulo,
                        descricao
                );


        return adicionar(tarefa);
    }




    /*
     * ===============================
     * MANIPULAÇÃO DOS DADOS
     * ===============================
     */


    public boolean adicionar(Compromisso compromisso){


        if(compromisso == null)
            return false;


        if(existeConflito(compromisso.getDataHora()))
            return false;


        return compromissos.add(compromisso);

    }





    public boolean remover(int id){


        return compromissos.removeIf(
                c -> c.getId() == id
        );

    }




    public List<Compromisso> listarTodos(){


        return new ArrayList<>(compromissos);

    }





    /*
     * ===============================
     * VALIDAÇÕES
     * ===============================
     */



    public boolean existeConflito(LocalDateTime dataHora){


        return compromissos.stream()
                .anyMatch(
                        c -> c.getDataHora()
                                .equals(dataHora)
                );

    }






    /*
     * ===============================
     * FILTROS
     * ===============================
     */



    public List<Compromisso> filtrarPorDia(
            int dia,
            int mes,
            int ano){


        return compromissos.stream()

                .filter(c ->
                        c.getDataHora().getDayOfMonth() == dia &&
                                c.getDataHora().getMonthValue() == mes &&
                                c.getDataHora().getYear() == ano
                )

                .collect(Collectors.toList());

    }




    public List<Compromisso> filtrarPorMes(
            int mes,
            int ano){


        return compromissos.stream()

                .filter(c ->
                        c.getDataHora().getMonthValue() == mes &&
                                c.getDataHora().getYear() == ano
                )

                .collect(Collectors.toList());

    }





    public List<Compromisso> filtrarPorAno(int ano){


        return compromissos.stream()

                .filter(c ->
                        c.getDataHora().getYear() == ano
                )

                .collect(Collectors.toList());

    }

    public List<Compromisso> filtrarPorTitulo(String titulo) {

        if (titulo == null || titulo.isBlank()) {
            return Collections.emptyList();
        }


        return compromissos.stream()
                .filter(c -> c.getTitulo() != null &&
                        c.getTitulo()
                                .toLowerCase()
                                .contains(titulo.trim().toLowerCase()))
                .collect(Collectors.toList());

    }






    public List<Compromisso> pesquisarTitulo(String titulo){


        if(titulo == null)
            return Collections.emptyList();



        return compromissos.stream()

                .filter(c ->
                        c.getTitulo()
                                .toLowerCase()
                                .contains(
                                        titulo.toLowerCase()
                                )
                )

                .collect(Collectors.toList());

    }





    public List<Reuniao> listarReunioes(){


        return compromissos.stream()

                .filter(c -> c instanceof Reuniao)

                .map(c -> (Reuniao)c)

                .collect(Collectors.toList());

    }





    public List<Evento> listarEventos(){


        return compromissos.stream()

                .filter(c -> c instanceof Evento)

                .map(c -> (Evento)c)

                .collect(Collectors.toList());

    }





    public List<Tarefa> listarTarefas(){


        return compromissos.stream()

                .filter(c -> c instanceof Tarefa)

                .map(c -> (Tarefa)c)

                .collect(Collectors.toList());

    }






    /*
     * ===============================
     * RELATÓRIOS
     * ===============================
     */



    public String gerarRelatorio(
            List<Compromisso> lista,
            String titulo){


        StringBuilder sb =
                new StringBuilder();



        sb.append("\n========== ")
                .append(titulo)
                .append(" ==========\n");



        if(lista.isEmpty()){

            sb.append(
                    "Nenhum compromisso encontrado.\n"
            );

        }


        else{


            for(Compromisso c : lista){

                sb.append(c)
                        .append("\n");

            }


            sb.append(
                            "Total: "
                    )
                    .append(lista.size())
                    .append(" compromisso(s).\n");

        }



        sb.append(
                "================================\n"
        );


        return sb.toString();

    }



}