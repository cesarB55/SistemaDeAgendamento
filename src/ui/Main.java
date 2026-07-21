package ui;

import modelo.Compromisso;
import servico.Agenda;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
//import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;


    public class Main extends JFrame {
        private Agenda agenda = new Agenda();
        private DefaultTableModel tableModel;
        private JTable table;
        private JTextField txtDataHora, txtPessoa, txtAssunto, txtDescricao;
        private JTextField txtFiltro;

        private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        public Main() {
            setTitle("Sistema de Agendamento de Compromissos");
            setSize(900, 600);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            initComponents();
            atualizarTabela(agenda.listarTodos());
        }

        private void initComponents() {
            // Painel principal com BorderLayout
            JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
            mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Painel de formulário (entrada de dados)
            JPanel formPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.anchor = GridBagConstraints.WEST;

            // Rótulos e campos
            gbc.gridx = 0; gbc.gridy = 0;
            formPanel.add(new JLabel("Data/Hora (dd/MM/yyyy HH:mm):"), gbc);
            gbc.gridx = 1;
            txtDataHora = new JTextField(20);
            formPanel.add(txtDataHora, gbc);

            gbc.gridx = 0; gbc.gridy = 1;
            formPanel.add(new JLabel("Pessoa atendida:"), gbc);
            gbc.gridx = 1;
            txtPessoa = new JTextField(20);
            formPanel.add(txtPessoa, gbc);

            gbc.gridx = 0; gbc.gridy = 2;
            formPanel.add(new JLabel("Assunto:"), gbc);
            gbc.gridx = 1;
            txtAssunto = new JTextField(20);
            formPanel.add(txtAssunto, gbc);

            gbc.gridx = 0; gbc.gridy = 3;
            formPanel.add(new JLabel("Descrição (opcional):"), gbc);
            gbc.gridx = 1;
            txtDescricao = new JTextField(20);
            formPanel.add(txtDescricao, gbc);

            // Botões de ação
            JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
            JButton btnAgendar = new JButton("Agendar");
            JButton btnRemover = new JButton("Remover Selecionado");
            JButton btnListarTodos = new JButton("Listar Todos");
            JButton btnRelatorios = new JButton("Relatórios");
            btnPanel.add(btnAgendar);
            btnPanel.add(btnRemover);
            btnPanel.add(btnListarTodos);
            btnPanel.add(btnRelatorios);

            gbc.gridx = 0; gbc.gridy = 4;
            gbc.gridwidth = 2;
            formPanel.add(btnPanel, gbc);

            // Adiciona o formPanel ao norte
            mainPanel.add(formPanel, BorderLayout.NORTH);

            // Tabela com scroll
            tableModel = new DefaultTableModel(new String[]{"ID", "Data/Hora", "Dia", "Pessoa", "Assunto", "Descrição"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            table = new JTable(tableModel);
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane scrollPane = new JScrollPane(table);
            mainPanel.add(scrollPane, BorderLayout.CENTER);

            // Painel inferior com campo de filtro rápido (opcional)
            JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            filterPanel.add(new JLabel("Filtrar (Pessoa/Assunto):"));
            txtFiltro = new JTextField(20);
            filterPanel.add(txtFiltro);
            JButton btnFiltrar = new JButton("Filtrar");
            filterPanel.add(btnFiltrar);
            mainPanel.add(filterPanel, BorderLayout.SOUTH);

            add(mainPanel);

            // Ações dos botões
            btnAgendar.addActionListener(e -> agendarCompromisso());
            btnRemover.addActionListener(e -> removerSelecionado());
            btnListarTodos.addActionListener(e -> atualizarTabela(agenda.listarTodos()));
            btnRelatorios.addActionListener(e -> mostrarDialogoRelatorios());
            btnFiltrar.addActionListener(e -> filtrarPorTexto());
        }

        private void agendarCompromisso() {
            String dataHoraStr = txtDataHora.getText().trim();
            String pessoa = txtPessoa.getText().trim();
            String assunto = txtAssunto.getText().trim();
            String descricao = txtDescricao.getText().trim();

            if (dataHoraStr.isEmpty() || pessoa.isEmpty() || assunto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha Data/Hora, Pessoa e Assunto.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            LocalDateTime dataHora;
            try {
                dataHora = LocalDateTime.parse(dataHoraStr, formatter);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Formato inválido! Use dd/MM/yyyy HH:mm", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (dataHora.isBefore(LocalDateTime.now())) {
                JOptionPane.showMessageDialog(this, "A data/hora deve ser a partir de agora.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Compromisso novo = new Compromisso(dataHora, pessoa, assunto, descricao);
            if (agenda.adicionar(novo)) {
                JOptionPane.showMessageDialog(this, "Compromisso agendado com sucesso!\n" + novo);
                limparCampos();
                atualizarTabela(agenda.listarTodos());
            } else {
                JOptionPane.showMessageDialog(this, "Horário já ocupado! Escolha outro horário.", "Conflito", JOptionPane.WARNING_MESSAGE);
            }
        }

        private void removerSelecionado() {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um compromisso na tabela.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id = (int) tableModel.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Remover compromisso ID " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (agenda.remover(id)) {
                    JOptionPane.showMessageDialog(this, "Compromisso removido.");
                    atualizarTabela(agenda.listarTodos());
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao remover.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        private void filtrarPorTexto() {
            String texto = txtFiltro.getText().trim();
            if (texto.isEmpty()) {
                atualizarTabela(agenda.listarTodos());
                return;
            }
            // Busca por pessoa ou assunto (contém)
            List<Compromisso> listaPessoa = agenda.filtrarPorPessoa(texto);
            List<Compromisso> listaAssunto = agenda.filtrarPorAssunto(texto);
            // União (sem duplicatas)
            listaPessoa.addAll(listaAssunto);
            listaPessoa.sort(null);
            atualizarTabela(listaPessoa);
        }

        private void mostrarDialogoRelatorios() {
            // Diálogo personalizado com opções de relatório
            String[] opcoes = {"Dia específico", "Mês/Ano", "Ano", "Pessoa", "Assunto"};
            String escolha = (String) JOptionPane.showInputDialog(this,
                    "Selecione o tipo de relatório:", "Relatórios",
                    JOptionPane.PLAIN_MESSAGE, null, opcoes, opcoes[0]);
            if (escolha == null) return;

            List<Compromisso> lista = null;
            String titulo = "";
            switch (escolha) {
                case "Dia específico":
                    String diaStr = JOptionPane.showInputDialog(this, "Dia (1-31):");
                    String mesStr = JOptionPane.showInputDialog(this, "Mês (1-12):");
                    String anoStr = JOptionPane.showInputDialog(this, "Ano (ex: 2026):");
                    if (diaStr == null || mesStr == null || anoStr == null) return;
                    try {
                        int dia = Integer.parseInt(diaStr);
                        int mes = Integer.parseInt(mesStr);
                        int ano = Integer.parseInt(anoStr);
                        lista = agenda.filtrarPorDia(dia, mes, ano);
                        titulo = String.format("Compromissos em %02d/%02d/%04d", dia, mes, ano);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Valores inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    break;
                case "Mês/Ano":
                    String mesStr2 = JOptionPane.showInputDialog(this, "Mês (1-12):");
                    String anoStr2 = JOptionPane.showInputDialog(this, "Ano (ex: 2026):");
                    if (mesStr2 == null || anoStr2 == null) return;
                    try {
                        int mes = Integer.parseInt(mesStr2);
                        int ano = Integer.parseInt(anoStr2);
                        lista = agenda.filtrarPorMes(mes, ano);
                        titulo = String.format("Compromissos em %02d/%04d", mes, ano);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Valores inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    break;
                case "Ano":
                    String anoStr3 = JOptionPane.showInputDialog(this, "Ano (ex: 2026):");
                    if (anoStr3 == null) return;
                    try {
                        int ano = Integer.parseInt(anoStr3);
                        lista = agenda.filtrarPorAno(ano);
                        titulo = String.format("Compromissos em %04d", ano);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Ano inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    break;
                case "Pessoa":
                    String pessoa = JOptionPane.showInputDialog(this, "Nome da pessoa:");
                    if (pessoa == null) return;
                    lista = agenda.filtrarPorPessoa(pessoa);
                    titulo = "Compromissos para: " + pessoa;
                    break;
                case "Assunto":
                    String assunto = JOptionPane.showInputDialog(this, "Assunto (ou parte):");
                    if (assunto == null) return;
                    lista = agenda.filtrarPorAssunto(assunto);
                    titulo = "Compromissos sobre: " + assunto;
                    break;
                default:
                    return;
            }

            if (lista != null) {
                String relatorio = agenda.gerarRelatorio(lista, titulo);
                JTextArea textArea = new JTextArea(relatorio);
                textArea.setEditable(false);
                JScrollPane scroll = new JScrollPane(textArea);
                scroll.setPreferredSize(new Dimension(600, 400));
                JOptionPane.showMessageDialog(this, scroll, "Relatório", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        private void atualizarTabela(List<Compromisso> lista) {
            tableModel.setRowCount(0);
            for (Compromisso c : lista) {
                tableModel.addRow(new Object[]{
                        c.getId(),
                        c.getDataHoraFormatada(),
                        c.getDiaDaSemana(),
                        c.getPessoa(),
                        c.getAssunto(),
                        c.getDescricao()
                });
            }
        }

        private void limparCampos() {
            txtDataHora.setText("");
            txtPessoa.setText("");
            txtAssunto.setText("");
            txtDescricao.setText("");
        }

        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {
                new Main().setVisible(true);
            });
        }
}
