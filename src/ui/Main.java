package ui;

import modelo.Compromisso;
import modelo.Evento;
import modelo.Reuniao;
import modelo.Tarefa;
import servico.Agenda;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Classe principal responsável pela interface gráfica (GUI) do sistema de agenda.
 * Utiliza a biblioteca Swing para renderização dos componentes.
 */
public class Main extends JFrame {

    // Regras de negócio e armazenamento de dados
    private Agenda agenda;

    // Formatador padrão para conversão de texto em LocalDateTime
    private DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    // Componentes da interface - Campos de texto para os filtros de pesquisa
    private JTextField campoDia;
    private JTextField campoMes;
    private JTextField campoAno;
    private JTextField campoHora;
    private JTextField campoMinuto;
    private JTextField campoBusca;

    // Componentes da interface - Seleção e exibição
    private JComboBox<String> comboTipo;
    private JTextArea areaRelatorio;
    private JLabel labelDataAtual;
    private JButton botaoCompromissos;

    /**
     * Construtor da classe Main.
     * Inicializa a agenda, carrega os dados de teste e monta a tela.
     */
    public Main() {
        agenda = new Agenda();
        carregarDadosExemplo();
        initComponents();
    }

    /**
     * Popula a agenda com dados fictícios para fins de teste e visualização inicial.
     */
    private void carregarDadosExemplo() {
        LocalDateTime agora = LocalDateTime.now();

        // Criação de um Evento daqui a 5 dias
        agenda.criarEvento(
                agora.plusDays(5),
                "Workshop de Java Avançado",
                "Auditório Principal",
                "Workshop sobre programação funcional e streams"
        );

        // Criação de uma Reunião daqui a 2 dias, às 14:00
        agenda.criarReuniao(
                agora.plusDays(2).withHour(14).withMinute(0),
                "Reunião de Equipe",
                "João Silva",
                "Alinhamento semanal do projeto"
        );

        // Criação de uma Tarefa para amanhã às 18:00
        agenda.criarTarefa(
                agora.plusDays(1).withHour(18).withMinute(0),
                "Finalizar Relatório Mensal",
                "Completar o relatório de progresso do mês"
        );

        // Criação de outro Evento daqui a 7 dias às 10:00
        agenda.criarEvento(
                agora.plusDays(7).withHour(10).withMinute(0),
                "Palestra sobre IA",
                "Sala 201",
                "Palestra sobre inteligência artificial e machine learning"
        );
    }

