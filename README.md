# Sistema de Agenda de Compromissos

## Descrição

O Sistema de Agenda de Compromissos é uma aplicação desenvolvida em Java com o objetivo de gerenciar diferentes tipos de compromissos por meio de uma interface gráfica em Swing.

O projeto foi desenvolvido utilizando os princípios da Programação Orientada a Objetos, aplicando conceitos como herança, polimorfismo, encapsulamento e separação de responsabilidades.

---

## Funcionalidades

- Cadastro de Reuniões
- Cadastro de Eventos
- Cadastro de Tarefas
- Remoção de compromissos
- Listagem cronológica automática
- Pesquisa por:
  - Dia
  - Mês
  - Ano
  - Pessoa
  - Título
  - Tipo de compromisso
- Geração de relatórios
- Validação de datas
- Verificação de conflitos de horário

---

## Estrutura do Projeto

```
src
│
├── modelo
│   ├── Compromisso.java
│   ├── Reuniao.java
│   ├── Evento.java
│   └── Tarefa.java
│
├── servico
│   └── Agenda.java
│
└── ui
    └── Main.java
```

---

## Arquitetura

O projeto foi dividido em três camadas.

### Modelo

Representa as entidades do sistema.

- Compromisso (classe abstrata)
- Reuniao
- Evento
- Tarefa

### Serviço

A classe Agenda é responsável por toda a lógica de negócio.

Entre suas responsabilidades estão:

- Criar compromissos
- Validar datas
- Verificar conflitos de horário
- Armazenar compromissos
- Pesquisar registros
- Gerar relatórios

### Interface

A classe Main é responsável apenas pela interface gráfica.

Ela coleta os dados do usuário, chama os métodos da Agenda e exibe os resultados na tela.

---

## Herança

A classe abstrata Compromisso possui os atributos comuns a todos os tipos de compromissos.

- ID
- Data e hora
- Título

As classes filhas especializam seu comportamento.

### Reunião

Campos adicionais:

- Pessoa
- Descrição

### Evento

Campos adicionais:

- Local
- Descrição

### Tarefa

Campos adicionais:

- Descrição
- Status (Concluída/Pendente)

---

## Tecnologias Utilizadas

- Java
- Swing
- Java Collections Framework
- TreeSet
- Stream API
- LocalDateTime
- DateTimeFormatter

---

## Organização dos Dados

Todos os compromissos são armazenados em um TreeSet.

Isso garante:

- Ordenação automática por data e hora
- Não existência de compromissos duplicados no mesmo horário

---

## Funcionalidades da Agenda

- Adicionar compromissos
- Remover compromissos
- Criar reuniões
- Criar eventos
- Criar tarefas
- Listar todos os registros
- Filtrar por dia
- Filtrar por mês
- Filtrar por ano
- Filtrar por pessoa
- Filtrar por título
- Filtrar por tipo
- Gerar relatórios

---

## Conceitos de POO Aplicados

- Herança
- Polimorfismo
- Encapsulamento
- Abstração
- Sobrescrita de métodos
- Separação de responsabilidades

---

## Como Executar

1. Clone o repositório.

```bash
git clone <url-do-repositório>
```

2. Abra o projeto em uma IDE Java.

3. Execute a classe:

```
ui.Main
```

---

## Autores

José Francisco
Cesar 
Pedro Henrique
Everaldo