    /**
     * Configura as propriedades da janela principal e adiciona os painéis.
     */
    private void initComponents() {
        setTitle("Sistema de Agenda");
        setSize(1000, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a janela na tela do usuário
        setLayout(new BorderLayout(10, 10)); // Layout principal com margens internas

        // Adiciona o cabeçalho no topo (NORTH)
        JPanel painelSuperior = criarPainelSuperior();
        add(painelSuperior, BorderLayout.NORTH);

        // Adiciona a área de filtros e relatórios no centro (CENTER)
        JPanel painelCentral = criarPainelCentral();
        add(painelCentral, BorderLayout.CENTER);

        setVisible(true); // Exibe a janela
    }

    /**
     * Constrói o cabeçalho da aplicação contendo o relógio e o botão de menu.
     * @return JPanel configurado para a parte superior da tela.
     */
    private JPanel criarPainelSuperior() {
        JPanel painel = new JPanel(new BorderLayout(10, 5));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        painel.setBackground(new Color(0, 102, 204)); // Fundo azul

        // Configuração da label do relógio (atualizada posteriormente por um Timer)
        String dataHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        labelDataAtual = new JLabel("🕒 " + dataHora, SwingConstants.CENTER);
        labelDataAtual.setFont(new Font("Arial", Font.BOLD, 18));
        labelDataAtual.setForeground(Color.WHITE);
        painel.add(labelDataAtual, BorderLayout.CENTER);

        // Configuração do botão principal de ações
        botaoCompromissos = new JButton("📅 COMPROMISSOS");
        botaoCompromissos.setFont(new Font("Arial", Font.BOLD, 14));
        botaoCompromissos.setBackground(new Color(255, 215, 0)); // Fundo amarelo/dourado
        botaoCompromissos.setForeground(Color.BLACK);
        botaoCompromissos.setFocusPainted(false);

        // Criação do menu suspenso (dropdown) para escolher o tipo de compromisso
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem itemEvento = new JMenuItem("📌 EVENTO");
        itemEvento.addActionListener(e -> abrirDialogoNovoCompromisso("EVENTO"));

        JMenuItem itemReuniao = new JMenuItem("👥 REUNIÃO");
        itemReuniao.addActionListener(e -> abrirDialogoNovoCompromisso("REUNIÃO"));

        JMenuItem itemTarefa = new JMenuItem("✅ TAREFA");
        itemTarefa.addActionListener(e -> abrirDialogoNovoCompromisso("TAREFA"));

        popupMenu.add(itemEvento);
        popupMenu.add(itemReuniao);
        popupMenu.add(itemTarefa);

        // Faz o menu aparecer logo abaixo do botão ao ser clicado
        botaoCompromissos.addActionListener(e ->
                popupMenu.show(botaoCompromissos, 0, botaoCompromissos.getHeight())
        );

        // Painel auxiliar para alinhar o botão à direita
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.setOpaque(false); // Fundo transparente
        painelBotoes.add(botaoCompromissos);
        painel.add(painelBotoes, BorderLayout.EAST);

        return painel;
    }

    /**
     * Constrói a área central, unindo os filtros (topo) e a área de texto do relatório (centro).
     */
    private JPanel criarPainelCentral() {
        JPanel painel = new JPanel(new BorderLayout(10,10));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Adiciona a barra de pesquisa na parte superior do painel central
        painel.add(criarPainelFiltros(), BorderLayout.NORTH);

        // Configuração da área de texto onde a agenda será impressa
        areaRelatorio = new JTextArea();
        areaRelatorio.setEditable(false); // Usuário não pode digitar aqui
        areaRelatorio.setFont(new Font("Monospaced", Font.PLAIN, 13)); // Fonte monoespaçada para manter alinhamento visual

        // Envolve o JTextArea em um JScrollPane para permitir rolagem (scroll)
        JScrollPane scroll = new JScrollPane(areaRelatorio);
        scroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0,102,204), 2),
                "📋 RELATÓRIO DE AGENDAMENTOS",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14)
        ));

        painel.add(scroll, BorderLayout.CENTER);

        // Carrega e exibe todos os compromissos logo ao iniciar
        atualizarRelatorio(agenda.listarTodos(), "TODOS OS COMPROMISSOS");

        return painel;
    }

    /**
     * Constrói o formulário de filtros (Data, Hora, Texto, Tipo) usando GridBagLayout para alinhamento preciso.
     */
    private JPanel criarPainelFiltros() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY), "🔎 FILTROS DE PESQUISA"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Margens entre os componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int linha = 0; // Controle dinâmico da linha no GridBagLayout

        // --- LINHA 0: Filtro de Data ---
        gbc.gridy = linha;

        gbc.gridx = 0;
        painel.add(new JLabel("📅 Data:"), gbc);

        gbc.gridx = 1;
        campoDia = new JTextField(4);
        painel.add(campoDia, gbc);

        gbc.gridx = 2;
        painel.add(new JLabel("/"), gbc);

        gbc.gridx = 3;
        campoMes = new JTextField(4);
        painel.add(campoMes, gbc);

        gbc.gridx = 4;
        painel.add(new JLabel("/"), gbc);

        gbc.gridx = 5;
        campoAno = new JTextField(6);
        painel.add(campoAno, gbc);

        // --- LINHA 1: Filtro de Hora ---
        linha++;
        gbc.gridy = linha;

        gbc.gridx = 0;
        painel.add(new JLabel("⏰ Hora:"), gbc);

        gbc.gridx = 1;
        campoHora = new JTextField(4);
        painel.add(campoHora, gbc);

        gbc.gridx = 2;
        painel.add(new JLabel(":"), gbc);

        gbc.gridx = 3;
        campoMinuto = new JTextField(4);
        painel.add(campoMinuto, gbc);

        // --- LINHA 2: Busca por Título e Tipo ---
        linha++;
        gbc.gridy = linha;

        gbc.gridx = 0;
        painel.add(new JLabel("🔍 Buscar:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3; // Faz o campo de busca ocupar 3 colunas
        campoBusca = new JTextField(20);
        painel.add(campoBusca, gbc);

        gbc.gridwidth = 1; // Reseta a largura para as próximas colunas
        gbc.gridx = 4;
        painel.add(new JLabel("Tipo:"), gbc);

        gbc.gridx = 5;
        comboTipo = new JComboBox<>(new String[]{"TODOS", "EVENTO", "REUNIÃO", "TAREFA"});
        painel.add(comboTipo, gbc);

        // --- LINHA 3: Botões de Ação ---
        linha++;
        gbc.gridy = linha;
        gbc.gridx = 0;
        gbc.gridwidth = 6; // O painel de botões ocupa a linha inteira

        JPanel botoes = new JPanel(new FlowLayout());

        JButton filtrar = new JButton("🔎 FILTRAR");
        filtrar.addActionListener(e -> aplicarFiltro());

        JButton limpar = new JButton("🧹 LIMPAR FILTROS");
        limpar.addActionListener(e -> limparFiltros());

        JButton completo = new JButton("📄 RELATÓRIO COMPLETO");
        completo.addActionListener(e -> atualizarRelatorio(agenda.listarTodos(), "TODOS OS COMPROMISSOS"));

        botoes.add(filtrar);
        botoes.add(limpar);
        botoes.add(completo);

        painel.add(botoes, gbc);

        return painel;
    }

    /**
     * Processa os dados preenchidos no formulário de pesquisa e filtra a lista em memória.
     */
    private void aplicarFiltro() {
        // Inicia com a lista completa
        List<Compromisso> resultado = agenda.listarTodos();
        String titulo = "FILTRO APLICADO";

        // 1. Filtro por Título do compromisso
        String busca = campoBusca.getText().trim();
        if(!busca.isEmpty()) {
            resultado = agenda.filtrarPorTitulo(busca);
            titulo += " - Título: " + busca;
        }

        // 2. Filtro por Tipo usando reflexão (instanceof)
        String tipo = (String) comboTipo.getSelectedItem();
        if(tipo != null && !tipo.equals("TODOS")) {
            if(tipo.equals("EVENTO")) {
                resultado = resultado.stream().filter(c -> c instanceof Evento).toList();
            } else if(tipo.equals("REUNIÃO")) {
                resultado = resultado.stream().filter(c -> c instanceof Reuniao).toList();
            } else if(tipo.equals("TAREFA")) {
                resultado = resultado.stream().filter(c -> c instanceof Tarefa).toList();
            }
            titulo += " - Tipo: " + tipo;
        }

        // 3. Filtro por Data (Dia/Mês/Ano)
        String dia = campoDia.getText().trim();
        String mes = campoMes.getText().trim();
        String ano = campoAno.getText().trim();

        try {
            if(!dia.isEmpty() && !mes.isEmpty() && !ano.isEmpty()) {
                // Converte os textos para inteiros e compara com as propriedades do LocalDateTime do compromisso
                resultado = resultado.stream()
                        .filter(c -> c.getDataHora().getDayOfMonth() == Integer.parseInt(dia)
                                && c.getDataHora().getMonthValue() == Integer.parseInt(mes)
                                && c.getDataHora().getYear() == Integer.parseInt(ano))
                        .toList();
            }

            // Obs: Os campos "campoHora" e "campoMinuto" existem na tela, mas não estão sendo aplicados no filtro aqui.

            // Atualiza a tela com a lista resultante
            atualizarRelatorio(resultado, titulo);

        } catch(NumberFormatException e) {
            // Previne erro caso o usuário digite letras nos campos de data
            JOptionPane.showMessageDialog(this, "Digite apenas números nos filtros.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Esvazia todos os campos do painel de filtro e restaura a lista completa.
     */
    private void limparFiltros() {
        campoDia.setText("");
        campoMes.setText("");
        campoAno.setText("");
        campoHora.setText("");
        campoMinuto.setText("");
        campoBusca.setText("");
        comboTipo.setSelectedIndex(0);

        atualizarRelatorio(agenda.listarTodos(), "TODOS OS COMPROMISSOS");
    }

    /**
     * Recebe uma lista de compromissos e as converte em texto usando a classe de negócio (Agenda).
     */
    private void atualizarRelatorio(List<? extends Compromisso> lista, String titulo) {
        areaRelatorio.setText(
                agenda.gerarRelatorio(lista.stream().map(c -> (Compromisso)c).toList(), titulo)
        );
        areaRelatorio.setCaretPosition(0); // Rola o texto de volta para o topo
    }

    /**
     * Abre uma caixa de diálogo dinâmica baseada no tipo de compromisso selecionado pelo usuário.
     * @param tipo "EVENTO", "REUNIÃO" ou "TAREFA"
     */
    private void abrirDialogoNovoCompromisso(String tipo) {
        JTextField titulo = new JTextField(20);
        JTextField data = new JTextField(20); // Requer digitação no formato exato "dd/MM/yyyy HH:mm"
        JTextField extra1 = new JTextField(20); // Usado para Local, Pessoa ou Descrição (varia por tipo)
        JTextField extra2 = new JTextField(20); // Usado para Descrição (varia por tipo)

        JPanel painel = new JPanel(new GridLayout(0, 2, 5, 5));

        // Campos obrigatórios para todos os tipos
        painel.add(new JLabel("Título:"));
        painel.add(titulo);
        painel.add(new JLabel("Data/Hora:"));
        painel.add(data);

        // Campos específicos que mudam dependendo do que está sendo criado
        if(tipo.equals("EVENTO")) {
            painel.add(new JLabel("Local:"));
            painel.add(extra1);
            painel.add(new JLabel("Descrição:"));
            painel.add(extra2);
        } else if(tipo.equals("REUNIÃO")) {
            painel.add(new JLabel("Pessoa:"));
            painel.add(extra1);
            painel.add(new JLabel("Descrição:"));
            painel.add(extra2);
        } else { // TAREFA
            painel.add(new JLabel("Descrição:"));
            painel.add(extra1); // Tarefa não possui campo "extra2"
        }

        // Exibe o popup (bloqueia o resto do app até o usuário responder)
        int opcao = JOptionPane.showConfirmDialog(this, painel, "Novo " + tipo, JOptionPane.OK_CANCEL_OPTION);

        if(opcao != JOptionPane.OK_OPTION) return; // Cancela operação se usuário fechar ou clicar em Cancelar

        try {
            // Tenta processar o texto digitado usando o padrão dd/MM/yyyy HH:mm
            LocalDateTime dataHora = LocalDateTime.parse(data.getText(), formatterData);
            boolean criado = false;

            // Chama o método apropriado na camada de negócio dependendo do tipo
            if(tipo.equals("EVENTO")) {
                criado = agenda.criarEvento(dataHora, titulo.getText(), extra1.getText(), extra2.getText());
            } else if(tipo.equals("REUNIÃO")) {
                criado = agenda.criarReuniao(dataHora, titulo.getText(), extra1.getText(), extra2.getText());
            } else if(tipo.equals("TAREFA")) {
                criado = agenda.criarTarefa(dataHora, titulo.getText(), extra1.getText());
            }

            if(criado) {
                // Atualiza a visualização caso a criação tenha sucesso
                atualizarRelatorio(agenda.listarTodos(), "TODOS OS COMPROMISSOS");
                JOptionPane.showMessageDialog(this, tipo + " criado com sucesso!");
            }

        } catch(Exception e) {
            // Captura erros gerais (principalmente DateTimeParseException caso a data seja digitada errada)
            JOptionPane.showMessageDialog(this, "Erro ao criar compromisso:\n" + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Cria uma thread paralela do Swing (Timer) que roda a cada 1 segundo (1000 ms)
     * para atualizar o relógio exibido no painel superior.
     */
    private void atualizarRelogio() {
        new Timer(1000, e -> {
            labelDataAtual.setText("🕒 " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        }).start();
    }

    /**
     * Ponto de entrada da aplicação.
     */
    public static void main(String[] args) {
        // Garante que a GUI seja iniciada na Event Dispatch Thread (boa prática do Swing)
        SwingUtilities.invokeLater(() -> {
            Main app = new Main();
            app.atualizarRelogio();
        });
    }
}